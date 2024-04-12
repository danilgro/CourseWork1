package ITMO.coursework.service.document.impl;

import ITMO.coursework.exceptions.CustomException;
import ITMO.coursework.model.db.entity.document.Lesson;
import ITMO.coursework.model.db.repository.document.LessonRepo;

import ITMO.coursework.model.dto.request.document.LessonInfoRequest;
import ITMO.coursework.model.dto.response.document.LessonInfoResponse;
import ITMO.coursework.model.enums.Status;
import ITMO.coursework.service.document.LessonService;
import ITMO.coursework.service.utils.PaginationUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepo lessonRepo;
    private final ObjectMapper mapper;


    @Override
    public LessonInfoResponse createLesson(LessonInfoRequest request) {
        Lesson lesson = mapper.convertValue(request, Lesson.class); // todo
        lesson.setCreatedAt(LocalDateTime.now());
        lesson.setStatus(Status.CREATED);
        lesson = lessonRepo.save(lesson);
        return mapper.convertValue(lesson, LessonInfoResponse.class);
    }

    @Override
    public LessonInfoResponse getLesson(Integer id) {
        return mapper.convertValue(getLessonDb(id), LessonInfoResponse.class);
    }

    @Override
    public Lesson getLessonDb(Integer id) {
        return lessonRepo.findById(id).orElseThrow(() -> new CustomException("Lesson not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public LessonInfoResponse updateLesson(Integer id, LessonInfoRequest request) {
        Lesson lesson = getLessonDb(id);
        lesson.setTitle(request.getTitle() == null ? lesson.getTitle() : request.getTitle());
        lesson.setDescription(request.getDescription() == null ? lesson.getDescription() : request.getDescription());
        lesson.setUrl(request.getUrl() == null ? lesson.getUrl() : request.getUrl());
        lesson.setUpdatedAt(LocalDateTime.now());
        lesson.setStatus(Status.UPDATED);
        lesson = lessonRepo.save(lesson);
        return mapper.convertValue(lesson, LessonInfoResponse.class);
    }

    @Override
    public String deleteLesson(Integer id) {
        Lesson lesson = getLessonDb(id);
        lesson.setStatus(Status.DELETED);
        lesson.setUpdatedAt(LocalDateTime.now());
        lessonRepo.save(lesson);
        return "Урок удалён";
    }

    @Override
    public Page<LessonInfoResponse> allLessons(Integer page, Integer perPage, String sort, Sort.Direction order) {
        Pageable request = PaginationUtil.getPageRequest(page, perPage, sort, order);
        List<LessonInfoResponse> allLessonsList = lessonRepo.findAll(request)
                .getContent()
                .stream()
                .map(lesson -> mapper.convertValue(lesson, LessonInfoResponse.class))
                .collect(Collectors.toList());
        PageImpl<LessonInfoResponse> pageResponse = new PageImpl<>(allLessonsList);
        return pageResponse;
    }
}
