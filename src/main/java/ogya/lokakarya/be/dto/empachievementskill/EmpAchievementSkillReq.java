package ogya.lokakarya.be.dto.empachievementskill;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ogya.lokakarya.be.entity.EmpAchievementSkill;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class EmpAchievementSkillReq {
    @JsonProperty("user_id")
    private UUID userId;

    @JsonProperty("achievement_id")
    private UUID achievementId;

    @JsonProperty("notes")
    private String notes;

    @JsonProperty("score")
    private Integer score;

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
