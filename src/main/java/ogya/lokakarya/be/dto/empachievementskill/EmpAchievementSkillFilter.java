package ogya.lokakarya.be.dto.empachievementskill;

import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class EmpAchievementSkillFilter {
    private List<UUID> userIds;
    private List<Integer> years;
    private Boolean withCreatedBy;
    private Boolean withUpdatedBy;
}
