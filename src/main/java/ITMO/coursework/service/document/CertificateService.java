package ITMO.coursework.service.document;

import ITMO.coursework.model.db.entity.document.Certificate;
import ITMO.coursework.model.dto.request.document.CertificateInfoRequest;
import ITMO.coursework.model.dto.response.document.CertificateInfoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;


public interface CertificateService {

    CertificateInfoResponse createCertificate (CertificateInfoRequest request);

    CertificateInfoResponse getCertificate(Integer id);

    Certificate getCertificateDb(Integer id);

    CertificateInfoResponse updateCertificate(Integer id, CertificateInfoRequest request);

    String deleteCertificate(Integer id);

    Page<CertificateInfoResponse> allCertificates(Integer page, Integer perPage, String sort, Sort.Direction order);

    CertificateInfoResponse linkCertificateWorker (Integer workerId, Integer CertificateId);
}
