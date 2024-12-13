package ogya.lokakarya.be.dto.groupattitudeskill;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ogya.lokakarya.be.dto.attitudeskill.AttitudeSkillDto;
import ogya.lokakarya.be.dto.user.UserDto;
import ogya.lokakarya.be.entity.GroupAttitudeSkill;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class GroupAttitudeSkillDto {
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
    private UserDto createdBy;

    @JsonProperty("update_at")
    private Date updatedAt;

    @JsonProperty("updated_by")
    private UserDto updatedBy;

    @JsonProperty("attitude_skills")
    private List<AttitudeSkillDto> attitudeSkills;

    public GroupAttitudeSkillDto(GroupAttitudeSkill groupAttitudeSkill,
            boolean withAttitudeSkills) {
        setId(groupAttitudeSkill.getId());
        setGroupName(groupAttitudeSkill.getGroupName());
        setPercentage(groupAttitudeSkill.getPercentage());
        setEnabled(groupAttitudeSkill.getEnabled());
        setCreatedAt(groupAttitudeSkill.getCreatedAt());
        if (groupAttitudeSkill.getCreatedBy() != null)
            setCreatedBy(new UserDto(groupAttitudeSkill.getCreatedBy(), false, false, false));
        setUpdatedAt(groupAttitudeSkill.getUpdatedAt());
        if (groupAttitudeSkill.getUpdatedBy() != null)
            setUpdatedBy(new UserDto(groupAttitudeSkill.getUpdatedBy(), false, false, false));
        List<AttitudeSkillDto> attitudeSkillDtos = new ArrayList<>();
        if (withAttitudeSkills && groupAttitudeSkill.getAttitudeSkills() != null) {
            groupAttitudeSkill.getAttitudeSkills().forEach(attitudeSkill -> attitudeSkillDtos
                    .add(new AttitudeSkillDto(attitudeSkill, false, false, false)));
        }
        setAttitudeSkills(attitudeSkillDtos);
    }
}
