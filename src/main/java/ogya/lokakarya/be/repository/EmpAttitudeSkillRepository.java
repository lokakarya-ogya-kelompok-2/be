package ogya.lokakarya.be.repository;

import ogya.lokakarya.be.entity.EmpAttitudeSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface EmpAttitudeSkillRepository extends JpaRepository<EmpAttitudeSkill, UUID> {
}
