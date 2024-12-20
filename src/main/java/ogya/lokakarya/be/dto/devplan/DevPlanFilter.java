package ogya.lokakarya.be.dto.devplan;

import lombok.Data;

@Data
public class DevPlanFilter {
    private String nameContains;
    private Boolean enabledOnly = false;
    private Boolean withCreatedBy = false;
    private Boolean withUpdatedBy = false;
    private Integer pageNumber;
    private Integer pageSize;
}
