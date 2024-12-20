package ogya.lokakarya.be.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ogya.lokakarya.be.entity.TechnicalSkill;

public interface TechnicalSkillRepository extends JpaRepository<TechnicalSkill, UUID>,
                JpaSpecificationExecutor<TechnicalSkill> {
}
