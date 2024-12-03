package ogya.lokakarya.be.dto.emptechnicalskill;

import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ogya.lokakarya.be.entity.EmpTechnicalSkill;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class EmpTechnicalSkillReq {
    // @NotNull(message = "User ID x be null")
    // @JsonProperty("user_id")
    // private UUID userId;

    @NotNull(message = "Technical skill ID cannot be null")
    @JsonProperty("technical_skill_id")
    private UUID technicalSkillId;

    @NotNull(message = "Score cannot be null")
    @Min(value = 0, message = "Score must be greater than or equal to 0")
    @Max(value = 100, message = "Score must be less than or equal to 100")
    @JsonProperty("score")
    private Integer score;

    @NotEmpty(message = "detail can't be empty")
    private String detail;

    @NotNull(message = "Assessment year cannot be null")
    @Min(value = 0, message = "Assessment year must be greater than or equal to 0")
    @Max(value = 9999, message = "Assessment year must be less than or equal to 9999")
    @JsonProperty("assessment_year")
    private Integer assessmentYear;

    public EmpTechnicalSkill toEntity() {
        EmpTechnicalSkill empTechnicalSkill = new EmpTechnicalSkill();
        empTechnicalSkill.setScore(score);
        empTechnicalSkill.setAssessmentYear(assessmentYear);
        empTechnicalSkill.setTechnicalSkilLDetail(detail);
        return empTechnicalSkill;
    }
}
