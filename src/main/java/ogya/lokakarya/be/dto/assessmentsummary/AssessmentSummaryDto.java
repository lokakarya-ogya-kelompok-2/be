package ogya.lokakarya.be.dto.assessmentsummary;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ogya.lokakarya.be.dto.empachievementskill.EmpAchievementSkillDto;
import ogya.lokakarya.be.dto.empattitudeskill.EmpAttitudeSkillDto;
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
        private BigDecimal score;

        @JsonProperty("approval_status")
        private Integer approvalStatus;

        @JsonProperty("approved_at")
        private Date approvedAt;

        @JsonProperty("approver")
        private UserDto approver;

        private List<SummaryData<EmpAchievementSkillDto>> achievements;

        @JsonProperty("attitude_skills")
        private List<SummaryData<EmpAttitudeSkillDto>> attitudeSkills;

        @JsonProperty("created_at")
        private Date createdAt;

        @JsonProperty("created_by")
        private UserDto createdBy;

        @JsonProperty("updated_at")
        private Date updatedAt;

        @JsonProperty("updated_by")
        private UserDto updatedBy;



        public AssessmentSummaryDto(AssessmentSummary assessmentSummary, boolean withApprover,
                        boolean withCreatedBy, boolean withUpdatedBy) {
                setId(assessmentSummary.getId());
                if (assessmentSummary.getUser() != null) {
                        setUser(new UserDto(assessmentSummary.getUser(), false, false, false));
                }
                setYear(assessmentSummary.getYear());
                setScore(assessmentSummary.getScore());
                setCreatedAt(assessmentSummary.getCreatedAt());
                if (withCreatedBy && assessmentSummary.getCreatedBy() != null)
                        setCreatedBy(new UserDto(assessmentSummary.getCreatedBy(), false, false,
                                        false));
                setUpdatedAt(assessmentSummary.getUpdatedAt());
                if (withUpdatedBy && assessmentSummary.getUpdatedBy() != null)
                        setUpdatedBy(new UserDto(assessmentSummary.getUpdatedBy(), false, false,
                                        false));
                if (withApprover && assessmentSummary.getApprover() != null)
                        setApprover(new UserDto(assessmentSummary.getApprover(), false, false,
                                        false));
                setApprovalStatus(assessmentSummary.getApprovalStatus());
                setApprovedAt(assessmentSummary.getApprovedAt());
        }
}
