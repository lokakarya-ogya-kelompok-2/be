package ogya.lokakarya.be.dto.assessmentsummary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ogya.lokakarya.be.entity.AssessmentSummary;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class AssessmentSummaryReq {
    @JsonProperty("user_id")
    private UUID userId;

    @JsonProperty("year")
    private Integer year;

    @JsonProperty("score")
    private Integer score;

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
