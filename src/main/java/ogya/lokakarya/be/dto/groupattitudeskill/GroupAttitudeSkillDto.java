package ogya.lokakarya.be.dto.groupattitudeskill;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ogya.lokakarya.be.dto.attitudeskill.AttitudeSkillDto;
import ogya.lokakarya.be.entity.GroupAttitudeSkill;

@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    private UUID createdBy;

    @JsonProperty("update_at")
    private Date updatedAt;

    @JsonProperty("updated_by")
    private UUID updatedBy;

    @JsonProperty("attitude_skills")
    private List<AttitudeSkillDto> attitudeSkills;

    public GroupAttitudeSkillDto(GroupAttitudeSkill groupAttitudeSkill,
            boolean withAttitudeSkills) {
        setId(groupAttitudeSkill.getId());
        setGroupName(groupAttitudeSkill.getGroupName());
        setPercentage(groupAttitudeSkill.getPercentage());
        setEnabled(groupAttitudeSkill.getEnabled());
        setCreatedAt(groupAttitudeSkill.getCreatedAt());
        setCreatedBy(groupAttitudeSkill.getCreatedBy());
        setUpdatedAt(groupAttitudeSkill.getUpdatedAt());
        setUpdatedBy(groupAttitudeSkill.getUpdatedBy());
        List<AttitudeSkillDto> attitudeSkillDtos = new ArrayList<>();
        if (withAttitudeSkills && groupAttitudeSkill.getAttitudeSkills() != null) {
            groupAttitudeSkill.getAttitudeSkills().forEach(attitudeSkill -> attitudeSkillDtos
                    .add(new AttitudeSkillDto(attitudeSkill, false, false, false)));
        }
        setAttitudeSkills(attitudeSkillDtos);
    }
}
