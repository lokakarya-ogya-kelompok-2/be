package ogya.lokakarya.be.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class SpecificationWrapper<E> {
    public Specification<E> fieldContains(String fieldName, String substr) {
        return (root, query, cb) -> cb.like(cb.lower(root.get(fieldName)),
                "%" + substr.toLowerCase() + "%");
    }

    public Specification<E> fieldEquals(String fieldName, Object value) {
        return (root, query, cb) -> cb.equal(root.get(fieldName), value);
    }
}
