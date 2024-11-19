package ogya.lokakarya.be.dto.attitudeskill;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "Attitude skill cannot be empty")
    @Size(max = 100, message = "Attitude skill must not exceed 100 characters")
    @JsonProperty("attitude_skill")
    private String attitudeSkill;

    @NotNull(message = "Group ID cannot be null")
    @JsonProperty("group_id")
    private UUID groupAttitudeSkill;

    @NotNull(message = "Enabled status must be provided")
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
