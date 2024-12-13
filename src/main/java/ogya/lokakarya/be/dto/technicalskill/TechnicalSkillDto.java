package ogya.lokakarya.be.dto.technicalskill;

import java.util.Date;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ogya.lokakarya.be.dto.user.UserDto;
import ogya.lokakarya.be.entity.TechnicalSkill;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class TechnicalSkillDto {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("technical_skill")
    private String technicalSKill;

    @JsonProperty("enabled")
    private boolean enabled = true;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("created_by")
    private UserDto createdBy;

    @JsonProperty("updated_at")
    private Date updatedAt;

    @JsonProperty("updated_by")
    private UserDto updatedBy;

    public TechnicalSkillDto(TechnicalSkill technicalSkill) {
        setId(technicalSkill.getId());
        setTechnicalSKill(technicalSkill.getName());
        setEnabled(technicalSkill.getEnabled());
        setCreatedAt(technicalSkill.getCreatedAt());
        if (technicalSkill.getCreatedBy() != null) {
            setCreatedBy(new UserDto(technicalSkill.getCreatedBy(), false, false, false));
        }
        setUpdatedAt(technicalSkill.getUpdatedAt());
        if (technicalSkill.getUpdatedBy() != null) {
            setUpdatedBy(new UserDto(technicalSkill.getUpdatedBy(), false, false, false));
        }
    }
}
