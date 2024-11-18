package ogya.lokakarya.be.dto.empdevplan;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ogya.lokakarya.be.entity.EmpDevPlan;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class EmpDevPlanReq {
    @JsonProperty("assessment_year")
    private Integer assessmentYear;

    public EmpDevPlan toEntity(){
        EmpDevPlan empDevPlan = new EmpDevPlan();
        empDevPlan.setAssessmentYear(assessmentYear);
        return empDevPlan;
    }
}
