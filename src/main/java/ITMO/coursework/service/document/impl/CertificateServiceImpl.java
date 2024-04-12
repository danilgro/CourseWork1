package ITMO.coursework.service.document.impl;

import ITMO.coursework.exceptions.CustomException;
import ITMO.coursework.model.db.entity.document.Certificate;
import ITMO.coursework.model.db.entity.document.Passport;
import ITMO.coursework.model.db.entity.people.Worker;
import ITMO.coursework.model.db.repository.document.CertificateRepo;
import ITMO.coursework.model.db.repository.document.PassportRepo;
import ITMO.coursework.model.dto.request.document.CertificateInfoRequest;
import ITMO.coursework.model.dto.response.document.CertificateInfoResponse;
import ITMO.coursework.model.dto.response.document.PassportInfoResponse;
import ITMO.coursework.model.dto.response.people.WorkerInfoResponse;
import ITMO.coursework.model.enums.Status;
import ITMO.coursework.service.document.CertificateService;
import ITMO.coursework.service.people.WorkerService;
import ITMO.coursework.service.utils.PaginationUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepo certificateRepo;
    private final ObjectMapper mapper;
    private final WorkerService workerService;


    @Override
    public CertificateInfoResponse createCertificate(CertificateInfoRequest request) {
        Certificate certificate = mapper.convertValue(request, Certificate.class); // todo
        certificate.setCreatedAt(LocalDateTime.now());
        certificate.setStatus(Status.CREATED);
        certificate = certificateRepo.save(certificate);
        return mapper.convertValue(certificate, CertificateInfoResponse.class);
    }

    @Override
    public CertificateInfoResponse getCertificate(Integer id) {
        return mapper.convertValue(getCertificateDb(id), CertificateInfoResponse.class);

    }

    @Override
    public Certificate getCertificateDb(Integer id) {
      return certificateRepo.findById(id).orElseThrow(() -> new CustomException("Certificate not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public CertificateInfoResponse updateCertificate(Integer id, CertificateInfoRequest request) {
        Certificate certificate = getCertificateDb(id);
        certificate.setName(request.getName() == null ? certificate.getName() : request.getName());
        certificate.setSurname(request.getSurname() == null ? certificate.getSurname() : request.getSurname());
        certificate.setMiddleName(request.getMiddleName() == null ? certificate.getMiddleName() : request.getMiddleName());
        certificate.setNumberCertificate(request.getNumberCertificate() == null ? certificate.getNumberCertificate() : request.getNumberCertificate());
        certificate.setWho(request.getWho() == null ? certificate.getWho() : request.getWho());
        certificate.setIssued(request.getIssued() == null ? certificate.getIssued() : request.getIssued());
        certificate.setValidUntil(request.getValidUntil() == null ? certificate.getValidUntil() : request.getValidUntil());
        certificate.setWelderLevel(request.getWelderLevel() == null ? certificate.getWelderLevel() : request.getWelderLevel());
        certificate.setProtocolNumber(request.getProtocolNumber() == null ? certificate.getProtocolNumber() : request.getProtocolNumber());
        certificate.setCodeBrand(request.getCodeBrand() == null ? certificate.getCodeBrand() : request.getCodeBrand());
        certificate.setUpdatedAt(LocalDateTime.now());
        certificate.setStatus(Status.UPDATED);
        certificate = certificateRepo.save(certificate);
        return mapper.convertValue(certificate, CertificateInfoResponse.class);

    }

    @Override
    public String deleteCertificate(Integer id) {
        Certificate certificate = getCertificateDb(id);
        certificate.setStatus(Status.DELETED);
        certificate.setUpdatedAt(LocalDateTime.now());
        certificate = certificateRepo.save(certificate);
        return "Сертификат удалён";
    }

    @Override
    public Page<CertificateInfoResponse> allCertificates(Integer page, Integer perPage, String sort, Sort.Direction order) {
        Pageable request = PaginationUtil.getPageRequest(page, perPage, sort, order);
        List<CertificateInfoResponse> allCertificatesList = certificateRepo.findAll(request)
                .getContent()
                .stream()
                .map(certificate -> mapper.convertValue(certificate, CertificateInfoResponse.class))
                .collect(Collectors.toList());
        PageImpl<CertificateInfoResponse> pageResponse = new PageImpl<>(allCertificatesList);
        return pageResponse;
    }

    @Override
    public CertificateInfoResponse linkCertificateWorker(Integer workerId, Integer certificateId) {
        Certificate certificate = getCertificateDb(certificateId); // получаем серт
        Worker worker = workerService.getWorkerDb(workerId);   // получаем сотрудника
        worker.getCertificates().add(certificate); // добавляем серт
        workerService.updateCertificateList(worker); // обновляем данные у сотрудника
        certificate.setWorker(worker);
        certificate = certificateRepo.save(certificate);
      //  WorkerInfoResponse workerInfoResponse = mapper.convertValue(worker, WorkerInfoResponse.class);
        CertificateInfoResponse certificateInfoResponse = mapper.convertValue(certificate, CertificateInfoResponse.class);
        // passportInfoResponse.setWorker(workerInfoResponse);
        return certificateInfoResponse;
    }
}
