package ITMO.coursework.service.document.impl;

import ITMO.coursework.exceptions.CustomException;
import ITMO.coursework.model.db.entity.document.Passport;
import ITMO.coursework.model.db.entity.people.Worker;
import ITMO.coursework.model.db.repository.document.PassportRepo;
import ITMO.coursework.model.dto.request.document.PassportInfoRequest;
import ITMO.coursework.model.dto.response.document.PassportInfoResponse;
import ITMO.coursework.model.enums.Status;
import ITMO.coursework.service.document.PassportService;
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
public class PassportServiceImpl implements PassportService {

    private final PassportRepo passportRepo;
    private final ObjectMapper mapper;
    private final WorkerService workerService;


    @Override
    public PassportInfoResponse createPassport(PassportInfoRequest request) {
        Passport passport = mapper.convertValue(request, Passport.class); // todo
        passport.setCreatedAt(LocalDateTime.now());
        passport.setStatus(Status.CREATED);
        passport = passportRepo.save(passport);
        return mapper.convertValue(passport, PassportInfoResponse.class);
    }

    @Override
    public PassportInfoResponse getPassport(Integer id) {
        return mapper.convertValue(getPassportDb(id), PassportInfoResponse.class);
    }

    @Override
    public Passport getPassportDb(Integer id) {
        return passportRepo.findById(id).orElseThrow(() -> new CustomException("Passport not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public PassportInfoResponse updatePassport(Integer id, PassportInfoRequest request) {
        Passport passport = getPassportDb(id);
        passport.setName(request.getName() == null ? passport.getName() : request.getName());
        passport.setSurname(request.getSurname() == null ? passport.getSurname() : request.getSurname());
        passport.setMiddleName(request.getMiddleName() == null ? passport.getMiddleName() : request.getMiddleName());
        passport.setSerial(request.getName() == null ? passport.getSerial() : request.getSerial());
        passport.setNumber(request.getNumberPassport() == null ? passport.getNumber() : request.getNumberPassport());
        passport.setWho(request.getWho() == null ? passport.getWho() : request.getWho());
        passport.setIssued(request.getIssued() == null ? passport.getIssued() : request.getIssued());
        passport.setBirthDate(request.getBirthDate() == null ? passport.getBirthDate() : request.getBirthDate());
        passport.setCountry(request.getCountry() == null ? passport.getCountry() : request.getCountry());
        passport.setCity(request.getCity() == null ? passport.getCity() : request.getCity());
        passport.setStreet(request.getStreet() == null ? passport.getStreet() : request.getStreet());
        passport.setNumberHouse(request.getNumberHouse() == null ? passport.getNumberHouse() : request.getNumberHouse());
        passport.setUpdatedAt(LocalDateTime.now());
        passport.setStatus(Status.UPDATED);
        passport = passportRepo.save(passport);
        return mapper.convertValue(passport, PassportInfoResponse.class);
    }

    @Override
    public String deletePassport(Integer id) {
        Passport passport = getPassportDb(id);
        passport.setStatus(Status.DELETED);
        passport.setUpdatedAt(LocalDateTime.now());
        passportRepo.save(passport);
        return "Паспорт удалён";
    }

    @Override
    public Page<PassportInfoResponse> allPassports(Integer page, Integer perPage, String sort, Sort.Direction order) {
        Pageable request = PaginationUtil.getPageRequest(page, perPage, sort, order);
        List<PassportInfoResponse> allPassportsList = passportRepo.findAll(request)
                .getContent()
                .stream()
                .map(passport -> mapper.convertValue(passport, PassportInfoResponse.class))
                .collect(Collectors.toList());
        PageImpl<PassportInfoResponse> pageResponse = new PageImpl<>(allPassportsList);
        return pageResponse;
    }

    @Override
    public PassportInfoResponse linkPassportWorker(Integer workerId, Integer passportId) {
        Passport passport = getPassportDb(passportId); // получаем паспорт
        Worker worker = workerService.getWorkerDb(workerId);   // получаем сотрудника
        worker.getPassports().add(passport); // добавляем паспорт
        workerService.updatePassportList(worker); // обновляем данные у сотрудника
        passport.setWorker(worker);
        passport = passportRepo.save(passport);
      //  WorkerInfoResponse workerInfoResponse = mapper.convertValue(worker, WorkerInfoResponse.class);
        PassportInfoResponse passportInfoResponse = mapper.convertValue(passport, PassportInfoResponse.class);
       // passportInfoResponse.setWorker(workerInfoResponse);
        return passportInfoResponse;
    }


}
