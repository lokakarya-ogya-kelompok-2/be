package ogya.lokakarya.be.dto.division;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ogya.lokakarya.be.dto.common.Filter;

@Data
@EqualsAndHashCode(callSuper=false)
public class DivisionFilter extends Filter {
    private String nameContains;
}
