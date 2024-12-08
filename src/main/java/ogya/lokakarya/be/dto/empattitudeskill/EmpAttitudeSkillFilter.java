package ogya.lokakarya.be.dto.empattitudeskill;

import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class EmpAttitudeSkillFilter {
    private List<UUID> userIds;
    private List<Integer> years;
    private Boolean withCreatedBy;
    private Boolean withUpdatedBy;
}
