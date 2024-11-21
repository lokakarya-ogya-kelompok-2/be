package ogya.lokakarya.be.exception;

import java.util.UUID;
import org.springframework.http.HttpStatus;

public class ResponseException extends RuntimeException {
    private final HttpStatus httpStatus;

    public ResponseException(String msg, HttpStatus httpStatus) {
        super(msg);
        this.httpStatus = httpStatus;
    }

    public static ResponseException userNotFound(UUID id) {
        return new ResponseException(String.format("User with id %s could not be found!", id),
                HttpStatus.NOT_FOUND);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
