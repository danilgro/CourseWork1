package ITMO.coursework.model.dto.request.document;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContractInfoRequest {
    String name;
    LocalDate date;
    String number;
    String subjectContract;
    Long costContract;
    LocalDate contractPeriod;
    Integer numberTrainedEmployees;
    String contractor;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

}
