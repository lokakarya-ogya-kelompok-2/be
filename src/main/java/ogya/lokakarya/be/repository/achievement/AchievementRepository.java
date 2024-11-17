package ogya.lokakarya.be.repository.achievement;

import ogya.lokakarya.be.entity.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AchievementRepository extends JpaRepository<Achievement, UUID> {
}
