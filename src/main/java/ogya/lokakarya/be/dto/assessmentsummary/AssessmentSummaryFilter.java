package ogya.lokakarya.be.dto.assessmentsummary;

import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ogya.lokakarya.be.dto.common.Filter;

@Data
@EqualsAndHashCode(callSuper = false)
public class AssessmentSummaryFilter extends Filter {
    // user.full_name and user.position
    private String anyStringFieldContains;
    private List<UUID> userIds;
    private List<Integer> years;
    private List<UUID> divisionIds;
    private Boolean withApprover = false;
}
