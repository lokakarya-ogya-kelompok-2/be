package ogya.lokakarya.be.dto.technicalskill;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ogya.lokakarya.be.dto.common.Filter;

@Data
@EqualsAndHashCode(callSuper = true)
public class TechnicalSkillFilter extends Filter {
    private String nameContains;
    private Boolean enabledOnly = false;
}
