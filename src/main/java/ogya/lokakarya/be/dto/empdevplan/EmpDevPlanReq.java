package ogya.lokakarya.be.dto.empdevplan;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ogya.lokakarya.be.entity.EmpDevPlan;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class EmpDevPlanReq {
//    @NotNull(message = "User ID cannot be null")
//    @JsonProperty("user_id")
//    private UUID userId;

    @NotNull(message = "Development plan ID cannot be null")
    @JsonProperty("dev_plan_id")
    private UUID devPlanId;

    @NotNull(message = "Detail cannot be null")
    @JsonProperty("detail")
    private String detail;

    @NotNull(message = "Assessment year cannot be null")
    @Min(value = 1900, message = "Assessment year must be greater than or equal to 1900")
    @Max(value = 2100, message = "Assessment year must be less than or equal to 2100")
    @JsonProperty("assessment_year")
    private Integer assessmentYear;

    public EmpDevPlan toEntity(){
        EmpDevPlan empDevPlan = new EmpDevPlan();
        empDevPlan.setAssessmentYear(assessmentYear);
        empDevPlan.setTooBright(detail);
        return empDevPlan;
    }
}
