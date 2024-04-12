package ITMO.coursework.controller.document;

import ITMO.coursework.model.dto.request.document.CertificateInfoRequest;
import ITMO.coursework.model.dto.response.document.CertificateInfoResponse;
import ITMO.coursework.service.document.CertificateService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("certificate")
public class CertificateController {

    private final CertificateService certificateService;

    @PostMapping
    @Operation(summary = "Создание удостоверения")
    //  @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public CertificateInfoResponse createCertificate(@RequestBody CertificateInfoRequest request) {
        return certificateService.createCertificate(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение удостоверения")
    //@PreAuthorize("hasAnyAuthority('ROLE_WORKER')")
    public CertificateInfoResponse getCertificate(@PathVariable Integer id) {
        return certificateService.getCertificate(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Редактирование удостоверения")
    //@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public CertificateInfoResponse updateCertificate(@PathVariable Integer id, @RequestBody CertificateInfoRequest request) {
        return certificateService.updateCertificate(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление удостоверения")
    // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public String deleteCertificate(@PathVariable Integer id) {
        return certificateService.deleteCertificate(id);
    }

    @GetMapping("/allCertificates")
    @Operation(summary = "Получение всех удостоверений")
    // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public Page<CertificateInfoResponse> allCertificates(@RequestParam(defaultValue = "1") Integer page,
                                                         @RequestParam(defaultValue = "10") Integer perPage,
                                                         @RequestParam(defaultValue = "id") String sort,
                                                         @RequestParam(defaultValue = "ASC") Sort.Direction order) {
        return certificateService.allCertificates(page, perPage, sort, order);
    }

    @PostMapping("/linkWorker/{workerId}/{certificateId}")
    @Operation(summary = "Присваивание удостоверения рабочему")
    // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public CertificateInfoResponse linkWorker(@PathVariable Integer workerId, @PathVariable Integer certificateId) {
        return certificateService.linkCertificateWorker(workerId, certificateId);
    }


}
