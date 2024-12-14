package ogya.lokakarya.be.dto.emptechnicalskill;

import java.util.Date;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ogya.lokakarya.be.dto.technicalskill.TechnicalSkillDto;
import ogya.lokakarya.be.dto.user.UserDto;
import ogya.lokakarya.be.entity.EmpTechnicalSkill;

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

    @JsonProperty("technical_skill")
    private TechnicalSkillDto technicalSkill;

    @JsonProperty("score")
    private Integer score;

    private String detail;

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

    public EmpTechnicalSkillDto(EmpTechnicalSkill empTechnicalSkill, boolean withCreatedBy,
            boolean withUpdatedBy) {
        setId(empTechnicalSkill.getId());
        if (empTechnicalSkill.getUser() != null) {
            setUser(new UserDto(empTechnicalSkill.getUser(), false, false, false));
        }
        if (empTechnicalSkill.getTechnicalSkill() != null) {
            setTechnicalSkill(
                    new TechnicalSkillDto(empTechnicalSkill.getTechnicalSkill(), false, false));
        }
        setDetail(empTechnicalSkill.getTechnicalSkilLDetail());
        setScore(empTechnicalSkill.getScore());
        setAssessmentYear(empTechnicalSkill.getAssessmentYear());
        setCreatedAt(empTechnicalSkill.getCreatedAt());
        if (withCreatedBy && empTechnicalSkill.getCreatedBy() != null) {
            setCreatedBy(new UserDto(empTechnicalSkill.getCreatedBy(), false, false, false));
        }
        setUpdatedAt(empTechnicalSkill.getUpdatedAt());
        if (withUpdatedBy && empTechnicalSkill.getUpdatedBy() != null) {
            setUpdatedBy(new UserDto(empTechnicalSkill.getUpdatedBy(), false, false, false));
        }
    }
}
