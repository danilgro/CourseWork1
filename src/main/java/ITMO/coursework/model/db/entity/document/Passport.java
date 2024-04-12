package ITMO.coursework.model.db.entity.document;

import ITMO.coursework.model.db.entity.people.Worker;
import ITMO.coursework.model.enums.Status;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "passport")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Enumerated(EnumType.STRING)
    Status status;
    @Column(name = "created_at")
    LocalDateTime createdAt;
    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    String name;
    String surname;
    String middleName;
    Integer serial;
    Integer number;
    String who;
    LocalDate issued;
    LocalDate birthDate;
    String country;
    String city;
    String street;
    String numberHouse;

    @ManyToOne(targetEntity = Worker.class)   // в Employee @OneToMany, value один и тот же для связывания
    @JsonBackReference (value = "passportHolder")   // в Passport @JsonBackReference, они связаны этой анотацией
    Worker worker;

}
