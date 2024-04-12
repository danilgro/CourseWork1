package ITMO.coursework.exceptions;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@RequiredArgsConstructor
//RuntimeException является не проверяемым исключением
public class CustomException extends RuntimeException {
    private final String message;
    private final HttpStatus status;
}
