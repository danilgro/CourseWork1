package ITMO.coursework.service.people;

import ITMO.coursework.config.WorkerDetails;
import ITMO.coursework.model.db.entity.people.Worker;
import ITMO.coursework.model.db.repository.people.WorkerRepo;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WorkerDetailsService {//implements UserDetailsService {

//    @Autowired
//    private WorkerRepo workerRepo;
//
//    // внедряем UserRepository полем
//    @Override
//    public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException {
//        Optional<Worker> user = workerRepo.findByName(username);  // ищем user в репозитории
//        return user.map(WorkerDetails::new)
//                .orElseThrow(() -> new UsernameNotFoundException( username + " not found"));
//        // кастим его к WorkerDetails или выбрасываем ошибку
//    }

}
