package ogya.lokakarya.be.dto.groupachievement;

import org.springframework.http.HttpStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ogya.lokakarya.be.dto.common.Filter;
import ogya.lokakarya.be.exception.ResponseException;

@Data
@EqualsAndHashCode(callSuper = false)
public class GroupAchievementFilter extends Filter {
    private String nameContains;
    private Integer minWeight;
    private Integer maxWeight;
    private Boolean enabledOnly = false;
    private Boolean withAchievements = false;
    private Boolean withEnabledChildOnly = false;

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
        if (withEnabledChildOnly != null && withEnabledChildOnly.booleanValue()
                && (withAchievements == null
                        || (withAchievements != null && !withAchievements.booleanValue()))) {
            throw new ResponseException(
                    "with_achievements must be set to true if with_enabled_child_only is true!",
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
