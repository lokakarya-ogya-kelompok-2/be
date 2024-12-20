package ogya.lokakarya.be.repository.specification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ogya.lokakarya.be.entity.GroupAttitudeSkill;


@SuppressWarnings({"java:S1118", "java:S1192"})
@Component
public class GroupAttitudeSkillSpecification {
    @Autowired
    private SpecificationFactory<GroupAttitudeSkill> spec;

    public Specification<GroupAttitudeSkill> nameContains(String substr) {
        return spec.fieldContains("groupName", substr);
    }

    public Specification<GroupAttitudeSkill> weightLte(Integer hi) {
        return spec.fieldLte("percentage", hi);
    }

    public Specification<GroupAttitudeSkill> weightGte(Integer lo) {
        return spec.fieldGte("percentage", lo);
    }

    public Specification<GroupAttitudeSkill> weightBetween(Integer lo, Integer hi) {
        return spec.fieldBetween("percentage", lo, hi);
    }

    public Specification<GroupAttitudeSkill> enabledEquals(boolean value) {
        return spec.fieldEquals("enabled", value);
    }
}
