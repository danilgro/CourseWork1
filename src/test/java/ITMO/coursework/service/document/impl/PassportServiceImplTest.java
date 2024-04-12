package ITMO.coursework.service.document.impl;

import ITMO.coursework.exceptions.CustomException;
import ITMO.coursework.model.db.entity.document.Passport;
import ITMO.coursework.model.db.entity.people.Worker;
import ITMO.coursework.model.db.repository.document.PassportRepo;
import ITMO.coursework.model.db.repository.people.WorkerRepo;
import ITMO.coursework.model.dto.request.document.PassportInfoRequest;
import ITMO.coursework.model.dto.response.document.PassportInfoResponse;
import ITMO.coursework.model.enums.Status;
import ITMO.coursework.service.people.impl.WorkerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class PassportServiceImplTest {

    @InjectMocks
    private PassportServiceImpl passportService;
    @Mock
    private WorkerServiceImpl workerService;

    @Mock  // глушим bd
    private PassportRepo passportRepo;

    @Mock // глушим bd
    private WorkerRepo workerRepo;
    @Spy //mapper не вызывается
    private ObjectMapper mapper;

    @BeforeEach  // выполненение перед каждым тестом
    public void init() {
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void createPassport() {
        mapper.registerModule(new JavaTimeModule());
        PassportInfoRequest request = new PassportInfoRequest();
        request.setCountry("Russia");
        Mockito.when(passportRepo.save(Mockito.any())).then(answer -> answer.getArgument(0));
        PassportInfoResponse passportInfoResponse = passportService.createPassport(request);
        assertEquals(Status.CREATED, passportInfoResponse.getStatus());
        assertEquals(request.getCountry(), passportInfoResponse.getCountry());
        assertNotNull(passportInfoResponse.getCreatedAt());
    }

    @Test
    public void getPassport() {
        Passport passport = new Passport();
        passport.setId(1);
        Mockito.when(passportRepo.findById(Mockito.any())).thenReturn(Optional.of(passport));
        PassportInfoResponse response = passportService.getPassport(passport.getId());
        assertEquals(response.getId(), passport.getId());
    }

    @Test
    public void getPassportDb() {
        Passport passport = new Passport();
        passport.setId(1);
        Mockito.when(passportRepo.findById(Mockito.any()))  // задаем поведение
                .thenReturn(Optional.of(passport))               //вызов 1
                .thenReturn(Optional.ofNullable(null));  // вызов 2
        Passport passportTest = passportService.getPassportDb(1);
        assertEquals(passport, passportTest);

        assertThrows(CustomException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                passportService.getPassportDb(1);
            }
        });
    }

    @Test
    public void updatePassport() {
        mapper.registerModule(new JavaTimeModule());
        PassportInfoRequest request = new PassportInfoRequest();
        Passport passport = new Passport();
        passport.setCountry("Russia");
        Mockito.when(passportRepo.findById(Mockito.any())).thenReturn(Optional.of(passport));
        Mockito.when(passportRepo.save(Mockito.any())).then(answer -> answer.getArgument(0));
        PassportInfoResponse passportInfoResponse = passportService.updatePassport(1, request);
        assertEquals(passport.getCountry(), passportInfoResponse.getCountry());
        // проверка 2 части
        request.setCountry("Россия");
        passportInfoResponse = passportService.updatePassport(1, request);
        assertEquals(request.getCountry(), passportInfoResponse.getCountry());
    }

    @Test
    public void deletePassport() {
        Passport passport = new Passport();
        Mockito.when(passportRepo.findById(passport.getId())).thenReturn(Optional.of(passport));
        passportService.deletePassport(passport.getId());
        Mockito.verify(passportRepo, Mockito.times(1)).save(
                Mockito.any(Passport.class)); // Проверяем что сохранениие 1ин раз.
        assertEquals(Status.DELETED, passport.getStatus()); // сверяем статус
    }

    @Test
    public void allPassports() {
        List<Passport> list = new ArrayList<>();
        for (int i = 1; i < 3; i++) {
            Passport passport = new Passport();
            passport.setId(i);
            list.add(passport);
        }
        List<PassportInfoResponse> responses = list.stream()
                .map(passport -> mapper.convertValue(passport, PassportInfoResponse.class))
                .collect(Collectors.toList());
        Mockito.when(passportRepo.findAll(Mockito.any(Pageable.class))).thenReturn(new PageImpl<>(list));

        Page<PassportInfoResponse> result = passportService.allPassports(1, 10, "id", Sort.Direction.ASC);
        assertEquals(result.getTotalElements(), list.size());
    }

    @Test
    public void linkPassportWorker() {
        mapper.registerModule(new JavaTimeModule());
        Passport passport = new Passport();
        passport.setId(1);
        List<Passport> list = new ArrayList<>();
        list.add(passport);
        Worker worker = new Worker();
        worker.setId(1);
        worker.setPassports(list);

        Mockito.when(passportRepo.findById(Mockito.any())).thenReturn(Optional.of(passport));
        Mockito.when(workerRepo.findById(Mockito.any())).thenReturn(Optional.of(worker));
        Mockito.when(workerService.getWorkerDb(Mockito.any())).thenReturn(worker);
        Mockito.when(passportRepo.save(Mockito.any())).then(answer -> answer.getArgument(0));

        PassportInfoResponse passportInfoResponse = passportService.linkPassportWorker(1, 1);
        assertEquals(worker.getPassports().get(0).getId(), passportInfoResponse.getId());

    }





}