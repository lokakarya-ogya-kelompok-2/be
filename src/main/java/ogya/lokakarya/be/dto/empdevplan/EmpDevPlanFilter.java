package ogya.lokakarya.be.dto.empdevplan;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class EmpDevPlanFilter {
    private List<UUID> userIds;
    private List<Integer> years;
    private Boolean withCreatedBy;
    private Boolean withUpdatedBy;
}
