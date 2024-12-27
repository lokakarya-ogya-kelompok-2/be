package ogya.lokakarya.be.dto.groupachievement;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ogya.lokakarya.be.dto.achievement.AchievementDto;
import ogya.lokakarya.be.dto.user.UserDto;
import ogya.lokakarya.be.entity.GroupAchievement;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class GroupAchievementDto {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("group_name")
    private String groupName;

    @JsonProperty("percentage")
    private Integer percentage;

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

    private List<AchievementDto> achievements;

    public GroupAchievementDto(GroupAchievement groupAchievement, boolean withCreatedBy,
            boolean withUpdatedBy, boolean withAchievements, boolean withEnabledChildOnly) {
        setId(groupAchievement.getId());
        setGroupName(groupAchievement.getName());
        setPercentage(groupAchievement.getWeight());
        setEnabled(groupAchievement.getEnabled());
        setCreatedAt(groupAchievement.getCreatedAt());
        if (withCreatedBy && groupAchievement.getCreatedBy() != null)
            setCreatedBy(new UserDto(groupAchievement.getCreatedBy(), false, false, false));
        setUpdatedAt(groupAchievement.getUpdatedAt());
        if (withUpdatedBy && groupAchievement.getUpdatedBy() != null)
            setUpdatedBy(new UserDto(groupAchievement.getUpdatedBy(), false, false, false));
        if (withAchievements && groupAchievement.getAchievements() != null)
            setAchievements(groupAchievement.getAchievements().stream().filter(achievement -> {
                if (withEnabledChildOnly) {
                    return achievement.getEnabled().booleanValue();
                }
                return true;
            }).map(achievement -> new AchievementDto(achievement, false, false, false)).toList());
    }
}
