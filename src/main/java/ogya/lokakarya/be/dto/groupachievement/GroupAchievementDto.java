package ogya.lokakarya.be.dto.groupachievement;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ogya.lokakarya.be.entity.GroupAchievement;
import java.util.Date;
import java.util.UUID;

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

    public GroupAchievementDto(GroupAchievement groupAchievement) {
        setId(groupAchievement.getId());
        setGroupName(groupAchievement.getGroupName());
        setPercentage(groupAchievement.getPercentage());
        setEnabled(groupAchievement.getEnabled());
        setCreatedAt(groupAchievement.getCreatedAt());
        setCreatedBy(groupAchievement.getCreatedBy());
        setUpdatedAt(groupAchievement.getUpdatedAt());
        setUpdatedBy(groupAchievement.getUpdatedBy());
    }
}
