package ogya.lokakarya.be.dto.empdevplan;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("user_id")
    private UUID userId;

    @JsonProperty("dev_plan_id")
    private UUID devPlanId;

    @JsonProperty("assessment_year")
    private Integer assessmentYear;

    public EmpDevPlan toEntity(){
        EmpDevPlan empDevPlan = new EmpDevPlan();
        empDevPlan.setAssessmentYear(assessmentYear);
        return empDevPlan;
    }
}
