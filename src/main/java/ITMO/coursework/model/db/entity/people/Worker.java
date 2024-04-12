package ITMO.coursework.model.db.entity.people;

import ITMO.coursework.model.db.entity.document.Certificate;
import ITMO.coursework.model.db.entity.document.Passport;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "worker")
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Worker extends Employee {

    String profession;
    @Column(name = "number_certification")
    String numberCertification;
    @Column(name = "date_certification")
    LocalDate dateCertification;

    @OneToMany  // в passport @ManyToOne
    @JsonManagedReference(value = "passportHolder")   // в Passport @JsonBackReference, они связаны этой анотацией
    List<Passport> passports;

    @OneToMany  // в certificate @ManyToOne
    @JsonManagedReference(value = "certificateHolder")   // в Certificate @JsonBackReference, они связаны этой анотацией
    List<Certificate> certificates;

}
