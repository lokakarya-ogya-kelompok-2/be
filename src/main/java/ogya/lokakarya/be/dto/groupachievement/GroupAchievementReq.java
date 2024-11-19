package ogya.lokakarya.be.dto.groupachievement;

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
import ogya.lokakarya.be.entity.GroupAchievement;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class GroupAchievementReq {
    @NotBlank(message = "Group name cannot be blank")
    @Size(max = 100, message = "Group name must be less than or equal to 100 characters")
    @JsonProperty("group_name")
    private String groupName;

    @NotNull(message = "Percentage cannot be null")
    @Min(value = 0, message = "Percentage must be greater than or equal to 0")
    @Max(value = 100, message = "Percentage must be less than or equal to 100")
    @JsonProperty("percentage")
    private Integer percentage;

    @NotNull(message = "Enabled status cannot be null")
    @JsonProperty("enabled")
    private Boolean enabled = true;

    public GroupAchievement toEntity(){
        GroupAchievement groupAchievement = new GroupAchievement();
        groupAchievement.setGroupName(groupName);
        groupAchievement.setPercentage(percentage);
        groupAchievement.setEnabled(enabled);
        return groupAchievement;
    }
}
