package ITMO.coursework.service.people.impl;

import ITMO.coursework.exceptions.CustomException;
import ITMO.coursework.model.db.entity.people.Manager;
import ITMO.coursework.model.db.repository.people.ManagerRepo;
import ITMO.coursework.model.dto.request.people.ManagerInfoRequest;
import ITMO.coursework.model.dto.response.people.ManagerInfoResponse;
import ITMO.coursework.model.enums.Status;
import ITMO.coursework.service.people.ManagerService;
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
public class ManagerServiceImpl implements ManagerService {

    private final ManagerRepo managerRepo;
    private final ObjectMapper mapper; // для перевода в JSON

    public ManagerInfoResponse createManager (ManagerInfoRequest request) {
        Manager manager = mapper.convertValue(request, Manager.class); //todo
        manager.setCreatedAt(LocalDateTime.now());
        manager.setStatus(Status.CREATED);
        manager = managerRepo.save(manager);
        return mapper.convertValue(manager, ManagerInfoResponse.class);
    }
    @Override
    public ManagerInfoResponse getManager(Integer id) {
        return mapper.convertValue(getManagerDb(id), ManagerInfoResponse.class);
    }
    @Override
    public Manager getManagerDb(Integer id) {
        return managerRepo.findById(id).orElseThrow(() -> new CustomException("Manager not found", HttpStatus.NOT_FOUND));
    }
    @Override
    public ManagerInfoResponse updateManager(Integer id, ManagerInfoRequest request) {
        Manager manager = getManagerDb(id);
        manager.setFirstname(request.getName() == null ? manager.getFirstname() : request.getName());
        manager.setSurname(request.getSurname() == null ? manager.getSurname() : request.getSurname());
        manager.setMiddleName(request.getMiddleName() == null ? manager.getMiddleName() : request.getMiddleName());
        manager.setDepartment(request.getDepartment() == null ? manager.getDepartment() : request.getDepartment());
        manager.setPhone(request.getPhone() == null ? manager.getPhone() : request.getPhone());
        manager.setEmail(request.getEmail() == null ? manager.getEmail() : request.getEmail());
        manager.setEducation(request.getEducation() == null ? manager.getEducation() : request.getEducation());
        manager.setGender(request.getGender() == null ? manager.getGender() : request.getGender());
        manager.setClassByJackWelch(request.getClassByJackWelch() == null ? manager.getClassByJackWelch() : request.getClassByJackWelch());
        manager.setBirthDay(request.getBirthDay() == null ? manager.getBirthDay() : request.getBirthDay());
        manager.setInn(request.getInn() == null ? manager.getInn() : request.getInn());
        manager.setSnils(request.getSnils() == null ? manager.getSnils() : request.getSnils());
        manager.setSalary( request.getSalary() == 0 ? manager.getSalary() : request.getSalary());
        manager.setQualificationLevel(request.getQualificationLevel() == null ? manager.getQualificationLevel() : request.getQualificationLevel());
        manager.setAge(request.getAge() == null ? manager.getAge() : request.getAge());
        manager.setPosition(request.getPosition() == null ? manager.getPosition() : request.getPosition());
        manager.setProfessionalExperience(request.getProfessionalExperience() == null ? manager.getProfessionalExperience() : request.getProfessionalExperience());
        manager.setUpdatedAt(LocalDateTime.now());
        manager.setStatus(Status.UPDATED);
        manager = managerRepo.save(manager);
        return mapper.convertValue(manager, ManagerInfoResponse.class);
    }
    @Override
    public String deleteManager(Integer id) {
        Manager manager = getManagerDb(id);
        manager.setStatus(Status.DELETED); // помечаем на удаление
        manager.setUpdatedAt(LocalDateTime.now());
        manager = managerRepo.save(manager);
        return "Работник удалён";
    }
    @Override
    public Page<ManagerInfoResponse> allManagers(Integer page, Integer perPage, String sort, Sort.Direction order) {
        Pageable request = PaginationUtil.getPageRequest(page, perPage, sort, order);
        List<ManagerInfoResponse> allManagersList = managerRepo.findAll(request)
                .getContent()
                .stream()
                .map(manager -> mapper.convertValue(manager, ManagerInfoResponse.class))
                .collect(Collectors.toList());
        PageImpl<ManagerInfoResponse> pageResponse = new PageImpl<>(allManagersList);
        return pageResponse;
   }


}
