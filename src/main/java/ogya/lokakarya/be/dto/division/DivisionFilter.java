package ogya.lokakarya.be.dto.division;

import lombok.Data;

@Data
public class DivisionFilter {
    private String nameContains;
    private Boolean withCreatedBy = false;
    private Boolean withUpdatedBy = false;
}
