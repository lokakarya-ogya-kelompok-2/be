package ogya.lokakarya.be.dto.groupAttitudeSkill;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.*;
import ogya.lokakarya.be.entity.GroupAttitudeSkill;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class GroupAttitudeSkillDto {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("group_name")
    private String groupName;

    @JsonProperty("percentage")
    private Integer percentage;

    @JsonProperty("enabled")
    private Boolean enabled = true;

    @JsonProperty("created_at")
    private Date createdAt = Date.valueOf(LocalDate.now());

    @JsonProperty("created_by")
    private UUID createdBy;

    @JsonProperty("update_at")
    private Date updatedAt;

    @JsonProperty("updated_by")
    private UUID updatedBy;

    public GroupAttitudeSkillDto(GroupAttitudeSkill groupAttitudeSkill) {
        setId(groupAttitudeSkill.getId());
        setGroupName(groupAttitudeSkill.getGroupName());
        setPercentage(groupAttitudeSkill.getPercentage());
        setEnabled(groupAttitudeSkill.getEnabled());
        setCreatedAt(groupAttitudeSkill.getCreatedAt());
        setCreatedBy(groupAttitudeSkill.getCreatedBy());
        setUpdatedAt(groupAttitudeSkill.getUpdatedAt());
        setUpdatedBy(groupAttitudeSkill.getUpdatedBy());
    }
}
