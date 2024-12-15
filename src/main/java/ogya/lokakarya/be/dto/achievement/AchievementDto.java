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
import ogya.lokakarya.be.dto.user.UserDto;
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
    private UserDto createdBy;

    @JsonProperty("updated_at")
    private Date updatedAt;

    @JsonProperty("updated_by")
    private UserDto updatedBy;

    public AchievementDto(Achievement achievement, boolean withCreatedBy, boolean withUpdatedBy,
            boolean withGroupAchievement) {
        setId(achievement.getId());
        setAchievement(achievement.getName());
        if (withGroupAchievement && achievement.getGroupAchievement() != null) {
            setGroupAchievement(new GroupAchievementDto(achievement.getGroupAchievement(), false,
                    false, false));
        }
        setEnabled(achievement.getEnabled());
        setCreatedAt(achievement.getCreatedAt());
        if (withCreatedBy && achievement.getCreatedBy() != null)
            setCreatedBy(new UserDto(achievement.getCreatedBy(), false, false, false));
        setUpdatedAt(achievement.getUpdatedAt());
        if (withUpdatedBy && achievement.getUpdatedBy() != null)
            setUpdatedBy(new UserDto(achievement.getUpdatedBy(), false, false, false));
    }
}
