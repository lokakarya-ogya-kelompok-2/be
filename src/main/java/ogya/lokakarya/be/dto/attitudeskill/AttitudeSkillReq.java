package ogya.lokakarya.be.dto.attitudeskill;

import java.util.UUID;
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
    private UUID groupAttitudeSkillId;

    @NotNull(message = "Enabled status must be provided")
    @JsonProperty("enabled")
    private Boolean enabled;

    public AttitudeSkill toEntity() {
        AttitudeSkill attitudeSkilll = new AttitudeSkill();
        attitudeSkilll.setName(attitudeSkill);
        attitudeSkilll.setEnabled(enabled);
        return attitudeSkilll;
    }
}
