package ogya.lokakarya.be.repository.technicalskill;

import ogya.lokakarya.be.entity.TechnicalSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TechnicalSkillRepository extends JpaRepository<TechnicalSkill, UUID> {
}
