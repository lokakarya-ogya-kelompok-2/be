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
import ogya.lokakarya.be.dto.technicalskill.TechnicalSkillFilter;
import ogya.lokakarya.be.entity.TechnicalSkill;

@SuppressWarnings("java:S1118")
public class TechnicalSkillSpecification {
    public static Specification<TechnicalSkill> filter(TechnicalSkillFilter filter) {
        return new Specification<>() {

            @Override
            @Nullable
            public Predicate toPredicate(@NonNull Root<TechnicalSkill> root,
                    @Nullable CriteriaQuery<?> query, @NonNull CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                if (filter.getNameContains() != null) {
                    predicates.add(cb.like(cb.lower(root.get("name")),
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
