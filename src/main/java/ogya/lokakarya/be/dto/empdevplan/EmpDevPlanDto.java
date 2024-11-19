package ogya.lokakarya.be.dto.empdevplan;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ogya.lokakarya.be.dto.devplan.DevPlanDto;
import ogya.lokakarya.be.dto.user.UserDto;
import ogya.lokakarya.be.entity.EmpDevPlan;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class EmpDevPlanDto {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("user_id")
    private UserDto user;

    @JsonProperty("dev_plan_id")
    private DevPlanDto devPlan;

    @JsonProperty("assessment_year")
    private Integer assessmentYear;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("created_by")
    private UUID createdBy;

    @JsonProperty("updated_at")
    private Date updatedAt;

    @JsonProperty("updated_by")
    private UUID updatedBy;

    public EmpDevPlanDto(EmpDevPlan empDevPlan) {
        setId(empDevPlan.getId());
        if(empDevPlan.getUser() != null) {
            setUser(new UserDto(empDevPlan.getUser(), false, false, false));
        }
        if (empDevPlan.getDevPlan() != null) {
            setDevPlan(new DevPlanDto(empDevPlan.getDevPlan()));
        }
        setAssessmentYear(empDevPlan.getAssessmentYear());
        setCreatedAt(empDevPlan.getCreatedAt());
        setCreatedBy(empDevPlan.getCreatedBy());
        setUpdatedAt(empDevPlan.getUpdatedAt());
        setUpdatedBy(empDevPlan.getUpdatedBy());
    }
}
