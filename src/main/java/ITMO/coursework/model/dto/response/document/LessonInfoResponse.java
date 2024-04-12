package ITMO.coursework.model.dto.response.document;

import ITMO.coursework.model.dto.request.document.LessonInfoRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LessonInfoResponse extends LessonInfoRequest {
    Integer id;
}
