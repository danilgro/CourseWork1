package ITMO.coursework.model.dto.request.people;

import ITMO.coursework.model.dto.request.people.EmployeeInfoRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ManagerInfoRequest extends EmployeeInfoRequest {
    String position;
    Integer professionalExperience;
}
