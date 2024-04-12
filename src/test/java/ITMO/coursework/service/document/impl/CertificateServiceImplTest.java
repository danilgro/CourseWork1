package ITMO.coursework.service.document.impl;

import ITMO.coursework.exceptions.CustomException;
import ITMO.coursework.model.db.entity.document.Certificate;
import ITMO.coursework.model.db.entity.people.Worker;
import ITMO.coursework.model.db.repository.document.CertificateRepo;
import ITMO.coursework.model.db.repository.people.WorkerRepo;
import ITMO.coursework.model.dto.request.document.CertificateInfoRequest;
import ITMO.coursework.model.dto.response.document.CertificateInfoResponse;
import ITMO.coursework.model.enums.Status;
import ITMO.coursework.service.people.impl.WorkerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;
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
public class CertificateServiceImplTest {

    @InjectMocks
    private CertificateServiceImpl certificateService;
    @Mock
    private WorkerServiceImpl workerService;
    @Mock  // глушим bd
    private CertificateRepo certificateRepo;;
    @Mock // глушим bd
    private WorkerRepo workerRepo;
    @Spy //mapper не вызывается
    private ObjectMapper mapper;

    @Test
    public void createCertificate() {
        mapper.registerModule(new JavaTimeModule());
        CertificateInfoRequest request = new CertificateInfoRequest();
        request.setNumberCertificate("45442885");
        Mockito.when(certificateRepo.save(Mockito.any())).then(answer -> answer.getArgument(0));

        CertificateInfoResponse certificateInfoResponse = certificateService.createCertificate(request);
        assertEquals(Status.CREATED, certificateInfoResponse.getStatus());
        assertEquals(request.getNumberCertificate(), certificateInfoResponse.getNumberCertificate());
        assertNotNull(certificateInfoResponse.getCreatedAt());
    }

    @Test
    public void getCertificate() {
        Certificate certificate = new Certificate();
        certificate.setId(1);
        Mockito.when(certificateRepo.findById(Mockito.any())).thenReturn(Optional.of(certificate));
        CertificateInfoResponse response = certificateService.getCertificate(certificate.getId());
        assertEquals(response.getId(), certificate.getId());
    }

    @Test
    public void getCertificateDb() {
        Certificate certificate = new Certificate();
        certificate.setId(1);
        Mockito.when(certificateRepo.findById(Mockito.any()))  // задаем поведение
                .thenReturn(Optional.of(certificate))               //вызов 1
                .thenReturn(Optional.ofNullable(null));  // вызов 2
        Certificate certificateTest = certificateService.getCertificateDb(1);
        assertEquals(certificate, certificateTest);

        assertThrows(CustomException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                certificateService.getCertificateDb(1);
            }
        });
    }

    @Test
    public void updateCertificate() {
        mapper.registerModule(new JavaTimeModule());
        CertificateInfoRequest request = new CertificateInfoRequest();
        Certificate certificate = new Certificate();
        certificate.setWho("Ростехнадзор");
        Mockito.when(certificateRepo.findById(Mockito.any())).thenReturn(Optional.of(certificate));
        Mockito.when(certificateRepo.save(Mockito.any())).then(answer -> answer.getArgument(0));
        CertificateInfoResponse certificateInfoResponse = certificateService.updateCertificate(1, request);
        assertEquals(certificate.getWho(), certificateInfoResponse.getWho());
        // проверка 2 части
        request.setWho("HAKC");
        certificateInfoResponse = certificateService.updateCertificate(1, request);
        assertEquals(request.getWho(), certificateInfoResponse.getWho());
    }

    @Test
    public void deleteCertificate() {
        Certificate certificate = new Certificate();
        Mockito.when(certificateRepo.findById(certificate.getId())).thenReturn(Optional.of(certificate));
        certificateService.deleteCertificate(certificate.getId());
        Mockito.verify(certificateRepo, Mockito.times(1)).save(
                Mockito.any(Certificate.class)); // Проверяем что сохранениие 1ин раз.
        assertEquals(Status.DELETED, certificate.getStatus()); // сверяем статус
    }

    @Test
    public void allCertificates() {
        List<Certificate> list = new ArrayList<>();
        for (int i = 1; i < 3; i++) {
            Certificate certificate = new Certificate();
            certificate.setId(i);
            list.add(certificate);
        }
        List<CertificateInfoResponse> responses = list.stream()
                .map(certificate -> mapper.convertValue(certificate, CertificateInfoResponse.class))
                .collect(Collectors.toList());
        Mockito.when(certificateRepo.findAll(Mockito.any(Pageable.class))).thenReturn(new PageImpl<>(list));
        Page<CertificateInfoResponse> result = certificateService.allCertificates(1, 10, "id", Sort.Direction.ASC);
        assertEquals(result.getTotalElements(), list.size());
    }

    @Test
    public void linkCertificateWorker() {
        mapper.registerModule(new JavaTimeModule());
        Certificate certificate = new Certificate();
        certificate.setId(1);
        List<Certificate> list = new ArrayList<>();
        list.add(certificate);
        Worker worker = new Worker();
        worker.setId(1);
        worker.setCertificates(list);

        Mockito.when(certificateRepo.findById(Mockito.any())).thenReturn(Optional.of(certificate));
        Mockito.when(workerRepo.findById(Mockito.any())).thenReturn(Optional.of(worker));
        Mockito.when(workerService.getWorkerDb(Mockito.any())).thenReturn(worker);
        Mockito.when(certificateRepo.save(Mockito.any())).then(answer -> answer.getArgument(0));

        CertificateInfoResponse certificateInfoResponse = certificateService.linkCertificateWorker(1, 1);
        assertEquals(worker.getCertificates().get(0).getId(), certificateInfoResponse.getId());

    }
}