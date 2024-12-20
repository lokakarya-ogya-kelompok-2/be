package ogya.lokakarya.be.dto.assessmentsummary;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import lombok.Data;
import ogya.lokakarya.be.exception.ResponseException;

@Data
public class AssessmentSummaryFilter {
    // user.full_name and user.position
    private String anyStringFieldContains;
    private List<UUID> userIds;
    private List<Integer> years;
    private List<UUID> divisionIds;
    private Boolean withCreatedBy = false;
    private Boolean withUpdatedBy = false;
    private Integer pageNumber;
    private Integer pageSize;
    private String sortColumn;
    private String sortMode;

    public void validate() throws ResponseException {
        if (sortMode != null
                && (!sortMode.equalsIgnoreCase("asc") || !sortMode.equalsIgnoreCase("desc"))) {
            throw new ResponseException("sort_mode can only be asc/desc", HttpStatus.BAD_REQUEST);
        }
    }
}
