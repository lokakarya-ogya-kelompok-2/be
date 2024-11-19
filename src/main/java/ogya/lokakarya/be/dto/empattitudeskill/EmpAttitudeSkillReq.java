package ogya.lokakarya.be.dto.empattitudeskill;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ogya.lokakarya.be.entity.EmpAttitudeSkill;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class EmpAttitudeSkillReq {
    @JsonProperty("user_id")
    private UUID userId;

    @JsonProperty("attitude_skill_id")
    private UUID attitudeSkillId;

    @JsonProperty("score")
    private Integer score;

    @JsonProperty("assessment_year")
    private Integer assessmentYear;

    public EmpAttitudeSkill toEntity(){
        EmpAttitudeSkill empAttitudeSkill = new EmpAttitudeSkill();
        empAttitudeSkill.setScore(score);
        empAttitudeSkill.setAssessmentYear(assessmentYear);
        return empAttitudeSkill;
    }
}
