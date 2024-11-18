package ogya.lokakarya.be.dto.assessmentsummary;

import java.util.Date;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ogya.lokakarya.be.dto.user.UserDto;
import ogya.lokakarya.be.entity.AssessmentSummary;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class AssessmentSummaryDto {
        @JsonProperty("id")
        private UUID id;

        @JsonProperty("user_id")
        private UserDto user;

        @JsonProperty("year")
        private Integer year;

        @JsonProperty("score")
        private Integer score;

        @JsonProperty("status")
        private Integer status;

        @JsonProperty("created_at")
        private Date createdAt;

        @JsonProperty("CREATED_BY")
        private UUID createdBy;

        @JsonProperty("UPDATED_AT")
        private Date updatedAt;

        @JsonProperty("UPDATED_BY")
        private UUID updatedBy;

        public AssessmentSummaryDto(AssessmentSummary assessmentSummary) {
                setId(assessmentSummary.getId());
                if (assessmentSummary.getUser() != null) {
                        setUser(new UserDto(assessmentSummary.getUser(), false, false, false));
                }
                setYear(assessmentSummary.getYear());
                setScore(assessmentSummary.getScore());
                setStatus(assessmentSummary.getStatus());
                setCreatedAt(assessmentSummary.getCreatedAt());
                setCreatedBy(assessmentSummary.getCreatedBy());
                setUpdatedAt(assessmentSummary.getUpdatedAt());
                setUpdatedBy(assessmentSummary.getUpdatedBy());
        }
}
