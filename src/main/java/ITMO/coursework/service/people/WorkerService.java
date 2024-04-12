package ITMO.coursework.service.people;

import ITMO.coursework.model.db.entity.people.Worker;
import ITMO.coursework.model.dto.request.people.WorkerInfoRequest;
import ITMO.coursework.model.dto.response.document.CertificateInfoResponse;
import ITMO.coursework.model.dto.response.document.PassportInfoResponse;
import ITMO.coursework.model.dto.response.people.WorkerInfoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface WorkerService {    // ГОТОВО
    WorkerInfoResponse createWorker(WorkerInfoRequest request);

    WorkerInfoResponse getWorker(Integer id);

    Worker getWorkerDb(Integer id);

    WorkerInfoResponse updateWorker(Integer id, WorkerInfoRequest request);

    String deleteWorker(Integer id);

    Page<WorkerInfoResponse> allWorkers(Integer page, Integer perPage, String sort, Sort.Direction order);

    Worker updatePassportList(Worker worker);

    Worker updateCertificateList(Worker worker);

    List<PassportInfoResponse> getPassportsWorker(Integer id);

    List<CertificateInfoResponse> getCertificatesWorker(Integer id);

    String setPassword(String password, Integer id);


}
