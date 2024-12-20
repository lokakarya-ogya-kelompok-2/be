package ogya.lokakarya.be.dto.technicalskill;

import lombok.Data;

@Data
public class TechnicalSkillFilter {
    private String nameContains;
    private Boolean enabledOnly = false;
    private Boolean withCreatedBy = false;
    private Boolean withUpdatedBy = false;
    private Integer pageNumber;
    private Integer pageSize;
}
