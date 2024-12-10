package ogya.lokakarya.be.dto.empsuggestion;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class EmpSuggestionFilter {
    private List<UUID> userIds;
    private List<Integer> years;
    private Boolean withCreatedBy;
    private Boolean withUpdatedBy;
}
