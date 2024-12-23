package ogya.lokakarya.be.repository.specification;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import ogya.lokakarya.be.entity.AssessmentSummary;
import ogya.lokakarya.be.entity.Division;
import ogya.lokakarya.be.entity.User;

@Component
public class AssessmentSummarySpecification {
    @Autowired
    private SpecificationFactory<AssessmentSummary> spec;

    public Specification<AssessmentSummary> userFullNameContains(String substr) {
        return (root, query, cb) -> {
            Join<AssessmentSummary, User> assessmentSummaryUserJoin =
                    root.join("user", JoinType.LEFT);
            return cb.like(cb.lower(assessmentSummaryUserJoin.get("fullName")),
                    "%" + substr.toLowerCase() + "%");
        };
    }

    public Specification<AssessmentSummary> positionContains(String substr) {
        return (root, query, cb) -> {
            Join<AssessmentSummary, User> assessmentSummaryUserJoin =
                    root.join("user", JoinType.LEFT);
            return cb.like(cb.lower(assessmentSummaryUserJoin.get("position")),
                    "%" + substr.toLowerCase() + "%");
        };
    }

    public Specification<AssessmentSummary> userIdIn(List<UUID> userIds) {
        return (root, query, cb) -> {
            Join<AssessmentSummary, User> assessmentSummaryUserJoin =
                    root.join("user", JoinType.LEFT);
            return assessmentSummaryUserJoin.get("id").in(userIds);
        };
    }

    public Specification<AssessmentSummary> divisionIdIn(List<UUID> divisionIds) {
        return (root, query, cb) -> {
            Join<AssessmentSummary, User> assessmentSummaryUserJoin =
                    root.join("user", JoinType.LEFT);
            Join<User, Division> userDivisionJoin =
                    assessmentSummaryUserJoin.join("division", JoinType.LEFT);
            return userDivisionJoin.get("id").in(divisionIds);
        };
    }

    public Specification<AssessmentSummary> yearIn(List<Integer> years) {
        return spec.fieldIn("year", years);
    }
}
