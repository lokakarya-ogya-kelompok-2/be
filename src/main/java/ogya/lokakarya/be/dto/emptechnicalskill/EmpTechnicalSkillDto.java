package ogya.lokakarya.be.dto.emptechnicalskill;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ogya.lokakarya.be.dto.technicalskill.TechnicalSkillDto;
import ogya.lokakarya.be.dto.user.UserDto;
import ogya.lokakarya.be.entity.EmpTechnicalSkill;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class EmpTechnicalSkillDto {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("user_id")
    private UserDto user;

    @JsonProperty("technical_skill_id")
    private TechnicalSkillDto technicalSkill;

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

    public EmpTechnicalSkillDto (EmpTechnicalSkill empTechnicalSkill) {
        setId(empTechnicalSkill.getId());
        if(empTechnicalSkill.getUser() != null) {
            setUser(new UserDto(empTechnicalSkill.getUser(), false, false, false));
        }
        if(empTechnicalSkill.getTechnicalSkill() != null) {
            setTechnicalSkill(new TechnicalSkillDto(empTechnicalSkill.getTechnicalSkill()));
        }
        setScore(empTechnicalSkill.getScore());
        setAssessmentYear(empTechnicalSkill.getAssessmentYear());
        setCreatedAt(empTechnicalSkill.getCreatedAt());
        setCreatedBy(empTechnicalSkill.getCreatedBy());
        setUpdatedAt(empTechnicalSkill.getUpdatedAt());
        setUpdatedBy(empTechnicalSkill.getUpdatedBy());
    }
}
