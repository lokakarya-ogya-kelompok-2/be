package ogya.lokakarya.be.dto.achievement;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.*;
import ogya.lokakarya.be.entity.Achievement;
import ogya.lokakarya.be.entity.GroupAchievement;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class AchievementDto {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("achievement")
    private String achievement;

    @JsonProperty("group_id")
    private GroupAchievement groupAchievement;

    @JsonProperty("enabled")
    private Boolean enabled = true;

    @JsonProperty("created_at")
    private Date createdAt = new Date();

    @JsonProperty("created_by")
    private UUID createdBy;

    @JsonProperty("updated_at")
    private Date updatedAt;

    @JsonProperty("updated_by")
    private UUID updatedBy;

    public AchievementDto(Achievement achievement) {
        setId(achievement.getId());
        setAchievement(achievement.getAchievement());
        setEnabled(achievement.getEnabled());
        setCreatedAt(achievement.getCreatedAt());
        setCreatedBy(achievement.getCreatedBy());
        setUpdatedAt(achievement.getUpdatedAt());
        setUpdatedBy(achievement.getUpdatedBy());
    }
}
