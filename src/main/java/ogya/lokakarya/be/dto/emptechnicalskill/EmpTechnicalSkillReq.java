package ogya.lokakarya.be.dto.emptechnicalskill;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ogya.lokakarya.be.entity.EmpTechnicalSkill;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class EmpTechnicalSkillReq {
    @JsonProperty("user_id")
    private UUID userId;

    @JsonProperty("technical_skill_id")
    private UUID technicalSkillId;

    @JsonProperty("score")
    private Integer score;

    @JsonProperty("assessment_year")
    private Integer assessmentYear;

    public EmpTechnicalSkill toEntity(){
        EmpTechnicalSkill empTechnicalSkill= new EmpTechnicalSkill();
        empTechnicalSkill.setScore(score);
        empTechnicalSkill.setAssessmentYear(assessmentYear);
        return empTechnicalSkill;
    }
}
