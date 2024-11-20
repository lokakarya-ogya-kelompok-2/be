package ogya.lokakarya.be.exception;

import java.util.UUID;
import org.springframework.http.HttpStatus;

public class ResponseException extends RuntimeException {
    private final String scope;
    private final HttpStatus httpStatus;

    public ResponseException(String scope, String msg, HttpStatus httpStatus) {
        super(msg);
        this.httpStatus = httpStatus;
        this.scope = scope;
    }

    public static ResponseException userNotFound(String scope, UUID id) {
        return new ResponseException(scope,
                String.format("User with id %s could not be found!", id), HttpStatus.NOT_FOUND);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getScope() {
        return this.scope;
    }
}
