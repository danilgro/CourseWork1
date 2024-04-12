package ITMO.coursework.model.db.entity.people;

import ITMO.coursework.model.db.entity.document.Certificate;
import ITMO.coursework.model.db.entity.document.Passport;
import ITMO.coursework.model.enums.ClassByJackWelch;
import ITMO.coursework.model.enums.Gender;
import ITMO.coursework.model.enums.Status;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@MappedSuperclass
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String firstname;
    String surname;
    @Column(name = "middle_name")
    String middleName;

    @Column(unique = true)
    String name;
    String password;
    String roles;

    String department;  //todo сделать отдельный класс
    String phone;
    String email;
    String education;
    @Column(name = "medical_certificate")
    String medicalCertificate;
    Long inn;
    Long snils;
    float salary;
    @Column(name = "qualification_level")
    Integer qualificationLevel;
    @Enumerated(EnumType.STRING) // записываем явно enum как строку, а не как число
    Gender gender;
    @Enumerated(EnumType.STRING)
    Status status;
    @Column(name = "class_by_Jack_Welch")
    @Enumerated(EnumType.STRING)
    ClassByJackWelch classByJackWelch;

    @Column(name = "created_at")
    LocalDateTime createdAt;
    @Column(name = "updated_at")
    LocalDateTime updatedAt;
    @Column(name = "birth_day")
    LocalDate birthDay;



//   @Transient // Вычислется из birthDay
    @Column(name = "age")
    Integer age;
//    public Integer getAge() {    // todo
//        return Period.between(birthDay,LocalDate.now()).getYears();
//    }

}
