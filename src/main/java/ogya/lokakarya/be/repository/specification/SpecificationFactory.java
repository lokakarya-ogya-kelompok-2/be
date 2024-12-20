package ogya.lokakarya.be.repository.specification;

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
}
