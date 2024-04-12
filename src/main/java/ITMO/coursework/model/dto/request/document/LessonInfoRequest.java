package ITMO.coursework.model.dto.request.document;


import ITMO.coursework.model.enums.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonInfoRequest {
    String title;
    String description;
    String url;
    Status status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
