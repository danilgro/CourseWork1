package ITMO.coursework.service.people.impl;

import ITMO.coursework.exceptions.CustomException;
import ITMO.coursework.model.db.entity.document.Certificate;
import ITMO.coursework.model.db.entity.document.Passport;
import ITMO.coursework.model.db.entity.people.Worker;
import ITMO.coursework.model.db.repository.document.PassportRepo;
import ITMO.coursework.model.db.repository.people.WorkerRepo;
import ITMO.coursework.model.dto.request.people.WorkerInfoRequest;
import ITMO.coursework.model.dto.response.document.CertificateInfoResponse;
import ITMO.coursework.model.dto.response.document.PassportInfoResponse;
import ITMO.coursework.model.dto.response.people.WorkerInfoResponse;
import ITMO.coursework.model.enums.Status;
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

import static org.junit.Assert.*;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RunWith(MockitoJUnitRunner.class)
public class WorkerServiceImplTest {
    @InjectMocks
    private WorkerServiceImpl workerService;   // берём реализацию
    @Mock // заглушка bd
    private WorkerRepo workerRepo;
    @Mock // заглушка bd
    private PassportRepo passportRepo;
    @Spy  // ObjectMapper не вызывается
    private ObjectMapper mapper;
    @BeforeEach   // выполняются перед каждым тестом
    public void init() {
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void createWorker() {
        mapper.registerModule(new JavaTimeModule());
        WorkerInfoRequest request = new WorkerInfoRequest();
        request.setProfession("Сварщик");
        Mockito.when(workerRepo.save(Mockito.any())).then(answer-> answer.getArgument(0));
        WorkerInfoResponse workerInfoResponse = workerService.createWorker(request);
        assertEquals(Status.CREATED, workerInfoResponse.getStatus());
        assertEquals(request.getProfession(), workerInfoResponse.getProfession());
        assertNotNull(workerInfoResponse.getCreatedAt());
    }

    @Test
    public void getWorker() {
        Worker worker = new Worker();
        worker.setId(1);
        Mockito.when(workerRepo.findById(Mockito.any())).thenReturn(Optional.of(worker));
        WorkerInfoResponse response = workerService.getWorker(worker.getId());
        assertEquals(response.getId(), worker.getId());
    }

    @Test
    public void getWorkerDb() {
        Worker worker = new Worker();
        worker.setId(1);
        Mockito.when(workerRepo.findById(Mockito.any()))  // задаем поведение
                .thenReturn(Optional.of(worker))               //вызов 1
                .thenReturn(Optional.ofNullable(null));  // вызов 2
      // 2ой вариант  // Mockito.when(workerRepo.findById(worker.getId())).thenReturn(Optional.of(worker));
        Worker workerTest = workerService.getWorkerDb(1);
        assertEquals(worker, workerTest);

        assertThrows(CustomException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                workerService.getWorkerDb(1);
            }
        } );
    }

    //Optional - класс обёртка для сущностей, проверку на null можно не делать
    // Пример Optional.ofNullable(new DemoEntity1()).orElse(other)
    @Test
    public void updateWorker() {
        mapper.registerModule(new JavaTimeModule());
        WorkerInfoRequest request = new WorkerInfoRequest();
        Worker worker = new Worker();
        worker.setFirstname("Василий");
        Mockito.when(workerRepo.findById(Mockito.any())).thenReturn(Optional.of(worker));
        Mockito.when(workerRepo.save(Mockito.any())).then(answer-> answer.getArgument(0));
        WorkerInfoResponse workerInfoResponse = workerService.updateWorker(1, request);
        assertEquals(worker.getFirstname(),workerInfoResponse.getFirstname());
        // проверка 2 части
        request.setFirstname("Петр");
        workerInfoResponse = workerService.updateWorker(1, request);
        assertEquals(request.getFirstname(),workerInfoResponse.getFirstname());
        //worker.setName(request.getName() == null ? worker.getName() : request.getName());
    }

    @Test
    public void deleteWorker() {
        Worker worker = new Worker();
        Mockito.when(workerRepo.findById(worker.getId())).thenReturn(Optional.of(worker));
        workerService.deleteWorker(worker.getId());
        Mockito.verify(workerRepo,Mockito.times(1)).save(
                Mockito.any(Worker.class)); // Проверяем что сохранениие 1ин раз.
        assertEquals(Status.DELETED, worker.getStatus()); // сверяем статус
    }

    @Test
    public void allWorkers() {
        List<Worker> list = new ArrayList<>();
        for (int i = 1; i < 8; i++) {
            Worker worker = new Worker();
            worker.setId(i);
            list.add(worker);
        }
        List<WorkerInfoResponse> responses = list.stream()
                .map(worker -> mapper.convertValue(worker,WorkerInfoResponse.class))
                .collect(Collectors.toList());
        Mockito.when(workerRepo.findAll(Mockito.any(Pageable.class))).thenReturn(new PageImpl<>(list));

        Page<WorkerInfoResponse> result = workerService.allWorkers(1, 10, "id", Sort.Direction.ASC);
        assertEquals(result.getTotalElements(), list.size());
      //  assertEquals(result.getContent(), responses);

    }

    @Test
    public void updatePassportList() {
      Worker worker = new Worker();
        workerService.updatePassportList(new Worker());
        Mockito.verify(workerRepo, Mockito.only()).save(Mockito.any());
    }

    @Test
    public void updateCertificateList() {
        Worker worker = new Worker();
        workerService.updateCertificateList(new Worker());
        Mockito.verify(workerRepo, Mockito.only()).save(Mockito.any());
    }


    @Test
    public void getPassportsWorker() {
        Worker workerOne = new Worker();
        workerOne.setId(1);
        Mockito.when(workerRepo.findById(Mockito.eq( workerOne.getId()))).thenReturn(Optional.of(workerOne));
        List<PassportInfoResponse> response = workerService.getPassportsWorker(workerOne.getId());
        assertTrue(response.isEmpty());

        Worker workerTwo = new Worker();
        workerTwo.setId(2);
        Passport passport = new Passport();
        workerTwo.setPassports(Arrays.asList(passport));
        Mockito.when(workerRepo.findById(Mockito.eq( workerTwo.getId()))).thenReturn(Optional.of(workerTwo));
        response = workerService.getPassportsWorker(workerTwo.getId());
        assertFalse(response.isEmpty());

    }

    @Test
    public void getCertificatesWorker() {
        Worker workerOne = new Worker();
        workerOne.setId(1);
        Mockito.when(workerRepo.findById(Mockito.eq( workerOne.getId()))).thenReturn(Optional.of(workerOne));
        List<CertificateInfoResponse> response = workerService.getCertificatesWorker(workerOne.getId());
        assertTrue(response.isEmpty());

        Worker workerTwo = new Worker();
        workerTwo.setId(2);
        Certificate certificate = new Certificate();
        workerTwo.setCertificates(Arrays.asList(certificate));
        Mockito.when(workerRepo.findById(Mockito.eq( workerTwo.getId()))).thenReturn(Optional.of(workerTwo));
        response = workerService.getCertificatesWorker(workerTwo.getId());
        assertFalse(response.isEmpty());
    }

    @Test
    public void setPassword() {
        Worker workerOne = new Worker();
        String password = "test";
        workerOne.setId(1);
        Mockito.when(workerRepo.findById(Mockito.eq( workerOne.getId()))).thenReturn(Optional.of(workerOne));
        workerService.setPassword(password,workerOne.getId());
    }
}