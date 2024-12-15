package ogya.lokakarya.be.dto.attitudeskill;

import lombok.Data;

@Data
public class AttitudeSkillFilter {
    private String nameContains;
    private Boolean enabledOnly = false;
    private Boolean withGroup = false;
    private Boolean withCreatedBy = false;
    private Boolean withUpdatedBy = false;
}
