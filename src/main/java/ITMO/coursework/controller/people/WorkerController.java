package ITMO.coursework.controller.people;

import ITMO.coursework.model.db.entity.document.Certificate;
import ITMO.coursework.model.dto.request.people.WorkerInfoRequest;
import ITMO.coursework.model.dto.response.document.CertificateInfoResponse;
import ITMO.coursework.model.dto.response.document.PassportInfoResponse;
import ITMO.coursework.model.dto.response.people.WorkerInfoResponse;
import ITMO.coursework.service.document.PassportService;
import ITMO.coursework.service.people.WorkerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/worker")    //  ГОТОВО
public class WorkerController {

    private final WorkerService workerService;  // инжектим (injection)  userService в наш контроллер
    private final PassportService passportService;


    @PostMapping
    @Operation(summary = "Создание пользователя")
    public WorkerInfoResponse createWorker(@RequestBody WorkerInfoRequest request) {
        return workerService.createWorker(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение данных о рабочем")
    public WorkerInfoResponse getWorker(@PathVariable Integer id) {
        return workerService.getWorker(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Редактирование данных рабочего")
    public WorkerInfoResponse updateWorker(@PathVariable Integer id, @RequestBody WorkerInfoRequest request) {
        return workerService.updateWorker(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление рабочего")
    public String deleteWorker(@PathVariable Integer id) {
        return workerService.deleteWorker(id);
    }

    @GetMapping("/all")
    @Operation(summary = "Получение всех рабочих")
    public Page<WorkerInfoResponse> allWorkers(@RequestParam(defaultValue = "1") Integer page,
                                               @RequestParam(defaultValue = "10") Integer perPage,
                                               @RequestParam(defaultValue = "name") String sort,
                                               @RequestParam(defaultValue = "ASC") Sort.Direction order) {
        return workerService.allWorkers(page, perPage, sort, order);
    }

    @GetMapping("/getPassports/{id}")
    @Operation(summary = "Получение паспортов рабочего")
    public List<PassportInfoResponse> getPassportsWorker(@PathVariable Integer id) {
        return workerService.getPassportsWorker(id);
    }

    @GetMapping("/getCertificates/{id}")
    @Operation(summary = "Получение сертификатов рабочего")
    public List<CertificateInfoResponse> getCertificatesWorker(@PathVariable Integer id) {
        return workerService.getCertificatesWorker(id);
    }

        @PostMapping("/setPassword/{id}")  //todo
    // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")   // права для роли Admin
    public String setPasswordWorker( String password, @PathVariable Integer id) {   // @PathVariable значение ищет в адресс
        workerService.setPassword(password,id);
        return "Password is saved";
    }



}
