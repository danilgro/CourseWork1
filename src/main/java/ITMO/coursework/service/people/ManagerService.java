package ITMO.coursework.service.people;

import ITMO.coursework.model.db.entity.people.Manager;
import ITMO.coursework.model.dto.request.people.ManagerInfoRequest;
import ITMO.coursework.model.dto.response.people.ManagerInfoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ManagerService {   // ГОТОВО
    ManagerInfoResponse createManager(ManagerInfoRequest request);

    ManagerInfoResponse getManager(Integer id);

    Manager getManagerDb(Integer id);

    ManagerInfoResponse updateManager(Integer id, ManagerInfoRequest request);

    String deleteManager(Integer id);

    Page<ManagerInfoResponse> allManagers(Integer page, Integer perPage, String sort, Sort.Direction order);

}
