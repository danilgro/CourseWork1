package ITMO.coursework.model.db.entity.document;

import ITMO.coursework.model.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "lesson")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String title;
    String description;
    String url;
    Status status;
    String result;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;


}
