package ogya.lokakarya.be.dto.attitudeSkill;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ogya.lokakarya.be.entity.AttitudeSkill;
import ogya.lokakarya.be.entity.GroupAttitudeSkill;
import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class AttitudeSkillDto {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("attitude_skill")
    private String attitudeSkill;

    @JsonProperty("group_id")
    private GroupAttitudeSkill groupAttitudeSkill;

    @JsonProperty("enabled")
    private Boolean enabled = true;

    @JsonProperty("created_at")
    private Date createdAt = Date.valueOf(LocalDate.now());

    @JsonProperty("created_by")
    private UUID createdBy;

    @JsonProperty("updated_at")
    private Date updatedAt;

    @JsonProperty("updated_by")
    private UUID updatedBy;

    public AttitudeSkillDto (AttitudeSkill attitudeSkill) {
        setId(attitudeSkill.getId());
        setAttitudeSkill(attitudeSkill.getAttitudeSkill());
        setGroupAttitudeSkill(attitudeSkill.getGroupAttitudeSkill());
    }
}
