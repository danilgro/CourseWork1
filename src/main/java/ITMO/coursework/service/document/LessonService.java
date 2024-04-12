package ITMO.coursework.service.document;

import ITMO.coursework.model.db.entity.document.Lesson;

import ITMO.coursework.model.dto.request.document.LessonInfoRequest;
import ITMO.coursework.model.dto.response.document.LessonInfoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface LessonService {
    LessonInfoResponse createLesson (LessonInfoRequest request);

    LessonInfoResponse getLesson(Integer id);

    Lesson getLessonDb(Integer id);

    LessonInfoResponse updateLesson(Integer id, LessonInfoRequest request);

    String deleteLesson(Integer id);

    Page< LessonInfoResponse> allLessons(Integer page, Integer perPage, String sort, Sort.Direction order);


}
