package ogya.lokakarya.be.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import io.jsonwebtoken.JwtException;
import ogya.lokakarya.be.dto.ResponseDto;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto<Map<String, String>>> handleValidationExceptions(
            MethodArgumentNotValidException ex, WebRequest req) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        ex.getBindingResult().getGlobalErrors().forEach(error -> {
            errors.put(error.getObjectName(), error.getDefaultMessage());
        });
        return ResponseDto.<Map<String, String>>builder().success(false)
                .message("Validation failed!").content(errors).build()
                .toResponse(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ResponseException.class)
    public ResponseEntity<ResponseDto<Void>> handleResponseException(ResponseException ex,
            WebRequest req) {
        return ResponseDto.<Void>builder().success(false).message(ex.getMessage()).build()
                .toResponse(ex.getHttpStatus());
    }


    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ResponseDto<Object>> handleJwtException(JwtException ex, WebRequest req) {
        return ResponseDto.builder().success(false).message(ex.getMessage()).build()
                .toResponse(HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<Object>> handleException(Exception ex, WebRequest req) {
        ex.printStackTrace();
        return ResponseDto.builder().success(false).message(ex.getMessage()).build()
                .toResponse(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
