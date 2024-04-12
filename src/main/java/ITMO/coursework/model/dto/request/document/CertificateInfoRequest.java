package ITMO.coursework.model.dto.request.document;

import ITMO.coursework.model.db.entity.document.Passport;
import ITMO.coursework.model.enums.WelderLevel;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CertificateInfoRequest extends Passport {
    String numberCertificate;
    WelderLevel welderLevel;
    LocalDate validUntil;
    String protocolNumber;
    String codeBrand;

}
