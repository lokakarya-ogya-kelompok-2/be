package ogya.lokakarya.be.dto.devplan;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ogya.lokakarya.be.dto.user.UserDto;
import ogya.lokakarya.be.entity.DevPlan;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class DevPlanDto {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("plan")
    private String plan;

    @JsonProperty("enabled")
    private boolean enabled = true;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("created_by")
    private UserDto createdBy;

    @JsonProperty("updated_at")
    private Date updatedAt;

    @JsonProperty("updated_by")
    private UserDto updatedBy;

    public DevPlanDto(DevPlan devPlan) {
        setId(devPlan.getId());
        setPlan(devPlan.getPlan());
        setEnabled(devPlan.isEnabled());
        setCreatedAt(devPlan.getCreatedAt());
        if (devPlan.getCreatedBy() != null) {
            setCreatedBy(new UserDto(devPlan.getCreatedBy(), false, false, false));
        }
        setUpdatedAt(devPlan.getUpdatedAt());
        if (devPlan.getUpdatedBy() != null) {
            setUpdatedBy(new UserDto(devPlan.getUpdatedBy(), false, false, false));
        }
    }
}
