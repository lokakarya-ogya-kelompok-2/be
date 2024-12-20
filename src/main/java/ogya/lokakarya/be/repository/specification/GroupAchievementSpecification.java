package ogya.lokakarya.be.repository.specification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ogya.lokakarya.be.entity.GroupAchievement;

@Component
@SuppressWarnings({"java:S1118", "java:S1192"})
public class GroupAchievementSpecification {

    @Autowired
    private SpecificationFactory<GroupAchievement> spec;

    public Specification<GroupAchievement> nameContains(String substr) {
        return spec.fieldContains("groupName", substr);
    }

    public Specification<GroupAchievement> weightLte(Integer hi) {
        return spec.fieldLte("percentage", hi);
    }

    public Specification<GroupAchievement> weightGte(Integer lo) {
        return spec.fieldGte("percentage", lo);
    }

    public Specification<GroupAchievement> weightBetween(Integer lo, Integer hi) {
        return spec.fieldBetween("percentage", lo, hi);
    }

    public Specification<GroupAchievement> enabledEquals(boolean value) {
        return spec.fieldEquals("enabled", value);
    }
}
