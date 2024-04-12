package ITMO.coursework.controller.document;

import ITMO.coursework.model.dto.request.document.LessonInfoRequest;
import ITMO.coursework.model.dto.response.document.LessonInfoResponse;
import ITMO.coursework.model.dto.response.document.PassportInfoResponse;
import ITMO.coursework.service.document.LessonService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
//import org.springframework.security.access.prepost.PreAuthorize;


@RestController
@AllArgsConstructor
@RequestMapping("lesson")
public class LessonController {
    private final LessonService lessonService;

    @PostMapping
    @Operation(summary = "Создание урока")
    public LessonInfoResponse createLesson(@RequestBody LessonInfoRequest request) {
        return lessonService.createLesson(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение урока")
    public LessonInfoResponse getLesson(@PathVariable Integer id) {
        return lessonService.getLesson(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Редактирование урока")
    public LessonInfoResponse updatePLesson(@PathVariable Integer id, @RequestBody LessonInfoRequest request) {
        return lessonService.updateLesson(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление урока")
    public String deleteLesson(@PathVariable Integer id) {
        return lessonService.deleteLesson(id);
    }

    @GetMapping("/allLessons")
    @Operation(summary = "Получение всех уроков")
    public Page<LessonInfoResponse> allPassports(@RequestParam(defaultValue = "1") Integer page,
                                                 @RequestParam(defaultValue = "10") Integer perPage,
                                                 @RequestParam(defaultValue = "id") String sort,
                                                 @RequestParam(defaultValue = "ASC") Sort.Direction order) {
        return lessonService.allLessons(page, perPage, sort, order);
    }


}