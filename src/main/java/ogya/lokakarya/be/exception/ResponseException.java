package ogya.lokakarya.be.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.UUID;

@Getter
public class ResponseException extends RuntimeException {
    private final HttpStatus httpStatus;

    private static final String NOT_FOUND_FORMAT = "%s with id %s could not be found";

    public ResponseException(String msg, HttpStatus httpStatus) {
        super(msg);
        this.httpStatus = httpStatus;
    }

    private static ResponseException notFoundResponse(String entity, UUID id) {
        return new ResponseException(String.format(NOT_FOUND_FORMAT, entity, id),
                HttpStatus.NOT_FOUND);
    }

    public static ResponseException userNotFound(UUID id) {
        return notFoundResponse("User", id);
    }

    public static ResponseException divisionNotFound(UUID id) {
        return notFoundResponse("Division", id);
    }

    public static ResponseException roleNotFound(UUID id) {
        return notFoundResponse("User", id);
    }
    public static ResponseException devPlanNotFound(UUID id) {
        return notFoundResponse("DevPlan", id);
    }


    public static ResponseException technicalSkillNotFound(UUID id) {
        return notFoundResponse("Technical Skill", id);
    }

    public static ResponseException empTechnicalSkillNotFound(UUID id) {
        return notFoundResponse("Employee Technical Skill", id);
    }

}
