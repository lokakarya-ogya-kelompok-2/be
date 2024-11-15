package ogya.lokakarya.be.dto.attitudeSkill;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ogya.lokakarya.be.entity.AttitudeSkill;
import ogya.lokakarya.be.entity.GroupAttitudeSkill;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class CreateAttitudeSkill {
    @JsonProperty("attitude_skill")
    private String attitudeSkill;

//    @JsonProperty("group_id")
//    private GroupAttitudeSkill groupAttitudeSkill;

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
