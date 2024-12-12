package ogya.lokakarya.be.exception;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
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
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        ex.getBindingResult().getGlobalErrors()
                .forEach(error -> errors.put(error.getObjectName(), error.getDefaultMessage()));
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

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseDto<Object>> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex, WebRequest req) {

        Throwable rootCause = ex.getRootCause();

        if (rootCause instanceof SQLIntegrityConstraintViolationException sqlEx) {
            String exceptionMsg = sqlEx.getMessage();
            HttpStatus httpStatus = HttpStatus.CONFLICT;
            String message = "Unknown error";
            if (exceptionMsg.contains("UK_USER_USERNAME")) {
                message = "User with given username already exists!";
            } else if (exceptionMsg.contains("UK_USER_EMAIL")) {
                message = "User with given email already exists!";
            } else if (exceptionMsg.contains("UK_DIVISION_DIVISION_NAME")) {
                message = "Division with given name already exists!";
            } else if (exceptionMsg.contains("FK_USER_DIVISION")) {
                message = "Cannot delete this division because it still has users assigned to it. Please reassign or remove these users first.";
                httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            } else if (exceptionMsg.contains("UK_ROLE_ROLENAME")) {
                message = "Role with given name already exists!";
            } else if (exceptionMsg.contains("UK_MENU_MENU_NAME")) {
                message = "Menu with given name already exists!";
            } else if (exceptionMsg.contains("FK_ATTITUDE_SKILL_GROUP_ATTITUDE_SKILL")) {
                message = "Cannot delete this group attitude skill because it still has attitude skills assigned to it. Please reassign or remove these attitude skills first.";
            } else if (exceptionMsg.contains("UK_ATTITUDE_SKILL_GROUP_NAME")) {
                message = "Attitude skill with given name already exists in this group!";
            } else if (exceptionMsg.contains("FK_ACHIEVEMENT_GROUP_ACHIEVEMENT")) {
                message = "Cannot delete this group achievement because it still has achievements assigned to it. Please reassign or remove these achievement first.";
            } else if (exceptionMsg.contains("UK_ACHIEVEMENT_NAME_GROUP_ID")) {
                message = "Achievement with given name already exists in this group!";
            } else if (exceptionMsg.contains("UK_TECHNICAL_SKILL_NAME")) {
                message = "Technical skill with given name already exists!";
            }

            return ResponseDto.builder().success(false).message(message).build()
                    .toResponse(httpStatus);
        }
        return ResponseDto.builder().success(false).message("Unknown error").build()
                .toResponse(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseDto<Object>> handleConstraintViolationException(
            ConstraintViolationException ex, WebRequest req) {

        String exceptionMsg = ex.getMessage();
        HttpStatus httpStatus = HttpStatus.CONFLICT;
        String message = "Unknown error";
        if (exceptionMsg.contains("UK_USER_USERNAME")) {
            message = "User with given username already exists!";
        } else if (exceptionMsg.contains("UK_USER_EMAIL")) {
            message = "User with given email already exists!";
        } else if (exceptionMsg.contains("UK_DIVISION_DIVISION_NAME")) {
            message = "Division with given name already exists!";
        } else if (exceptionMsg.contains("FK_USER_DIVISION")) {
            message = "Cannot delete this division because it still has active users assigned to it. Please reassign or remove these users first.";
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        } else if (exceptionMsg.contains("UK_ROLE_ROLENAME")) {
            message = "Role with given name already exists!";
        } else if (exceptionMsg.contains("UK_MENU_MENU_NAME")) {
            message = "Menu with given name already exists!";
        } else if (exceptionMsg.contains("FK_ATTITUDE_SKILL_GROUP_ATTITUDE_SKILL")) {
            message = "Cannot delete this group attitude skill because it still has attitude skills assigned to it. Please reassign or remove these attitude skills first.";
        } else if (exceptionMsg.contains("UK_ATTITUDE_SKILL_GROUP_NAME")) {
            message = "Attitude skill with given name already exists in this group!";
        } else if (exceptionMsg.contains("FK_ACHIEVEMENT_GROUP_ACHIEVEMENT")) {
            message = "Cannot delete this group achievement because it still has achievements assigned to it. Please reassign or remove these achievement first.";
        } else if (exceptionMsg.contains("UK_ACHIEVEMENT_NAME_GROUP_ID")) {
            message = "Achievement with given name already exists in this group!";
        } else if (exceptionMsg.contains("UK_TECHNICAL_SKILL_NAME")) {
            message = "Technical skill with given name already exists!";
        } else {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseDto.builder().success(false).message(message).build()
                .toResponse(httpStatus);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<Object>> handleException(Exception ex, WebRequest req) {
        ex.printStackTrace();
        return ResponseDto.builder().success(false).message(ex.getMessage()).build()
                .toResponse(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
