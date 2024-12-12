package ogya.lokakarya.be.dto.technicalskill;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ogya.lokakarya.be.entity.TechnicalSkill;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class TechnicalSkillReq {
    @NotBlank(message = "Technical skill name cannot be blank")
    @Size(max = 100, message = "Technical skill name must be less than or equal to 100 characters")
    @JsonProperty("technical_skill")
    private String technicalSKill;

    @NotNull(message = "Enabled status cannot be null")
    @JsonProperty("enabled")
    private Boolean enabled;

    public TechnicalSkill toEntity() {
        TechnicalSkill technicalSkill = new TechnicalSkill();
        technicalSkill.setName(technicalSKill);
        technicalSkill.setEnabled(enabled);
        return technicalSkill;
    }
}
