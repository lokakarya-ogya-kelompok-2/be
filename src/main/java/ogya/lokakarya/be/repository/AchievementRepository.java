package ogya.lokakarya.be.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ogya.lokakarya.be.dto.achievement.AchievementFilter;
import ogya.lokakarya.be.entity.Achievement;

public interface AchievementRepository
        extends JpaRepository<Achievement, UUID>, FilterRepository<Achievement, AchievementFilter> {
}
