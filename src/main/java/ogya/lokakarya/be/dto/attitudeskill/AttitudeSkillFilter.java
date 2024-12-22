package ogya.lokakarya.be.dto.attitudeskill;

import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ogya.lokakarya.be.dto.common.Filter;

@Data
@EqualsAndHashCode(callSuper = false)
public class AttitudeSkillFilter extends Filter {
    // name & groupname
    private String anyStringFieldContains;
    private String nameContains;
    private List<UUID> groupIds;
    private Boolean enabledOnly = false;
    private Boolean withGroup = false;
}
