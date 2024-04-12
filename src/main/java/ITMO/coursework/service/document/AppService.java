package ITMO.coursework.service.document;

import ITMO.coursework.model.db.entity.document.Lesson;
import ITMO.coursework.model.db.entity.people.Worker;
import ITMO.coursework.model.dto.request.document.CabinetInfoRequest;
import ITMO.coursework.model.dto.request.people.WorkerInfoRequest;
import ITMO.coursework.model.dto.response.document.CabinetInfoResponse;
import ITMO.coursework.model.dto.response.document.CertificateInfoResponse;
import ITMO.coursework.model.dto.response.document.LessonInfoResponse;
import ITMO.coursework.model.dto.response.document.PassportInfoResponse;
import ITMO.coursework.model.dto.response.people.WorkerInfoResponse;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface AppService {

    CabinetInfoResponse createCabinet(Integer id, List<Lesson> list);   // Admin

    String deleteCabinet(Integer id);



}
