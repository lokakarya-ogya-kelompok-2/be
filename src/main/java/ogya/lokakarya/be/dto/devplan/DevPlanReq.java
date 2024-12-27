package ogya.lokakarya.be.dto.devplan;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ogya.lokakarya.be.entity.DevPlan;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class DevPlanReq {
    @NotBlank(message = "Plan name cannot be blank")
    @Size(max = 100, message = "Plan name must be less than or equal to 100 characters")
    @JsonProperty("plan")
    private String plan;

    @NotNull(message = "Enabled status cannot be null")
    @JsonProperty("enabled")
    private Boolean enabled;

    public DevPlan toEntity() {
        DevPlan devPlan = new DevPlan();
        devPlan.setName(plan);
        devPlan.setEnabled(enabled);
        return devPlan;
    }
}
