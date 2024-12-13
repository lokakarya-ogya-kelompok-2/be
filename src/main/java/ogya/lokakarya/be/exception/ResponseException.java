package ogya.lokakarya.be.exception;

import java.util.UUID;
import org.springframework.http.HttpStatus;
import lombok.Getter;

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
        return notFoundResponse("Development Plan", id);
    }


    public static ResponseException technicalSkillNotFound(UUID id) {
        return notFoundResponse("Technical Skill", id);
    }

    public static ResponseException empTechnicalSkillNotFound(UUID id) {
        return notFoundResponse("Employee Technical Skill", id);
    }

    public static ResponseException empAttitudeSkillNotFound(UUID id) {
        return notFoundResponse("Employee Attitude Skill", id);
    }

    public static ResponseException empDevPlanNotFound(UUID id) {
        return notFoundResponse("Employee Development Plan", id);
    }

    public static ResponseException empSuggestionNotFound(UUID id) {
        return notFoundResponse("Employee Suggestion", id);
    }

    public static ResponseException empAchievementNotFound(UUID id) {
        return notFoundResponse("Employee Achievement", id);
    }

    public static ResponseException attitudeSkillNotFound(UUID id) {
        return notFoundResponse("Attitude Skill", id);
    }

    public static ResponseException achievementNotFound(UUID id) {
        return notFoundResponse("Achievement", id);
    }

    public static ResponseException groupAchievementNotFound(UUID id) {
        return notFoundResponse("Group Achievement", id);
    }

    public static ResponseException groupAttitudeSkillNotFound(UUID id) {
        return notFoundResponse("Group Attitude Skill", id);
    }

    public static ResponseException assessmentSummaryNotFound(UUID id) {
        return notFoundResponse("Assessment Summary", id);
    }

    public static ResponseException menuNotFound(UUID id) {
        return notFoundResponse("Menu", id);
    }

    public static ResponseException unauthorized() {
        return new ResponseException("You're not allowed to perform this action!",
                HttpStatus.UNAUTHORIZED);
    }
}
