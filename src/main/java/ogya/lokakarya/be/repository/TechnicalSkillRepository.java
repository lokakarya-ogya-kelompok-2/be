package ogya.lokakarya.be.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ogya.lokakarya.be.dto.technicalskill.TechnicalSkillFilter;
import ogya.lokakarya.be.entity.TechnicalSkill;

public interface TechnicalSkillRepository extends JpaRepository<TechnicalSkill, UUID>,
        FilterRepository<TechnicalSkill, TechnicalSkillFilter> {
}
