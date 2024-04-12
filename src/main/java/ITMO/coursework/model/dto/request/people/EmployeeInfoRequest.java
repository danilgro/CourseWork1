package ITMO.coursework.model.dto.request.people;

import ITMO.coursework.model.db.entity.document.Certificate;
import ITMO.coursework.model.db.entity.document.Passport;
import ITMO.coursework.model.dto.response.document.PassportInfoResponse;
import ITMO.coursework.model.enums.ClassByJackWelch;
import ITMO.coursework.model.enums.Gender;
import ITMO.coursework.model.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@MappedSuperclass
@Getter
@Setter
//@SuperBuilder   // для реализации патеррна builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class EmployeeInfoRequest {

    String firstname;
    String surname;
    String middleName;
    String department;  //todo сделать отдельный класс
    String phone;
    String email;
    String education;
    String medicalCertificate;
    Long inn;
    Long snils;
    float salary;
    Integer qualificationLevel;
    Integer age;
    String password;
    String roles;
    String name;
    Gender gender;
    Status status;
    ClassByJackWelch classByJackWelch;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    LocalDate birthDay;
    List<PassportInfoResponse> passports;
    List<Certificate> certificates;
}
