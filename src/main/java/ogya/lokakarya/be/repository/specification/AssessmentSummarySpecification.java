package ogya.lokakarya.be.repository.specification;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import ogya.lokakarya.be.entity.AssessmentSummary;
import ogya.lokakarya.be.entity.Division;
import ogya.lokakarya.be.entity.User;

@SuppressWarnings({"java:S1118", "java:S3776"})
public class AssessmentSummarySpecification {
    public static Specification<AssessmentSummary> userFullNameContains(String substr) {
        return (root, query, cb) -> {
            Join<AssessmentSummary, User> assessmentSummaryUserJoin =
                    root.join("user", JoinType.LEFT);
            return cb.like(cb.lower(assessmentSummaryUserJoin.get("fullName")),
                    "%" + substr.toLowerCase() + "%");
        };
    }

    public static Specification<AssessmentSummary> positionContains(String substr) {
        return (root, query, cb) -> {
            Join<AssessmentSummary, User> assessmentSummaryUserJoin =
                    root.join("user", JoinType.LEFT);
            return cb.like(cb.lower(assessmentSummaryUserJoin.get("position")),
                    "%" + substr.toLowerCase() + "%");
        };
    }

    public static Specification<AssessmentSummary> userIdIn(List<UUID> userIds) {
        return (root, query, cb) -> {
            Join<AssessmentSummary, User> assessmentSummaryUserJoin =
                    root.join("user", JoinType.LEFT);
            return assessmentSummaryUserJoin.get("id").in(userIds);
        };
    }

    public static Specification<AssessmentSummary> divisionIdIn(List<UUID> divisionIds) {
        return (root, query, cb) -> {
            Join<AssessmentSummary, User> assessmentSummaryUserJoin =
                    root.join("user", JoinType.LEFT);
            Join<User, Division> userDivisionJoin =
                    assessmentSummaryUserJoin.join("division", JoinType.LEFT);
            return userDivisionJoin.get("id").in(divisionIds);
        };
    }

    public static Specification<AssessmentSummary> yearIn(List<Integer> years) {
        return (root, query, cb) -> root.get("year").in(years);

    }
}
