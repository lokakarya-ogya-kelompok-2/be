package ogya.lokakarya.be.dto.achievement;

import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class AchievementFilter {
    // name and group name
    private String anyStringFieldContains;
    private String nameContains;
    private List<UUID> groupIds;
    private Boolean enabledOnly = false;
    private Boolean withGroup = false;
    private Boolean withCreatedBy = false;
    private Boolean withUpdatedBy = false;
    private Integer pageNumber;
    private Integer pageSize;
}
