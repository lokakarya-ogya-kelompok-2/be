package ogya.lokakarya.be.repository.specification;

import java.sql.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import ogya.lokakarya.be.entity.Division;
import ogya.lokakarya.be.entity.User;

@SuppressWarnings({"java:S1192"})
@Component
public class UserSpecification {
    @Autowired
    private SpecificationFactory<User> spec;

    public Specification<User> usernameContains(String substr) {
        return spec.fieldContains("username", substr);
    }

    public Specification<User> fullNameContains(String substr) {
        return spec.fieldContains("fullName", substr);
    }

    public Specification<User> positionContains(String substr) {
        return spec.fieldContains("position", substr);
    }

    public Specification<User> emailContains(String substr) {
        return spec.fieldContains("emailAddress", substr);
    }

    public Specification<User> divisionNameContains(String substr) {
        return (root, query, cb) -> {
            Join<User, Division> userDivisionJoin = root.join("division", JoinType.LEFT);
            return cb.like(cb.lower(userDivisionJoin.get("divisionName")),
                    "%" + substr.toLowerCase() + "%");
        };
    }

    public Specification<User> joinDateGte(Date lo) {
        return spec.fieldGte("joinDate", lo);
    }

    public Specification<User> joinDateLte(Date hi) {
        return spec.fieldLte("joinDate", hi);
    }

    public Specification<User> joinDateBetween(Date lo, Date hi) {
        return spec.fieldBetween("joinDate", lo, hi);
    }

    public Specification<User> employeeStatusEquals(Integer value) {
        return spec.fieldEquals("employeeStatus", value);
    }

    public Specification<User> enabledEquals(Boolean value) {
        return spec.fieldEquals("enabled", value);
    }
}
