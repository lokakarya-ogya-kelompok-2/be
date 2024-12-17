package ogya.lokakarya.be.dto.attitudeskill;

import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class AttitudeSkillFilter {
    private String nameContains;
    private List<UUID> groupIds;
    private Boolean enabledOnly = false;
    private Boolean withGroup = false;
    private Boolean withCreatedBy = false;
    private Boolean withUpdatedBy = false;
}