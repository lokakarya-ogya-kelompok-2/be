package ogya.lokakarya.be.dto.empachievementskill;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ogya.lokakarya.be.dto.achievement.AchievementDto;
import ogya.lokakarya.be.dto.user.UserDto;
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

    @JsonProperty("user_id")
    private UserDto user;

    @JsonProperty("notes")
    private String notes;

    @JsonProperty("achievement_id")
    private AchievementDto achievement;

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
        if(achievementSkill.getUser() != null) {
            setUser(new UserDto(achievementSkill.getUser(), false, false, false));
        }
        if(achievementSkill.getAchievement() != null) {
            setAchievement(new AchievementDto(achievementSkill.getAchievement()));
        }
        setNotes(achievementSkill.getNotes());
        setScore(achievementSkill.getScore());
        setAssessmentYear(achievementSkill.getAssessmentYear());
        setCreatedAt(achievementSkill.getCreatedAt());
        setCreatedBy(achievementSkill.getCreatedBy());
        setUpdatedAt(achievementSkill.getUpdatedAt());
        setUpdatedBy(achievementSkill.getUpdatedBy());
    }
}
