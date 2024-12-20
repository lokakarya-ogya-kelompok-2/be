package ogya.lokakarya.be.repository.specification;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import ogya.lokakarya.be.dto.devplan.DevPlanFilter;
import ogya.lokakarya.be.entity.DevPlan;

@SuppressWarnings("java:S1118")
public class DevPlanSpecification {
    public static Specification<DevPlan> filter(DevPlanFilter filter) {
        return new Specification<DevPlan>() {

            @Override
            @Nullable
            public Predicate toPredicate(@NonNull Root<DevPlan> root,
                    @Nullable CriteriaQuery<?> query, @NonNull CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                if (filter.getNameContains() != null) {
                    predicates.add(cb.like(cb.lower(root.get("plan")),
                            "%" + filter.getNameContains().toLowerCase() + "%"));
                }
                if (filter.getEnabledOnly().booleanValue()) {
                    predicates.add(cb.equal(root.get("enabled"), true));
                }

                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
