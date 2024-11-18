package ogya.lokakarya.be.dto.empattitudeskill;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ogya.lokakarya.be.entity.EmpAchievementSkill;
import ogya.lokakarya.be.entity.EmpAttitudeSkill;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class EmpAttitudeSkillDto {
    @JsonProperty("id")
    private UUID id;

//    @JsonProperty("attitude_skill_id")
//    private AttitudeSkill attitudeSkill;
//
//    @JsonProperty("user_id")
//    private User user;

    @JsonProperty("score")
    private Integer score;

    @JsonProperty("assessment_year")
    private Integer assessmentYear;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("created_by")
    private UUID createdBy;

    @JsonProperty("updated_at")
    private Date updatedAt;

    @JsonProperty("updated_by")
    private UUID updatedBy;

    public EmpAttitudeSkillDto (EmpAttitudeSkill empAttitudeSkill) {
        setId(empAttitudeSkill.getId());
        setScore(empAttitudeSkill.getScore());
        setAssessmentYear(empAttitudeSkill.getAssessmentYear());
        setCreatedAt(empAttitudeSkill.getCreatedAt());
        setCreatedBy(empAttitudeSkill.getCreatedBy());
        setUpdatedAt(empAttitudeSkill.getUpdatedAt());
        setUpdatedBy(empAttitudeSkill.getUpdatedBy());
    }
}
