package ogya.lokakarya.be.dto.devPlan;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ogya.lokakarya.be.entity.DevPlan;
import java.util.Date;
import java.util.UUID;

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
    private boolean enabled=true;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("created_by")
    private UUID createdBy;

    @JsonProperty("updated_at")
    private Date updatedAt;

    @JsonProperty("updated_by")
    private UUID updatedBy;

    public DevPlanDto(DevPlan devPlan) {
        setId(devPlan.getId());
        setPlan(devPlan.getPlan());
        setEnabled(devPlan.isEnabled());
        setCreatedAt(devPlan.getCreatedAt());
        setCreatedBy(devPlan.getCreatedBy());
        setUpdatedAt(devPlan.getUpdatedAt());
        setUpdatedBy(devPlan.getUpdatedBy());
    }
}
