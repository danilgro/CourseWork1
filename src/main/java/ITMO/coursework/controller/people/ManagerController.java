package ITMO.coursework.controller.people;

import ITMO.coursework.model.dto.request.people.ManagerInfoRequest;
import ITMO.coursework.model.dto.response.people.ManagerInfoResponse;
import ITMO.coursework.service.people.ManagerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("manager")  //  ГОТОВО
public class ManagerController {

    private final ManagerService managerService;

    @PostMapping
    @Operation(summary = "Создание сотрудника")
    public ManagerInfoResponse createManager(@RequestBody ManagerInfoRequest request) {
        return managerService.createManager(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение данных о сотруднике")
    public ManagerInfoResponse getManager(@PathVariable Integer id) {
        return managerService.getManager(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Редактирование данных сотрудника")
    public ManagerInfoResponse updateManager(@PathVariable Integer id, @RequestBody ManagerInfoRequest request) {
        return managerService.updateManager(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление сотрудника")
    public String deleteManager(@PathVariable Integer id) {
        return managerService.deleteManager(id);
    }


    @GetMapping("/all")
    @Operation(summary = "Получение всех сотрудников")
    public Page<ManagerInfoResponse> allManagers(@RequestParam(defaultValue = "1") Integer page,
                                                 @RequestParam(defaultValue = "10") Integer perPage,
                                                 @RequestParam(defaultValue = "name") String sort,
                                                 @RequestParam(defaultValue = "ASC") Sort.Direction order) {
        return managerService.allManagers(page, perPage, sort, order);
    }

}
