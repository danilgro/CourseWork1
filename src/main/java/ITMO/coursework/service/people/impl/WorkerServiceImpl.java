package ITMO.coursework.service.people.impl;

import ITMO.coursework.exceptions.CustomException;
import ITMO.coursework.model.db.entity.people.Worker;
import ITMO.coursework.model.db.repository.people.WorkerRepo;
import ITMO.coursework.model.dto.request.people.WorkerInfoRequest;
import ITMO.coursework.model.dto.response.document.CertificateInfoResponse;
import ITMO.coursework.model.dto.response.document.PassportInfoResponse;
import ITMO.coursework.model.dto.response.people.WorkerInfoResponse;
import ITMO.coursework.model.enums.Status;
import ITMO.coursework.service.people.WorkerService;
import ITMO.coursework.service.utils.PaginationUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j  // Логгирование библиотека lombok
@Service
@AllArgsConstructor
public class WorkerServiceImpl implements WorkerService {

    private final WorkerRepo workerRepo;  // инжектим repository  методы из JpaRepository
    private final ObjectMapper mapper;  // инжектим ObjectMapper  работа с  JSON

    @Override   // работает
    public WorkerInfoResponse createWorker(WorkerInfoRequest request) {
        Worker worker = mapper.convertValue(request, Worker.class); //todo
        worker.setCreatedAt(LocalDateTime.now());
        worker.setStatus(Status.CREATED);
        worker = workerRepo.save(worker);
        return mapper.convertValue(worker, WorkerInfoResponse.class);
    }

    @Override  // работает
    public WorkerInfoResponse getWorker(Integer id) {
        return mapper.convertValue(getWorkerDb(id), WorkerInfoResponse.class);
    }

    @Override
    // для внутренних обращений отдаёт сущность Worker
    public Worker getWorkerDb(Integer id) {
        return workerRepo.findById(id).orElseThrow(() -> new CustomException("Worker not found", HttpStatus.NOT_FOUND));
    }

    @Override   // работает
    public WorkerInfoResponse updateWorker(Integer id, WorkerInfoRequest request) {
        Worker worker = getWorkerDb(id);
        worker.setFirstname(request.getFirstname() == null ? worker.getFirstname() : request.getFirstname());
        worker.setSurname(request.getSurname() == null ? worker.getSurname() : request.getSurname());
        worker.setMiddleName(request.getMiddleName() == null ? worker.getMiddleName() : request.getMiddleName());
        worker.setDepartment(request.getDepartment() == null ? worker.getDepartment() : request.getDepartment());
        worker.setPhone(request.getPhone() == null ? worker.getPhone() : request.getPhone());
        worker.setEmail(request.getEmail() == null ? worker.getEmail() : request.getEmail());
        worker.setEducation(request.getEducation() == null ? worker.getEducation() : request.getEducation());
        worker.setMedicalCertificate(request.getMedicalCertificate() == null ? worker.getMedicalCertificate() : request.getMedicalCertificate());
        worker.setProfession(request.getProfession() == null ? worker.getProfession() : request.getProfession());
        worker.setNumberCertification(request.getNumberCertification() == null ? worker.getNumberCertification() : request.getNumberCertification());
        worker.setGender(request.getGender() == null ? worker.getGender() : request.getGender());
        worker.setClassByJackWelch(request.getClassByJackWelch() == null ? worker.getClassByJackWelch() : request.getClassByJackWelch());
        worker.setBirthDay(request.getBirthDay() == null ? worker.getBirthDay() : request.getBirthDay());
        worker.setInn(request.getInn() == null ? worker.getInn() : request.getInn());
        worker.setSnils(request.getSnils() == null ? worker.getSnils() : request.getSnils());
        worker.setSalary(request.getSalary() == 0 ? worker.getSalary() : request.getSalary());
        worker.setQualificationLevel(request.getQualificationLevel() == null ? worker.getQualificationLevel() : request.getQualificationLevel());
        worker.setAge(request.getAge() == null ? worker.getAge() : request.getAge());
        worker.setUpdatedAt(LocalDateTime.now());
        worker.setStatus(Status.UPDATED);
        worker = workerRepo.save(worker);
        return mapper.convertValue(worker, WorkerInfoResponse.class);
    }

    @Override  //  работает
    public String deleteWorker(Integer id) {
        Worker worker = getWorkerDb(id);
        worker.setStatus(Status.DELETED); // помечаем на удаление
        worker.setUpdatedAt(LocalDateTime.now());
        worker = workerRepo.save(worker);
        return "Работник удалён";
    }

    @Override
    public Page<WorkerInfoResponse> allWorkers(Integer page, Integer perPage, String sort, Sort.Direction order) {    //  работает
        Pageable request = PaginationUtil.getPageRequest(page, perPage, sort, order);
        List<WorkerInfoResponse> allWorkersList = workerRepo.findAll(request)
                .getContent()
                .stream()
                .map(worker -> mapper.convertValue(worker, WorkerInfoResponse.class))
                .collect(Collectors.toList());
        PageImpl<WorkerInfoResponse> pageResponse = new PageImpl<>(allWorkersList);
        return pageResponse;
    }

    @Override
    public Worker updatePassportList(Worker worker) {
        return workerRepo.save(worker);   // обновляем данные worker
    }
    @Override
    public Worker updateCertificateList(Worker worker) {
        return workerRepo.save(worker);   // обновляем данные worker
    }

    @Override
    public List<PassportInfoResponse> getPassportsWorker(Integer id) {
        Worker worker = getWorkerDb(id);
        if (worker.getPassports() != null) {
            List<PassportInfoResponse> allPassports = worker.getPassports()
                    .stream()
                    .map(passport -> mapper.convertValue(passport, PassportInfoResponse.class))
                    .collect(Collectors.toList());
            return allPassports;
        } else {
            log.error("Passport not found");
            return new ArrayList<>();
        }
    }

    @Override
    public List<CertificateInfoResponse> getCertificatesWorker(Integer id) {
        Worker worker = getWorkerDb(id);
        if (worker.getCertificates() != null) {
            List<CertificateInfoResponse> allCertificates = worker.getCertificates()
                    .stream()
                    .map(certificate -> mapper.convertValue(certificate, CertificateInfoResponse.class))
                    .collect(Collectors.toList());
            return allCertificates;
        } else {
            log.error("Certificate not found");
            return new ArrayList<>();
        }
    }

    @Override
    public String setPassword(String password, Integer id) {
        Worker worker = getWorkerDb(id);
        worker.setPassword(password);
        workerRepo.save(worker);
        return "Password set";
    }


}



