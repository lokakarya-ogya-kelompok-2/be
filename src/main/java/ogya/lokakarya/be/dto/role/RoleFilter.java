package ogya.lokakarya.be.dto.role;

import lombok.Data;

@Data
public class RoleFilter {
    private String nameContains;
    private Boolean withMenus = false;
    private Boolean withCreatedBy = false;
    private Boolean withUpdatedBy = false;
}
