package ogya.lokakarya.be.dto.empattitudeskill;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ogya.lokakarya.be.dto.attitudeskill.AttitudeSkillDto;
import ogya.lokakarya.be.dto.user.UserDto;
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

    @JsonProperty("attitude_skill_id")
    private AttitudeSkillDto attitudeSkill;

    @JsonProperty("user_id")
    private UserDto user;

    @JsonProperty("score")
    private Integer score;

    @JsonProperty("assessment_year")
    private Integer assessmentYear;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("created_by")
    private UserDto createdBy;

    @JsonProperty("updated_at")
    private Date updatedAt;

    @JsonProperty("updated_by")
    private UUID updatedBy;

    public EmpAttitudeSkillDto(EmpAttitudeSkill empAttitudeSkill, Boolean withCreatedBy) {
        setId(empAttitudeSkill.getId());
        if (empAttitudeSkill.getUser() != null) {
            setUser(new UserDto(empAttitudeSkill.getUser(), false, false, false));
        }
        if (empAttitudeSkill.getAttitudeSkill() != null) {
            setAttitudeSkill(
                    new AttitudeSkillDto(empAttitudeSkill.getAttitudeSkill(), false, false, false));
        }
        setScore(empAttitudeSkill.getScore());
        setAssessmentYear(empAttitudeSkill.getAssessmentYear());
        setCreatedAt(empAttitudeSkill.getCreatedAt());
        if (withCreatedBy && empAttitudeSkill.getCreatedBy() != null) {
            setCreatedBy(new UserDto(empAttitudeSkill.getCreatedBy(), false, false, false));
        }
        setUpdatedAt(empAttitudeSkill.getUpdatedAt());
        setUpdatedBy(empAttitudeSkill.getUpdatedBy());
    }
}
