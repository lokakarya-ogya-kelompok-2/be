package ogya.lokakarya.be.dto.attitudeskill;

import java.util.Date;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ogya.lokakarya.be.dto.groupattitudeskill.GroupAttitudeSkillDto;
import ogya.lokakarya.be.dto.user.UserDto;
import ogya.lokakarya.be.entity.AttitudeSkill;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class AttitudeSkillDto {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("attitude_skill")
    private String attitudeSkill;

    @JsonProperty("group_id")
    private GroupAttitudeSkillDto groupAttitudeSkill;

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

    public AttitudeSkillDto(AttitudeSkill attitudeSkill, boolean withGroupAttitudeSkill,
            boolean withCreatedBy, boolean withUpdatedBy) {
        setId(attitudeSkill.getId());
        if (withGroupAttitudeSkill && attitudeSkill.getGroupAttitudeSkill() != null) {
            setGroupAttitudeSkill(
                    new GroupAttitudeSkillDto(attitudeSkill.getGroupAttitudeSkill(), false));
        }
        setAttitudeSkill(attitudeSkill.getName());
        setEnabled(attitudeSkill.getEnabled());
        setCreatedAt(attitudeSkill.getCreatedAt());
        if (withCreatedBy && attitudeSkill.getCreatedBy() != null)
            setCreatedBy(new UserDto(attitudeSkill.getCreatedBy(), false, false, false));
        setUpdatedAt(attitudeSkill.getUpdatedAt());
        if (withUpdatedBy && attitudeSkill.getUpdatedBy() != null)
            setUpdatedBy(new UserDto(attitudeSkill.getUpdatedBy(), false, false, false));
    }
}
