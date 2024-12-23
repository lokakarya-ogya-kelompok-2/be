package ogya.lokakarya.be.dto.technicalskill;

import lombok.Data;
import ogya.lokakarya.be.dto.common.Filter;

@Data
public class TechnicalSkillFilter extends Filter {
    private String nameContains;
    private Boolean enabledOnly = false;
}
