package ITMO.coursework.model.dto.response.people;

import ITMO.coursework.model.dto.request.people.ManagerInfoRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ManagerInfoResponse extends ManagerInfoRequest {
    Integer id;

}
