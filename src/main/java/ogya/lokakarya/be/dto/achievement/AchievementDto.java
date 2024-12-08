package ogya.lokakarya.be.dto.achievement;

import java.util.Date;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ogya.lokakarya.be.dto.groupachievement.GroupAchievementDto;
import ogya.lokakarya.be.entity.Achievement;

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
    private GroupAchievementDto groupAchievement;

    @Builder.Default
    @JsonProperty("enabled")
    private Boolean enabled = true;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("created_by")
    private UUID createdBy;

    @JsonProperty("updated_at")
    private Date updatedAt;

    @JsonProperty("updated_by")
    private UUID updatedBy;

    public AchievementDto(Achievement achievement, boolean withGroupAchievement) {
        setId(achievement.getId());
        setAchievement(achievement.getAchievement());
        if (withGroupAchievement && achievement.getGroupAchievement() != null) {
            setGroupAchievement(new GroupAchievementDto(achievement.getGroupAchievement(), false,
                    false, false));
        }
        setEnabled(achievement.getEnabled());
        setCreatedAt(achievement.getCreatedAt());
        setCreatedBy(achievement.getCreatedBy());
        setUpdatedAt(achievement.getUpdatedAt());
        setUpdatedBy(achievement.getUpdatedBy());
    }
}
