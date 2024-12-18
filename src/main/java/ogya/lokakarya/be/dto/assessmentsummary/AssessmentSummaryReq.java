package ogya.lokakarya.be.dto.assessmentsummary;

import java.math.BigDecimal;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ogya.lokakarya.be.entity.AssessmentSummary;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class AssessmentSummaryReq {
    @JsonIgnore
    private UUID id;

    @NotNull(message = "User ID cannot be null")
    @JsonProperty("user_id")
    private UUID userId;

    @NotNull(message = "Year cannot be null")
    @Min(value = 1900, message = "Year must be greater than or equal to 1900")
    @Max(value = 2100, message = "Year must be less than or equal to 2100")
    @JsonProperty("year")
    private Integer year;

    @NotNull(message = "Score cannot be null")
    @Min(value = 0, message = "Score must be greater than or equal to 0")
    @Max(value = 100, message = "Score must be less than or equal to 100")
    @JsonProperty("score")
    private BigDecimal score;

    @NotNull(message = "Status cannot be null")
    @Min(value = 1, message = "Status must be 1 (kontrak) or 2 (permanen)")
    @Max(value = 2, message = "Status must be 1 (kontrak) or 2 (permanen)")
    @JsonProperty("status")
    private Integer status;

    public AssessmentSummary toEntity() {
        AssessmentSummary assessmentSummary = new AssessmentSummary();
        assessmentSummary.setYear(year);
        assessmentSummary.setScore(score);
        assessmentSummary.setStatus(status);
        return assessmentSummary;
    }
}
