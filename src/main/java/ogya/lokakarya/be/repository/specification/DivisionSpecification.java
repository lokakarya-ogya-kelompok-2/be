package ogya.lokakarya.be.repository.specification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ogya.lokakarya.be.entity.Division;

@Component
public class DivisionSpecification {
    @Autowired
    private SpecificationFactory<Division> spec;

    public Specification<Division> nameContains(String substr) {
        return spec.fieldContains("name", substr);
    }
}
