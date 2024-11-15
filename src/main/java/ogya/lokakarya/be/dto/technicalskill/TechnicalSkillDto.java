package ogya.lokakarya.be.dto.technicalskill;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ogya.lokakarya.be.entity.TechnicalSkill;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class TechnicalSkillDto {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("technical_skill")
    private String technicalSKill;

    @JsonProperty("enabled")
    private boolean enabled=true;

    @JsonProperty("created_at")
    private Date createdAt = new Date();

    @JsonProperty("created_by")
    private UUID createdBy;

    @JsonProperty("updated_at")
    private Date updatedAt;

    @JsonProperty("updated_by")
    private UUID updatedBy;

    public TechnicalSkillDto(TechnicalSkill technicalSkill) {
        setId(technicalSkill.getId());
        setTechnicalSKill(technicalSkill.getTechnicalSkill());
        setEnabled(technicalSkill.getEnabled());
        setCreatedAt(technicalSkill.getCreatedAt());
        setCreatedBy(technicalSkill.getCreatedBy());
        setUpdatedAt(technicalSkill.getUpdatedAt());
    }
}
