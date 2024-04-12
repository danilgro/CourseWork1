package ITMO.coursework.service.document.impl;

import ITMO.coursework.exceptions.CustomException;
import ITMO.coursework.model.db.entity.document.Lesson;
import ITMO.coursework.model.db.entity.document.Passport;
import ITMO.coursework.model.db.repository.document.LessonRepo;
import ITMO.coursework.model.db.repository.document.PassportRepo;
import ITMO.coursework.model.db.repository.people.WorkerRepo;
import ITMO.coursework.model.dto.request.document.LessonInfoRequest;
import ITMO.coursework.model.dto.request.document.PassportInfoRequest;
import ITMO.coursework.model.dto.response.document.LessonInfoResponse;
import ITMO.coursework.model.dto.response.document.PassportInfoResponse;
import ITMO.coursework.model.enums.Status;
import ITMO.coursework.service.people.impl.WorkerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class LessonServiceImplTest {

    @InjectMocks
    private LessonServiceImpl lessonService;

    @Mock  // глушим bd
    private LessonRepo lessonRepo;

    @Spy //mapper не вызывается
    private ObjectMapper mapper;

    @BeforeEach  // выполненение перед каждым тестом
    public void init() {
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void createLesson() {
        mapper.registerModule(new JavaTimeModule());
        LessonInfoRequest request = new LessonInfoRequest();
        request.setTitle("Урок");
        Mockito.when(lessonRepo.save(Mockito.any())).then(answer -> answer.getArgument(0));
        LessonInfoResponse lessonInfoResponse = lessonService.createLesson(request);
        assertEquals(Status.CREATED, lessonInfoResponse.getStatus());
        assertEquals(request.getTitle(), lessonInfoResponse.getTitle());
        assertNotNull(lessonInfoResponse.getCreatedAt());
    }


    @Test
    public void getLesson() {
        Lesson lesson = new Lesson();
        lesson.setId(1);
        Mockito.when(lessonRepo.findById(Mockito.any())).thenReturn(Optional.of(lesson));
        LessonInfoResponse response = lessonService.getLesson(lesson.getId());
        assertEquals(response.getId(), lesson.getId());
    }

    @Test
    public void getLessonDb() {
        Lesson lesson = new Lesson();
        lesson.setId(1);
        Mockito.when(lessonRepo.findById(Mockito.any()))  // задаем поведение
                .thenReturn(Optional.of(lesson))               //вызов 1
                .thenReturn(Optional.ofNullable(null));  // вызов 2
        Lesson lessonTest = lessonService.getLessonDb(1);
        assertEquals(lesson, lessonTest);

        assertThrows(CustomException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                lessonService.getLessonDb(1);
            }
        });
    }

    @Test
    public void updateLesson() {
        mapper.registerModule(new JavaTimeModule());
        LessonInfoRequest request = new LessonInfoRequest();
        Lesson lesson = new Lesson();
        lesson.setTitle("Урок");
        Mockito.when(lessonRepo.findById(Mockito.any())).thenReturn(Optional.of(lesson));
        Mockito.when(lessonRepo.save(Mockito.any())).then(answer -> answer.getArgument(0));
        LessonInfoResponse lessonInfoResponse = lessonService.updateLesson(1, request);
        assertEquals(lesson.getTitle(), lessonInfoResponse.getTitle());
        // проверка 2 части
        request.setTitle("Урок");
        lessonInfoResponse = lessonService.updateLesson(1, request);
        assertEquals(request.getTitle(), lessonInfoResponse.getTitle());
    }

    @Test
    public void deleteLesson() {
        Lesson lesson = new Lesson();
        Mockito.when(lessonRepo.findById(lesson.getId())).thenReturn(Optional.of(lesson));
        lessonService.deleteLesson(lesson.getId());
        Mockito.verify(lessonRepo, Mockito.times(1)).save(
                Mockito.any(Lesson.class)); // Проверяем что сохранениие 1ин раз.
        assertEquals(Status.DELETED, lesson.getStatus()); // сверяем статус
    }

    @Test
    public void allLessons() {
        List<Lesson> list = new ArrayList<>();
        for (int i = 1; i < 3; i++) {
            Lesson lesson = new Lesson();
            lesson.setId(i);
            list.add(lesson);
        }
        List<LessonInfoResponse> responses = list.stream()
                .map(lesson -> mapper.convertValue(lesson, LessonInfoResponse.class))
                .collect(Collectors.toList());
        Mockito.when(lessonRepo.findAll(Mockito.any(Pageable.class))).thenReturn(new PageImpl<>(list));

        Page<LessonInfoResponse> result = lessonService.allLessons(1, 10, "id", Sort.Direction.ASC);
        assertEquals(result.getTotalElements(), list.size());

    }
}