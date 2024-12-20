package ogya.lokakarya.be.specification;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import ogya.lokakarya.be.dto.groupachievement.GroupAchievementFilter;
import ogya.lokakarya.be.entity.GroupAchievement;

@SuppressWarnings({"java:S1118", "java:S1192"})
public class GroupAchievementSpecification {
    public static Specification<GroupAchievement> filter(GroupAchievementFilter filter) {
        return new Specification<>() {

            @Override
            @Nullable
            public Predicate toPredicate(@NonNull Root<GroupAchievement> root,
                    @Nullable CriteriaQuery<?> query, @NonNull CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                if (filter.getNameContains() != null) {
                    predicates.add(cb.like(cb.lower(root.get("groupName")),
                            "%" + filter.getNameContains().toLowerCase() + "%"));
                }
                if (filter.getMinWeight() != null && filter.getMaxWeight() != null) {
                    predicates.add(cb.between(root.get("percentage"), filter.getMinWeight(),
                            filter.getMaxWeight()));
                } else if (filter.getMinWeight() != null) {
                    predicates.add(
                            cb.greaterThanOrEqualTo(root.get("percentage"), filter.getMinWeight()));
                } else if (filter.getMaxWeight() != null) {
                    predicates.add(
                            cb.lessThanOrEqualTo(root.get("percentage"), filter.getMaxWeight()));
                }
                if (filter.getEnabledOnly().booleanValue()) {
                    predicates.add(cb.equal(root.get("enabled"), true));
                }

                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }

        };
    }
}
