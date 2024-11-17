package ogya.lokakarya.be.dto.empachievementskill;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ogya.lokakarya.be.entity.EmpAchievementSkill;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class EmpAchievementSkillDto {
    @JsonProperty("id")
    private UUID id;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;

    @JsonProperty("notes")
    private String notes;

//    @ManyToOne
//    @JoinColumn(name = "achievement_id")
//    private Achievement achievement;

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

    public EmpAchievementSkillDto(EmpAchievementSkill achievementSkill) {
        setId(achievementSkill.getId());
        setNotes(achievementSkill.getNotes());
        setScore(achievementSkill.getScore());
        setAssessmentYear(achievementSkill.getAssessmentYear());
        setCreatedAt(achievementSkill.getCreatedAt());
        setCreatedBy(achievementSkill.getCreatedBy());
        setUpdatedAt(achievementSkill.getUpdatedAt());
        setUpdatedBy(achievementSkill.getUpdatedBy());
    }
}
