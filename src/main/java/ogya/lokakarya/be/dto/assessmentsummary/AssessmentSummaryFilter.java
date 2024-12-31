package ogya.lokakarya.be.dto.assessmentsummary;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ogya.lokakarya.be.dto.common.Filter;
import ogya.lokakarya.be.exception.ResponseException;

@Data
@EqualsAndHashCode(callSuper = false)
public class AssessmentSummaryFilter extends Filter {
    // user.full_name and user.position
    private String anyStringFieldContains;
    private List<UUID> userIds;
    private List<Integer> years;
    private List<UUID> divisionIds;
    private Boolean withApprover = false;
    private Integer approvalStatus;

    public void validate() {
        if (years != null && years.stream().anyMatch(year -> year <= 0)) {
            throw new ResponseException("year must be greater than 0", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (approvalStatus != null && (approvalStatus != 0 && approvalStatus != 1)) {
            throw new ResponseException("approval_status can only be 0 or 1", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
