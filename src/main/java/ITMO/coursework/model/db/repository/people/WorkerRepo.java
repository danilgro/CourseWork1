package ITMO.coursework.model.db.repository.people;

import ITMO.coursework.model.db.entity.people.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkerRepo extends JpaRepository <Worker,Integer> {

   Optional<Worker> findByName(String username);
}
