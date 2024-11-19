package ogya.lokakarya.be.dto.empattitudeskill;

import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ogya.lokakarya.be.entity.EmpAttitudeSkill;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class EmpAttitudeSkillReq {
    @NotNull(message = "User ID cannot be null")
    @JsonProperty("user_id")
    private UUID userId;

    @NotNull(message = "Attitude Skill ID cannot be null")
    @JsonProperty("attitude_skill_id")
    private UUID attitudeSkillId;

    @NotNull(message = "Score cannot be null")
    @Min(value = 0, message = "Score must be at least 0")
    @Max(value = 100, message = "Score must not exceed 100")
    @JsonProperty("score")
    private Integer score;

    @NotNull(message = "Assessment Year cannot be null")
    @Min(value = 1900, message = "Assessment Year must not be earlier than 1900")
    @Max(value = 2100, message = "Assessment Year must not be later than 2100")
    @JsonProperty("assessment_year")
    private Integer assessmentYear;

    public EmpAttitudeSkill toEntity() {
        EmpAttitudeSkill empAttitudeSkill = new EmpAttitudeSkill();
        empAttitudeSkill.setScore(score);
        empAttitudeSkill.setAssessmentYear(assessmentYear);
        return empAttitudeSkill;
    }
}
