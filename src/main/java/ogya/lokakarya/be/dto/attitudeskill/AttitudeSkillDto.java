package ogya.lokakarya.be.dto.attitudeskill;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ogya.lokakarya.be.dto.groupattitudeskill.GroupAttitudeSkillDto;
import ogya.lokakarya.be.entity.AttitudeSkill;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    private UUID createdBy;

    @JsonProperty("updated_at")
    private Date updatedAt;

    @JsonProperty("updated_by")
    private UUID updatedBy;

    public AttitudeSkillDto (AttitudeSkill attitudeSkill) {
        setId(attitudeSkill.getId());
        if(attitudeSkill.getGroupAttitudeSkill() != null) {
            setGroupAttitudeSkill(new GroupAttitudeSkillDto(attitudeSkill.getGroupAttitudeSkill()));
        }
        setAttitudeSkill(attitudeSkill.getAttitudeSkill());
        setEnabled(attitudeSkill.getEnabled());
        setCreatedAt(attitudeSkill.getCreatedAt());
        setCreatedBy(attitudeSkill.getCreatedBy());
        setUpdatedAt(attitudeSkill.getUpdatedAt());
        setUpdatedBy(attitudeSkill.getUpdatedBy());
    }
}
