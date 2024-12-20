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
import ogya.lokakarya.be.dto.assessmentsummary.AssessmentSummaryFilter;
import ogya.lokakarya.be.entity.AssessmentSummary;
import ogya.lokakarya.be.entity.Division;
import ogya.lokakarya.be.entity.User;

@SuppressWarnings({"java:S1118", "java:S3776"})
public class AssessmentSummarySpecification {
    public static Specification<AssessmentSummary> filter(AssessmentSummaryFilter filter) {
        return new Specification<AssessmentSummary>() {

            @Override
            @Nullable
            public Predicate toPredicate(@NonNull Root<AssessmentSummary> root,
                    @Nullable CriteriaQuery<?> query, @NonNull CriteriaBuilder cb) {
                Join<AssessmentSummary, User> assessmentSummaryUserJoin = null;
                List<Predicate> predicates = new ArrayList<>();
                if (filter.getAnyStringFieldContains() != null) {
                    List<Predicate> orPredicates = new ArrayList<>();
                    if (assessmentSummaryUserJoin == null)
                        assessmentSummaryUserJoin = root.join("user", JoinType.LEFT);
                    orPredicates.add(cb.like(cb.lower(assessmentSummaryUserJoin.get("fullName")),
                            "%" + filter.getAnyStringFieldContains().toLowerCase() + "%"));
                    orPredicates.add(cb.like(cb.lower(assessmentSummaryUserJoin.get("position")),
                            "%" + filter.getAnyStringFieldContains().toLowerCase() + "%"));
                    predicates.add(cb.or(orPredicates.toArray(new Predicate[orPredicates.size()])));
                }
                if (filter.getUserIds() != null) {
                    if (assessmentSummaryUserJoin == null) {
                        assessmentSummaryUserJoin = root.join("user", JoinType.LEFT);
                    }
                    predicates.add(assessmentSummaryUserJoin.get("id").in(filter.getUserIds()));
                }

                if (filter.getDivisionIds() != null && !filter.getDivisionIds().isEmpty()) {
                    if (assessmentSummaryUserJoin == null) {
                        assessmentSummaryUserJoin = root.join("user", JoinType.LEFT);
                    }
                    Join<User, Division> userDivisionJoin =
                            assessmentSummaryUserJoin.join("division", JoinType.LEFT);
                    predicates.add(userDivisionJoin.get("id").in(filter.getDivisionIds()));
                }

                if (filter.getYears() != null && !filter.getYears().isEmpty()) {
                    predicates.add(root.get("year").in(filter.getYears()));
                }

                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }

        };
    }
}
