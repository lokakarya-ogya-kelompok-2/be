package ogya.lokakarya.be.dto.empachievementskill;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ogya.lokakarya.be.entity.EmpAchievementSkill;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class EmpAchievementSkillReq {
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
