package ITMO.coursework.service.document.impl;

import ITMO.coursework.model.db.entity.document.Lesson;
import ITMO.coursework.model.db.repository.people.WorkerRepo;
import ITMO.coursework.service.people.WorkerService;
import lombok.AllArgsConstructor;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AppServiceImpl {
    private WorkerRepo workerRepo;
    private WorkerService workerService;
 //   private PasswordEncoder passwordEncoder;

//    public void setPasswordWorker(String password, Integer id) {
//        Worker worker = workerService.getWorkerDb(id);
//        worker.setPassword(passwordEncoder.encode(password));
//        workerRepo.save(worker);
//    }

}

