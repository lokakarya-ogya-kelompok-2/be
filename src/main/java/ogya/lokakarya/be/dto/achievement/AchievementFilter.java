package ogya.lokakarya.be.dto.achievement;

import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ogya.lokakarya.be.dto.common.Filter;

@Data
@EqualsAndHashCode(callSuper = false)
public class AchievementFilter extends Filter {
    // name and group name
    private String anyStringFieldContains;
    private String nameContains;
    private List<UUID> groupIds;
    private Boolean enabledOnly = false;
    private Boolean withGroup = false;
}
