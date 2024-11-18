package ogya.lokakarya.be.dto.groupattitudeskill;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ogya.lokakarya.be.entity.GroupAttitudeSkill;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class GroupAttitudeSkillReq {
    @JsonProperty("group_name")
    private String groupName;

    @JsonProperty("percentage")
    private Integer percentage;

    @JsonProperty("enabled")
    private Boolean enabled = true;

    public GroupAttitudeSkill toEntity(){
        GroupAttitudeSkill groupAttitudeSkill = new GroupAttitudeSkill();
        groupAttitudeSkill.setGroupName(groupName);
        groupAttitudeSkill.setPercentage(percentage);
        groupAttitudeSkill.setEnabled(enabled);
        return groupAttitudeSkill;
    }
}
