package ogya.lokakarya.be.repository.specification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ogya.lokakarya.be.entity.DevPlan;

@SuppressWarnings("java:S1118")
@Component
public class DevPlanSpecification {
    @Autowired
    SpecificationFactory<DevPlan> spec;

    public Specification<DevPlan> nameContains(String substr) {
        return spec.fieldContains("plan", substr);
    }

    public Specification<DevPlan> enabledEquals(boolean value) {
        return spec.fieldEquals("enabled", value);
    }
}
