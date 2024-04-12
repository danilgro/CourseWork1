package ITMO.coursework.service.people.impl;

import ITMO.coursework.model.db.entity.people.Manager;
import ITMO.coursework.model.db.repository.people.ManagerRepo;
import ITMO.coursework.model.dto.request.people.ManagerInfoRequest;
import ITMO.coursework.model.dto.response.people.ManagerInfoResponse;
import lombok.AllArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.stereotype.Service;
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
public class ManagerServiceImplTest {

    @InjectMocks
    private ManagerServiceImpl managerService;   // берём реализацию
    @Mock // заглушка bd
    private ManagerRepo managerRepo;
    @Spy  // ObjectMapper не вызывается
    private ObjectMapper mapper;

    @BeforeEach   // выполняются перед каждым тестом
    public void init() {
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void createManager() {
        mapper.registerModule(new JavaTimeModule());
        ManagerInfoRequest request = new ManagerInfoRequest();
        request.setPosition("Cпециалист");
        Mockito.when(managerRepo.save(Mockito.any())).then(answer -> answer.getArgument(0));
        ManagerInfoResponse managerInfoResponse = managerService.createManager(request);
        assertEquals(Status.CREATED, managerInfoResponse.getStatus());
        assertEquals(request.getPosition(), managerInfoResponse.getPosition());
        assertNotNull(managerInfoResponse.getCreatedAt());
    }

    @Test
    public void getManager() {
        Manager manager = new Manager();
        manager.setId(1);
        Mockito.when(managerRepo.findById(Mockito.any())).thenReturn(Optional.of(manager));
        ManagerInfoResponse response = managerService.getManager(manager.getId());
        assertEquals(response.getId(), manager.getId());
    }

    @Test
    public void getManagerDb() {
        Manager manager = new Manager();
        manager.setId(1);
        Mockito.when(managerRepo.findById(Mockito.any()))  // задаем поведение
                .thenReturn(Optional.of(manager))               //вызов 1
                .thenReturn(Optional.ofNullable(null));  // вызов 2
        Manager managerTest = managerService.getManagerDb(1);
        assertEquals(manager, managerTest);
        assertThrows(CustomException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                managerService.getManagerDb(1);
            }
        });
    }

    @Test
    public void updateManager() {
        mapper.registerModule(new JavaTimeModule());
        ManagerInfoRequest request = new ManagerInfoRequest();
        Manager manager = new Manager();
        manager.setPosition("Специалист");
        Mockito.when(managerRepo.findById(Mockito.any())).thenReturn(Optional.of(manager));
        Mockito.when(managerRepo.save(Mockito.any())).then(answer-> answer.getArgument(0));
        ManagerInfoResponse managerInfoResponse = managerService.updateManager(1, request);
        assertEquals(manager.getPosition(),managerInfoResponse.getPosition());
        // проверка 2 части
        request.setPosition("рабочий");
        managerInfoResponse = managerService.updateManager(1, request);
        assertEquals(request.getPosition(),managerInfoResponse.getPosition());
    }

    @Test
    public void deleteManager() {
        Manager manager = new Manager();
        Mockito.when(managerRepo.findById(manager.getId())).thenReturn(Optional.of(manager));
        managerService.deleteManager(manager.getId());
        Mockito.verify(managerRepo,Mockito.times(1)).save(
                Mockito.any(Manager.class)); // Проверяем что сохранениие 1ин раз.
        assertEquals(Status.DELETED, manager.getStatus()); // сверяем статус

    }

    @Test
    public void allManagers() {
        List<Manager> list = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            Manager manager = new Manager();
            manager.setId(i);
            list.add(manager);
        }
        List<ManagerInfoResponse> responses = list.stream()
                .map(manager -> mapper.convertValue(manager,ManagerInfoResponse.class))
                .collect(Collectors.toList());
        Mockito.when(managerRepo.findAll(Mockito.any(Pageable.class))).thenReturn(new PageImpl<>(list));

        Page<ManagerInfoResponse> result = managerService.allManagers(1, 10, "id", Sort.Direction.ASC);
        assertEquals(result.getTotalElements(), list.size());
        //  assertEquals(result.getContent(), responses);

    }
}