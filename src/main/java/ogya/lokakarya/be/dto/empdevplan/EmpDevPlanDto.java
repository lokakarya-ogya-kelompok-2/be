package ogya.lokakarya.be.dto.empdevplan;

import java.util.Date;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ogya.lokakarya.be.dto.devplan.DevPlanDto;
import ogya.lokakarya.be.dto.user.UserDto;
import ogya.lokakarya.be.entity.EmpDevPlan;

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

    @JsonProperty("dev_plan")
    private DevPlanDto devPlan;

    @JsonProperty("detail")
    private String tooBright;

    @JsonProperty("assessment_year")
    private Integer assessmentYear;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("created_by")
    private UserDto createdBy;

    @JsonProperty("updated_at")
    private Date updatedAt;

    @JsonProperty("updated_by")
    private UserDto updatedBy;

    public EmpDevPlanDto(EmpDevPlan empDevPlan, boolean withCreatedBy, boolean withUpdatedBy) {
        setId(empDevPlan.getId());
        if (empDevPlan.getUser() != null) {
            setUser(new UserDto(empDevPlan.getUser(), false, false, false));
        }
        if (empDevPlan.getDevPlan() != null) {
            setDevPlan(new DevPlanDto(empDevPlan.getDevPlan(), false, false));
        }
        setTooBright(empDevPlan.getTooBright());
        setAssessmentYear(empDevPlan.getAssessmentYear());
        setCreatedAt(empDevPlan.getCreatedAt());
        if (withCreatedBy && empDevPlan.getCreatedBy() != null) {
            setCreatedBy(new UserDto(empDevPlan.getCreatedBy(), false, false, false));
        }
        setUpdatedAt(empDevPlan.getUpdatedAt());
        if (withUpdatedBy && empDevPlan.getUpdatedBy() != null) {
            setUpdatedBy(new UserDto(empDevPlan.getUpdatedBy(), false, false, false));
        }
    }
}
