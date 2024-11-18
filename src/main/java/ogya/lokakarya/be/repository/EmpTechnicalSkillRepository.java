package ogya.lokakarya.be.repository;

import ogya.lokakarya.be.entity.EmpTechnicalSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface EmpTechnicalSkillRepository extends JpaRepository<EmpTechnicalSkill, UUID> {

}
