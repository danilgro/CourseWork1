package ITMO.coursework.model.dto.request.people;

import ITMO.coursework.model.dto.request.people.EmployeeInfoRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;


@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WorkerInfoRequest extends EmployeeInfoRequest {

    String profession;
    String numberCertification;
    LocalDate dateCertification;
}
