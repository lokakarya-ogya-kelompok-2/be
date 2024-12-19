package ogya.lokakarya.be.specifications;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import ogya.lokakarya.be.dto.division.DivisionFilter;
import ogya.lokakarya.be.entity.Division;

@SuppressWarnings("java:S1118")
public class DivisionSpecification {
    public static Specification<Division> filter(DivisionFilter filter) {
        return new Specification<Division>() {

            @Override
            @Nullable
            public Predicate toPredicate(@NonNull Root<Division> root,
                    @Nullable CriteriaQuery<?> query, @NonNull CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                if (filter.getNameContains() != null) {
                    predicates.add(cb.like(cb.lower(root.get("divisionName")),
                            "%" + filter.getNameContains().toLowerCase() + "%"));
                }

                return cb.and(predicates.toArray(new Predicate[predicates.size()]));

            }
        };
    }
}
