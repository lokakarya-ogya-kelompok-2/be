package ogya.lokakarya.be.repository.specification;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import ogya.lokakarya.be.entity.Achievement;
import ogya.lokakarya.be.entity.GroupAchievement;

@Component
@SuppressWarnings({"java:S1118", "java:S3776"})
public class AchievementSpecification {

    @Autowired
    private SpecificationFactory<Achievement> spec;

    public Specification<Achievement> nameContains(String substr) {
        return spec.fieldContains("name", substr);
    }

    public Specification<Achievement> enabledEquals(boolean value) {
        return spec.fieldEquals("enabled", value);
    }

    public Specification<Achievement> groupNameContains(String substr) {
        return (root, query, cb) -> {
            Join<Achievement, GroupAchievement> achievementGroupAchievementJoin =
                    root.join("groupAchievement", JoinType.LEFT);
            return cb.like(cb.lower(achievementGroupAchievementJoin.get("groupName")),
                    "%" + substr.toLowerCase() + "%");
        };
    }

    public Specification<Achievement> groupIdIn(List<UUID> groupIds) {
        return (root, query, cb) -> {
            Join<Achievement, GroupAchievement> achievementGroupAchievementJoin =
                    root.join("groupAchievement", JoinType.LEFT);
            return achievementGroupAchievementJoin.get("id").in(groupIds);
        };
    }
}
