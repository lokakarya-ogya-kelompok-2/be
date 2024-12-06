package ogya.lokakarya.be.dto.empattitudeskill;

import java.util.Date;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ogya.lokakarya.be.dto.attitudeskill.AttitudeSkillDto;
import ogya.lokakarya.be.dto.user.UserDto;
import ogya.lokakarya.be.entity.EmpAttitudeSkill;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class EmpAttitudeSkillDto {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("attitude_skill")
    private AttitudeSkillDto attitudeSkill;

    @JsonProperty("user")
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
    private UserDto updatedBy;

    public EmpAttitudeSkillDto(EmpAttitudeSkill empAttitudeSkill, boolean withCreatedBy,
            boolean withUpdatedBy) {
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
        if (withUpdatedBy && empAttitudeSkill.getUpdatedBy() != null) {
            setUpdatedBy(new UserDto(empAttitudeSkill.getUpdatedBy(), false, false, false));
        }
    }
}
