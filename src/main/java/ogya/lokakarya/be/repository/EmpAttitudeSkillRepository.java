package ogya.lokakarya.be.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ogya.lokakarya.be.dto.empattitudeskill.EmpAttitudeSkillFilter;
import ogya.lokakarya.be.entity.EmpAttitudeSkill;

public interface EmpAttitudeSkillRepository extends JpaRepository<EmpAttitudeSkill, UUID>,
                FilterRepository<EmpAttitudeSkill, EmpAttitudeSkillFilter> {
}
