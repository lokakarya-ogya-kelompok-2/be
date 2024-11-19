package ogya.lokakarya.be.dto.groupattitudeskill;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ogya.lokakarya.be.entity.GroupAttitudeSkill;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class GroupAttitudeSkillReq {
    @NotBlank(message = "Group name cannot be empty")
    @Size(max = 100, message = "Group name must not exceed 100 characters")
    @JsonProperty("group_name")
    private String groupName;

    @NotNull(message = "Percentage cannot be null")
    @Min(value = 0, message = "Percentage must be at least 0")
    @Max(value = 100, message = "Percentage must not exceed 100")
    @JsonProperty("percentage")
    private Integer percentage;

    @NotNull(message = "Enabled status must be provided")
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
