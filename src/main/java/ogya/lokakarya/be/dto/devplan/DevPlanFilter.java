package ogya.lokakarya.be.dto.devplan;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ogya.lokakarya.be.dto.common.Filter;

@Data
@EqualsAndHashCode(callSuper = false)
public class DevPlanFilter extends Filter {
    private String nameContains;
    private Boolean enabledOnly = false;
}
