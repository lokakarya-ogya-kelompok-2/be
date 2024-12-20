package ogya.lokakarya.be.repository.specification;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import ogya.lokakarya.be.entity.AttitudeSkill;
import ogya.lokakarya.be.entity.GroupAttitudeSkill;

@SuppressWarnings({"java:S1118", "java:S3776"})
@Component
public class AttitudeSkillSpecification {

    @Autowired
    private SpecificationFactory<AttitudeSkill> spec;

    public Specification<AttitudeSkill> nameContains(String substr) {
        return spec.fieldContains("name", substr);
    }

    public Specification<AttitudeSkill> enabledEquals(boolean value) {
        return spec.fieldEquals("enabled", value);
    }

    public Specification<AttitudeSkill> groupNameContains(String substr) {
        return (root, query, cb) -> {
            Join<AttitudeSkill, GroupAttitudeSkill> achievementGroupAttitudeSkillJoin =
                    root.join("groupAttitudeSkill", JoinType.LEFT);
            return cb.like(cb.lower(achievementGroupAttitudeSkillJoin.get("groupName")),
                    "%" + substr.toLowerCase() + "%");
        };
    }

    public Specification<AttitudeSkill> groupIdIn(List<UUID> groupIds) {
        return (root, query, cb) -> {
            Join<AttitudeSkill, GroupAttitudeSkill> achievementGroupAttitudeSkillJoin =
                    root.join("groupAttitudeSkill", JoinType.LEFT);
            return achievementGroupAttitudeSkillJoin.get("id").in(groupIds);
        };
    }
}
