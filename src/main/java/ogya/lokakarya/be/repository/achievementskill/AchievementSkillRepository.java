package ogya.lokakarya.be.repository.achievementskill;

import ogya.lokakarya.be.entity.EmpAchievementSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AchievementSkillRepository extends JpaRepository<EmpAchievementSkill, UUID> {
}
