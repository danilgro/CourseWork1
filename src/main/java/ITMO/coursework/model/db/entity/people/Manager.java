package ITMO.coursework.model.db.entity.people;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "manager")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Manager extends Employee{

    String position;
    @Column(name = "professional_experience")
    Integer professionalExperience;
}
