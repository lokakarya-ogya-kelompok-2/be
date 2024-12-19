package ogya.lokakarya.be.specifications;

import java.sql.Date;
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
import ogya.lokakarya.be.dto.user.UserFilter;
import ogya.lokakarya.be.entity.Division;
import ogya.lokakarya.be.entity.User;

@SuppressWarnings({"java:S3776", "java:S1118", "java:S1192"})
public class UserSpecification {
    public static Specification<User> filter(UserFilter filter) {
        return new Specification<User>() {

            @Override
            @Nullable
            public Predicate toPredicate(@NonNull Root<User> root, @Nullable CriteriaQuery<?> query,
                    @NonNull CriteriaBuilder cb) {

                List<Predicate> predicates = new ArrayList<>();

                if (filter.getUsernameContains() != null) {
                    predicates.add(cb.like(cb.lower(root.get("username")),
                            "%" + filter.getUsernameContains().toLowerCase() + "%"));
                }
                if (filter.getNameContains() != null) {
                    predicates.add(cb.like(cb.lower(root.get("fullName")),
                            "%" + filter.getNameContains().toLowerCase() + "%"));
                }
                if (filter.getPositionContains() != null) {
                    predicates.add(cb.like(cb.lower(root.get("position")),
                            "%" + filter.getPositionContains().toLowerCase() + "%"));
                }
                if (filter.getEmailContains() != null) {
                    predicates.add(cb.like(cb.lower(root.get("emailAddress")),
                            "%" + filter.getEmailContains().toLowerCase() + "%"));
                }
                if (filter.getMinJoinDate() != null && filter.getMaxJoinDate() != null) {
                    predicates.add(
                            cb.between(root.get("joinDate"), Date.valueOf(filter.getMinJoinDate()),
                                    Date.valueOf(filter.getMaxJoinDate())));
                } else if (filter.getMinJoinDate() != null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("joinDate"),
                            Date.valueOf(filter.getMinJoinDate())));
                } else if (filter.getMaxJoinDate() != null) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("joinDate"),
                            Date.valueOf(filter.getMaxJoinDate())));
                }
                if (filter.getEmployeeStatus() != null) {
                    predicates
                            .add(cb.equal(root.get("employeeStatus"), filter.getEmployeeStatus()));
                }
                if (filter.getDivisionNameContains() != null) {
                    Join<User, Division> userDivisionJoin = root.join("division", JoinType.LEFT);
                    predicates.add(cb.like(cb.lower(userDivisionJoin.get("divisionName")),
                            "%" + filter.getDivisionNameContains().toLowerCase() + "%"));
                }
                if (filter.getEnabledOnly().booleanValue()) {
                    predicates.add(cb.equal(root.get("enabled"), true));
                }

                return cb.and(predicates.toArray(new Predicate[predicates.size()]));

            }
        };
    }

    public static Specification<User> filterAnyStringFields(UserFilter filter) {
        return new Specification<User>() {

            @Override
            @Nullable
            public Predicate toPredicate(@NonNull Root<User> root, @Nullable CriteriaQuery<?> query,
                    @NonNull CriteriaBuilder cb) {

                List<Predicate> predicates = new ArrayList<>();
                List<Predicate> orPredicates = new ArrayList<>();

                orPredicates.add(cb.like(cb.lower(root.get("username")),
                        "%" + filter.getAnyStringFieldsContains().toLowerCase() + "%"));
                orPredicates.add(cb.like(cb.lower(root.get("fullName")),
                        "%" + filter.getAnyStringFieldsContains().toLowerCase() + "%"));
                orPredicates.add(cb.like(cb.lower(root.get("position")),
                        "%" + filter.getAnyStringFieldsContains().toLowerCase() + "%"));
                orPredicates.add(cb.like(cb.lower(root.get("emailAddress")),
                        "%" + filter.getAnyStringFieldsContains().toLowerCase() + "%"));
                Join<User, Division> userDivisionJoin = root.join("division", JoinType.LEFT);
                orPredicates.add(cb.like(cb.lower(userDivisionJoin.get("divisionName")),
                        "%" + filter.getAnyStringFieldsContains().toLowerCase() + "%"));
                predicates.add(cb.or(orPredicates.toArray(new Predicate[orPredicates.size()])));

                if (filter.getMinJoinDate() != null && filter.getMaxJoinDate() != null) {
                    predicates.add(
                            cb.between(root.get("joinDate"), Date.valueOf(filter.getMinJoinDate()),
                                    Date.valueOf(filter.getMaxJoinDate())));
                } else if (filter.getMinJoinDate() != null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("joinDate"),
                            Date.valueOf(filter.getMinJoinDate())));
                } else if (filter.getMaxJoinDate() != null) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("joinDate"),
                            Date.valueOf(filter.getMaxJoinDate())));
                }
                if (filter.getEmployeeStatus() != null) {
                    predicates
                            .add(cb.equal(root.get("employeeStatus"), filter.getEmployeeStatus()));
                }
                if (filter.getEnabledOnly().booleanValue()) {
                    predicates.add(cb.equal(root.get("enabled"), true));
                }

                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
