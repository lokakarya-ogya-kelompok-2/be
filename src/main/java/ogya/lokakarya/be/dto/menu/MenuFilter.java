package ogya.lokakarya.be.dto.menu;

import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class MenuFilter {
    private UUID userId;
    private List<String> roleNames;
    private Boolean withCreatedBy;
    private Boolean withUpdatedBy;
}
