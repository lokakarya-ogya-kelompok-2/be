package ogya.lokakarya.be.dto.groupachievement;

import org.springframework.http.HttpStatus;
import lombok.Data;
import ogya.lokakarya.be.exception.ResponseException;

@Data
public class GroupAchievementFilter {
    private String nameContains;
    private Integer minWeight;
    private Integer maxWeight;
    private Boolean enabledOnly = false;
    private Boolean withAchievements = false;
    private Boolean withCreatedBy = false;
    private Boolean withUpdatedBy = false;

    public void validate() throws ResponseException {
        if (minWeight != null && (minWeight < 0 || minWeight > 100)) {
            throw new ResponseException("min_weight must be between 0 and 100",
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (maxWeight != null && (maxWeight < 0 || maxWeight > 100)) {
            throw new ResponseException("min_weight must be between 0 and 100",
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (minWeight != null && maxWeight != null && minWeight > maxWeight) {
            throw new ResponseException("max_weight can't be less than min_weight",
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
