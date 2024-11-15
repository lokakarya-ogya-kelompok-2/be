package ogya.lokakarya.be.dto.devPlan;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ogya.lokakarya.be.entity.DevPlan;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class CreateDevPlan {
    @JsonProperty("plan")
    private String plan;

    @JsonProperty("enabled")
    private boolean enabled=true;

    public DevPlan toEntity(){
        DevPlan devPlan = new DevPlan();
        devPlan.setPlan(plan);
        devPlan.setEnabled(enabled);

        return devPlan;
    }
}
