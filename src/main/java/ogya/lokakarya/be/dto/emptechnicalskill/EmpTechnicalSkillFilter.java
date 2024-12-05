package ogya.lokakarya.be.dto.emptechnicalskill;

import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class EmpTechnicalSkillFilter {
    private List<UUID> userIds;
    private List<Integer> years;
    private Boolean withCreatedBy;
    private Boolean withUpdatedBy;
}
