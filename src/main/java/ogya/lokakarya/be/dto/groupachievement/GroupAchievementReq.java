package ogya.lokakarya.be.dto.groupachievement;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ogya.lokakarya.be.entity.GroupAchievement;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class GroupAchievementReq {
    @JsonProperty("group_name")
    private String groupName;

    @JsonProperty("percentage")
    private Integer percentage;

    @JsonProperty("enabled")
    private Boolean enabled = true;

    public GroupAchievement toEntity(){
        GroupAchievement groupAchievement = new GroupAchievement();
        groupAchievement.setGroupName(groupName);
        groupAchievement.setPercentage(percentage);
        groupAchievement.setEnabled(enabled);
        return groupAchievement;
    }
}
