package ogya.lokakarya.be.dto.achievement;

import lombok.Data;

@Data
public class AchievementFilter {
    private String nameContains;
    private Boolean enabledOnly = false;
    private Boolean withGroup = false;
    private Boolean withCreatedBy = false;
    private Boolean withUpdatedBy = false;
}
