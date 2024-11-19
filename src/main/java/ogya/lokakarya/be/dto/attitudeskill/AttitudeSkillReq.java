package ogya.lokakarya.be.dto.attitudeskill;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ogya.lokakarya.be.entity.AttitudeSkill;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class AttitudeSkillReq {
    @JsonProperty("attitude_skill")
    private String attitudeSkill;

    @JsonProperty("group_id")
    private UUID groupAttitudeSkill;

    @JsonProperty("enabled")
    private Boolean enabled = true;

    public AttitudeSkill toEntity(){
        AttitudeSkill attitudeSkilll = new AttitudeSkill();
        attitudeSkilll.setAttitudeSkill(attitudeSkill);
        attitudeSkilll.setEnabled(enabled);
//        attitudeSkilll.setGroupAttitudeSkill(groupAttitudeSkill);
        return attitudeSkilll;
    }
}
