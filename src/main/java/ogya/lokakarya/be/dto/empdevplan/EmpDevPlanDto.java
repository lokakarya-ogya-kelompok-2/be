package ogya.lokakarya.be.dto.empdevplan;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import ogya.lokakarya.be.entity.DevPlan;
import ogya.lokakarya.be.entity.EmpDevPlan;
import ogya.lokakarya.be.entity.User;

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

//    @JsonProperty("user_id")
//    private User user;
//
//    @JsonProperty("dev_plan_id")
//    private DevPlan devPlan;

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
        setAssessmentYear(empDevPlan.getAssessmentYear());
        setCreatedAt(empDevPlan.getCreatedAt());
        setCreatedBy(empDevPlan.getCreatedBy());
        setUpdatedAt(empDevPlan.getUpdatedAt());
        setUpdatedBy(empDevPlan.getUpdatedBy());
    }
}
