package ogya.lokakarya.be.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ogya.lokakarya.be.dto.empachievementskill.EmpAchievementSkillFilter;
import ogya.lokakarya.be.entity.EmpAchievementSkill;

public interface EmpAchievementSkillRepository extends JpaRepository<EmpAchievementSkill, UUID>,
                FilterRepository<EmpAchievementSkill, EmpAchievementSkillFilter> {
}
