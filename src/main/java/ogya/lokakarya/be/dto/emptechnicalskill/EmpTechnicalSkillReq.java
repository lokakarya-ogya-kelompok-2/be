package ogya.lokakarya.be.dto.emptechnicalskill;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ogya.lokakarya.be.entity.EmpTechnicalSkill;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class EmpTechnicalSkillReq {
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
