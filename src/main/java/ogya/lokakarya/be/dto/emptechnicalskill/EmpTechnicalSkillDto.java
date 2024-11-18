package ogya.lokakarya.be.dto.emptechnicalskill;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
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

//    @JsonProperty("user_id")
//    private User user;
//
//    @JsonProperty("technical_skill_id")
//    private TechnicalSkill technicalSkill;

    @JsonProperty("score")
    private Integer score;

    @JsonProperty("assessment_year")
    private Integer assessmentYear;

    @JsonProperty("created_at")
    private Date createdAt = new Date();

    @JsonProperty("created_by")
    private UUID createdBy;

    @JsonProperty("updated_at")
    private Date updatedAt;

    @JsonProperty("updated_by")
    private UUID updatedBy;

    public EmpTechnicalSkillDto (EmpTechnicalSkill empTechnicalSkill) {
        setId(empTechnicalSkill.getId());
        setScore(empTechnicalSkill.getScore());
        setAssessmentYear(empTechnicalSkill.getAssessmentYear());
        setCreatedAt(empTechnicalSkill.getCreatedAt());
        setCreatedBy(empTechnicalSkill.getCreatedBy());
        setUpdatedAt(empTechnicalSkill.getUpdatedAt());
        setUpdatedBy(empTechnicalSkill.getUpdatedBy());
    }
}
