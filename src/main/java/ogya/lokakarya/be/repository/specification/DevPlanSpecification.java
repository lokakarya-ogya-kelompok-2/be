package ogya.lokakarya.be.repository.specification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ogya.lokakarya.be.entity.DevPlan;

@Component
public class DevPlanSpecification {
    @Autowired
    SpecificationFactory<DevPlan> spec;

    public Specification<DevPlan> nameContains(String substr) {
        return spec.fieldContains("name", substr);
    }

    public Specification<DevPlan> enabledEquals(Boolean value) {
        return spec.fieldEquals("enabled", value);
    }
}
