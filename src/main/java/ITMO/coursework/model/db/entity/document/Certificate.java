package ITMO.coursework.model.db.entity.document;

import ITMO.coursework.model.db.entity.people.Worker;
import ITMO.coursework.model.enums.Status;
import ITMO.coursework.model.enums.WelderLevel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "certificate")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Certificate{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "welder_level")
    WelderLevel welderLevel;

    @Column(name = "valid_until")
    LocalDate validUntil;

    @Column(name = "protocol_number")
    String protocolNumber;

    @Column(name = "code_brand")
    String codeBrand;

    String name;
    String surname;
    String middleName;

    String numberCertificate;
    String who;
    LocalDate issued;
    LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    Status status;
    @Column(name = "created_at")
    LocalDateTime createdAt;
    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @ManyToOne(targetEntity = Worker.class)    // в Employee @OneToMany
    @JsonBackReference(value = "certificateHolder")
    Worker worker;

}
