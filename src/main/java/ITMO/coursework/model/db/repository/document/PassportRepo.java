package ITMO.coursework.model.db.repository.document;

import ITMO.coursework.model.db.entity.document.Passport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassportRepo extends JpaRepository <Passport,Integer> {

}
