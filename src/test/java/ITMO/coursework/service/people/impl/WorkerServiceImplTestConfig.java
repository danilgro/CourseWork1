package ITMO.coursework.service.people.impl;

import ITMO.coursework.model.db.entity.people.Worker;
import ITMO.coursework.model.db.repository.document.PassportRepo;
import ITMO.coursework.model.db.repository.people.WorkerRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

//@TestConfiguration

public class WorkerServiceImplTestConfig {
    @MockBean
    WorkerRepo workerRepo;
    @MockBean
    PassportRepo passportRepo;

    @Bean
    public WorkerServiceImpl workerServiceImpl(WorkerRepo workerRepo) {

        return new WorkerServiceImpl(workerRepo, new ObjectMapper());
    }




}
