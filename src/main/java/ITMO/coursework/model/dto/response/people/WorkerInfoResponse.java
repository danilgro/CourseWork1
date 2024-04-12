package ITMO.coursework.model.dto.response.people;

import ITMO.coursework.model.dto.request.people.WorkerInfoRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
//@SuperBuilder         // для реализации патеррна builder
//@AllArgsConstructor   // коструктор со всеми аргументами
//@NoArgsConstructor   // коструктор без аргументов
@FieldDefaults(level = AccessLevel.PRIVATE)  // установка всех полей по умолчанию private
//@JsonIgnoreProperties(ignoreUnknown = true)   // игнорировать поля со значением "null"

public class WorkerInfoResponse extends WorkerInfoRequest {
    Integer id;

}
