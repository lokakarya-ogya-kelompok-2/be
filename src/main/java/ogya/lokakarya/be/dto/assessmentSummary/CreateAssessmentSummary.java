package ogya.lokakarya.be.dto.assessmentSummary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ogya.lokakarya.be.entity.AssessmentSummary;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class CreateAssessmentSummary {
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
