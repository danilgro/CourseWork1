package ITMO.coursework.model.db.repository.people;

import ITMO.coursework.model.db.entity.people.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepo extends JpaRepository<Manager, Integer> {


}
