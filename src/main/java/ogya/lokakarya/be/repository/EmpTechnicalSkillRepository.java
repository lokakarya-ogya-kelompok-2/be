package ogya.lokakarya.be.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ogya.lokakarya.be.dto.emptechnicalskill.EmpTechnicalSkillFilter;
import ogya.lokakarya.be.entity.EmpTechnicalSkill;

public interface EmpTechnicalSkillRepository extends JpaRepository<EmpTechnicalSkill, UUID>,
                FilterRepository<EmpTechnicalSkill, EmpTechnicalSkillFilter> {

}
