package ITMO.coursework.controller.document;

import ITMO.coursework.model.dto.request.document.PassportInfoRequest;
import ITMO.coursework.model.dto.response.document.PassportInfoResponse;
import ITMO.coursework.service.document.PassportService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
//import org.springframework.security.access.prepost.PreAuthorize;


@RestController
@AllArgsConstructor
@RequestMapping("passport")
public class PassportController {

    private final PassportService passportService;

    @PostMapping
    @Operation(summary = "Создание паспорта")
    //  @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public PassportInfoResponse createPassport(@RequestBody PassportInfoRequest request) {
        return passportService.createPassport(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение паспорта")
    //@PreAuthorize("hasAnyAuthority('ROLE_WORKER')")
    public PassportInfoResponse getPassport(@PathVariable Integer id) {
        return passportService.getPassport(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Редактирование паспорта")
    //  @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public PassportInfoResponse updatePassport(@PathVariable Integer id, @RequestBody PassportInfoRequest request) {
        return passportService.updatePassport(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление паспорта")
    //  @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public String deletePassport(@PathVariable Integer id) {
        return passportService.deletePassport(id);
    }

    @GetMapping("/allPassports")
    @Operation(summary = "Получение всех паспортов")
    //  @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public Page<PassportInfoResponse> allPassports(@RequestParam(defaultValue = "1") Integer page,
                                                   @RequestParam(defaultValue = "10") Integer perPage,
                                                   @RequestParam(defaultValue = "id") String sort,
                                                   @RequestParam(defaultValue = "ASC") Sort.Direction order) {
        return passportService.allPassports(page, perPage, sort, order);
    }

    @PostMapping("/linkWorker/{workerId}/{passportId}")
    @Operation(summary = "Присваивание паспорта рабочему")
    //  @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public PassportInfoResponse linkWorker(@PathVariable Integer workerId, @PathVariable Integer passportId) {
        return passportService.linkPassportWorker(workerId, passportId);
    }


}