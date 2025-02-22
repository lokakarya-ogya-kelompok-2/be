package ogya.lokakarya.be.repository.specification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ogya.lokakarya.be.entity.TechnicalSkill;

@Component
public class TechnicalSkillSpecification {
    @Autowired
    SpecificationFactory<TechnicalSkill> spec;

    public Specification<TechnicalSkill> nameContains(String substr) {
        return spec.fieldContains("name", substr);
    }

    public Specification<TechnicalSkill> enabledEquals(Boolean value) {
        return spec.fieldEquals("enabled", value);
    }
}
