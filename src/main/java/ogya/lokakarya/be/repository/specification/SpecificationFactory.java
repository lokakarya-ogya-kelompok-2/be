package ogya.lokakarya.be.repository.specification;

import java.sql.Date;
import java.util.Collection;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class SpecificationFactory<E> {
    public Specification<E> fieldContains(String fieldName, String substr) {
        return (root, query, cb) -> cb.like(cb.lower(root.get(fieldName)),
                "%" + substr.toLowerCase() + "%");
    }

    public Specification<E> fieldEquals(String fieldName, Object value) {
        return (root, query, cb) -> cb.equal(root.get(fieldName), value);
    }

    public Specification<E> fieldIn(String fieldName, Collection<?> values) {
        return (root, query, cb) -> root.get(fieldName).in(values);
    }

    public Specification<E> fieldGte(String fieldName, Integer lo) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(fieldName), lo);
    }

    public Specification<E> fieldLte(String fieldName, Integer hi) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get(fieldName), hi);
    }

    public Specification<E> fieldBetween(String fieldName, Integer lo, Integer hi) {
        return (root, query, cb) -> cb.between(root.get(fieldName), lo, hi);
    }

    public Specification<E> fieldGte(String fieldName, Date lo) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(fieldName), lo);
    }

    public Specification<E> fieldLte(String fieldName, Date hi) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get(fieldName), hi);
    }

    public Specification<E> fieldBetween(String fieldName, Date lo, Date hi) {
        return (root, query, cb) -> cb.between(root.get(fieldName), lo, hi);
    }
}
