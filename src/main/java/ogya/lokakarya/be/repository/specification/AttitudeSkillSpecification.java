package ogya.lokakarya.be.repository.specification;

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
import ogya.lokakarya.be.dto.attitudeskill.AttitudeSkillFilter;
import ogya.lokakarya.be.entity.AttitudeSkill;
import ogya.lokakarya.be.entity.GroupAttitudeSkill;

@SuppressWarnings({"java:S1118", "java:S3776"})
public class AttitudeSkillSpecification {
    public static Specification<AttitudeSkill> filter(AttitudeSkillFilter filter) {
        return new Specification<>() {

            @Override
            @Nullable
            public Predicate toPredicate(@NonNull Root<AttitudeSkill> root,
                    @Nullable CriteriaQuery<?> query, @NonNull CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                Join<AttitudeSkill, GroupAttitudeSkill> attitudeSkillGroupAttitudeSkillJoin = null;
                if (filter.getAnyStringFieldContains() != null) {
                    List<Predicate> orPredicates = new ArrayList<>();
                    orPredicates.add(cb.like(cb.lower(root.get("name")),
                            "%" + filter.getAnyStringFieldContains() + "%"));
                    attitudeSkillGroupAttitudeSkillJoin =
                            root.join("groupAttitudeSkill", JoinType.LEFT);
                    orPredicates.add(
                            cb.like(cb.lower(attitudeSkillGroupAttitudeSkillJoin.get("groupName")),
                                    "%" + filter.getAnyStringFieldContains() + "%"));
                    predicates.add(cb.or(orPredicates.toArray(new Predicate[orPredicates.size()])));
                } else {
                    if (filter.getNameContains() != null) {
                        predicates.add(cb.like(cb.lower(root.get("name")),
                                "%" + filter.getNameContains().toLowerCase() + "%"));
                    }
                }
                if (filter.getGroupIds() != null || filter.getWithGroup().booleanValue()) {
                    if (attitudeSkillGroupAttitudeSkillJoin == null) {
                        attitudeSkillGroupAttitudeSkillJoin =
                                root.join("groupAttitudeSkill", JoinType.LEFT);
                    }
                    if (filter.getGroupIds() != null) {
                        predicates.add(attitudeSkillGroupAttitudeSkillJoin.get("id")
                                .in(filter.getGroupIds()));
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
