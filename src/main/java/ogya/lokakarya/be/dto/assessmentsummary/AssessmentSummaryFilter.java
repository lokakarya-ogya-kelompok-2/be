package ogya.lokakarya.be.dto.assessmentsummary;

import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class AssessmentSummaryFilter {
    private List<UUID> userIds;
    private List<Integer> years;
    private List<UUID> divisionIds;
    private Boolean withCreatedBy = false;
    private Boolean withUpdatedBy = false;
}
