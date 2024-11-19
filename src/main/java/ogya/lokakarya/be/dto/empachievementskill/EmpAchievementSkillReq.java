package ogya.lokakarya.be.dto.empachievementskill;

import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ogya.lokakarya.be.entity.EmpAchievementSkill;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class EmpAchievementSkillReq {
    @NotNull(message = "User ID cannot be null")
    @JsonProperty("user_id")
    private UUID userId;

    @NotNull(message = "Achievement ID cannot be null")
    @JsonProperty("achievement_id")
    private UUID achievementId;

    @NotBlank(message = "Notes cannot be blank")
    @Size(max = 100, message = "Notes must be less than or equal to 100 characters")
    @JsonProperty("notes")
    private String notes;

    @NotNull(message = "Score cannot be null")
    @Min(value = 0, message = "Score must be greater than or equal to 0")
    @Max(value = 100, message = "Score must be less than or equal to 100")
    @JsonProperty("score")
    private Integer score;

    @NotNull(message = "Assessment year cannot be null")
    @Min(value = 1900, message = "Assessment Year must not be earlier than 1900")
    @Max(value = 2100, message = "Assessment Year must not be later than 2100")
    @JsonProperty("assessment_year")
    private Integer assessmentYear;

    public EmpAchievementSkill toEntity() {
        EmpAchievementSkill empAchievementSkill = new EmpAchievementSkill();
        empAchievementSkill.setNotes(notes);
        empAchievementSkill.setScore(score);
        empAchievementSkill.setAssessmentYear(assessmentYear);
        return empAchievementSkill;
    }
}
