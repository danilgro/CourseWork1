package ITMO.coursework.service.document;

import ITMO.coursework.model.db.entity.document.Passport;
import ITMO.coursework.model.dto.request.document.PassportInfoRequest;
import ITMO.coursework.model.dto.response.document.PassportInfoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;


public interface PassportService {
    PassportInfoResponse createPassport (PassportInfoRequest request);

    PassportInfoResponse getPassport(Integer id);

    Passport getPassportDb(Integer id);

    PassportInfoResponse updatePassport(Integer id, PassportInfoRequest request);

    String deletePassport(Integer id);

    Page<PassportInfoResponse> allPassports(Integer page, Integer perPage, String sort, Sort.Direction order);

    PassportInfoResponse linkPassportWorker(Integer workerId, Integer passportId);

}
