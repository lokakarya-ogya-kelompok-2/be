package ogya.lokakarya.be.specification;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import ogya.lokakarya.be.dto.achievement.AchievementFilter;
import ogya.lokakarya.be.entity.Achievement;
import ogya.lokakarya.be.entity.GroupAchievement;

@SuppressWarnings({"java:S1118", "java:S3776"})
public class AchievementSpecification {
    public static Specification<Achievement> filter(AchievementFilter filter) {
        return new Specification<Achievement>() {

            @Override
            @Nullable
            public Predicate toPredicate(@NonNull Root<Achievement> root,
                    @Nullable CriteriaQuery<?> query, @NonNull CriteriaBuilder cb) {
                Join<Achievement, GroupAchievement> achievementGroupAchievementJoin = null;
                List<Predicate> predicates = new ArrayList<>();
                if (filter.getAnyStringFieldContains() != null) {
                    System.out.println("MASUK DIATAS");
                    List<Predicate> orPredicates = new ArrayList<>();
                    orPredicates.add(cb.like(cb.lower(root.get("name")),
                            "%" + filter.getAnyStringFieldContains().toLowerCase() + "%"));
                    achievementGroupAchievementJoin = root.join("groupAchievement", JoinType.LEFT);
                    orPredicates
                            .add(cb.like(cb.lower(achievementGroupAchievementJoin.get("groupName")),
                                    "%" + filter.getAnyStringFieldContains().toLowerCase() + "%"));
                    predicates.add(cb.or(orPredicates.toArray(new Predicate[orPredicates.size()])));
                } else {
                    if (filter.getNameContains() != null) {
                        predicates.add(cb.like(cb.lower(root.get("name")),
                                "%" + filter.getNameContains().toLowerCase() + "%"));
                    }
                }
                if (filter.getGroupIds() != null || filter.getWithGroup().booleanValue()) {
                    if (achievementGroupAchievementJoin == null) {
                        achievementGroupAchievementJoin =
                                root.join("groupAchievement", JoinType.LEFT);
                    }
                    if (filter.getGroupIds() != null) {
                        predicates.add(achievementGroupAchievementJoin.in(filter.getGroupIds()));
                    }
                }
                if (filter.getEnabledOnly().booleanValue()) {
                    predicates.add(cb.equal(root.get("enabled"), true));
                }

                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
