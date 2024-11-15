package ogya.lokakarya.be.dto.assessmentSummary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ogya.lokakarya.be.entity.AssessmentSummary;
import ogya.lokakarya.be.entity.User;
import java.util.Date;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class AssessmentSummaryDto {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("user_id")
    private User user;

    @JsonProperty("year")
    private Integer year;

    @JsonProperty("score")
    private Integer score;

    @JsonProperty("status")
    private Integer status;

    @JsonProperty("created_at")
    private Date createdAt = new Date();

    @JsonProperty("CREATED_BY")
    private UUID createdBy;

    @JsonProperty("UPDATED_AT")
    private Date updatedAt;

    @JsonProperty("UPDATED_BY")
    private UUID updatedBy;

    public AssessmentSummaryDto(AssessmentSummary assessmentSummary) {
        setId(assessmentSummary.getId());
        setUser(assessmentSummary.getUser());
        setYear(assessmentSummary.getYear());
        setScore(assessmentSummary.getScore());
        setStatus(assessmentSummary.getStatus());
        setCreatedAt(assessmentSummary.getCreatedAt());
        setCreatedBy(assessmentSummary.getCreatedBy());
        setUpdatedAt(assessmentSummary.getUpdatedAt());
        setUpdatedBy(assessmentSummary.getUpdatedBy());
    }
}
