package ogya.lokakarya.be.dto.technicalskill;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ogya.lokakarya.be.entity.TechnicalSkill;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class CreateTechnicalSkill {

    @JsonProperty("technical_skill")
    private String technicalSKill;

    @JsonProperty("enabled")
    private boolean enabled=true;

    public TechnicalSkill toEntity() {
        TechnicalSkill technicalSkill = new TechnicalSkill();
        technicalSkill.setTechnicalSkill(technicalSKill);
        technicalSkill.setEnabled(enabled);
        return technicalSkill;
    }
}
