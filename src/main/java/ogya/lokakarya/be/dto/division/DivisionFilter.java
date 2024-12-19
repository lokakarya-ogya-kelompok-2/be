package ogya.lokakarya.be.dto.division;

import lombok.Data;

@Data
public class DivisionFilter {
    private String nameContains;
    private Integer pageNumber;
    private Integer pageSize;
    private Boolean withCreatedBy = false;
    private Boolean withUpdatedBy = false;
}
