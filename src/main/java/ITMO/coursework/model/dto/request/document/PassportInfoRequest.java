package ITMO.coursework.model.dto.request.document;

import ITMO.coursework.model.db.entity.document.Passport;
import ITMO.coursework.model.enums.Status;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PassportInfoRequest {
    String name;
    String surname;
    String middleName;
    Integer serial;
    Integer numberPassport;
    String who;
    LocalDate issued;
    LocalDate birthDate;
    String country;
    String city;
    String street;
    String numberHouse;
    Status status;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
