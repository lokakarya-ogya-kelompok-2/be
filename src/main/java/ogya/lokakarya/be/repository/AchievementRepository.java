package ogya.lokakarya.be.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ogya.lokakarya.be.entity.Achievement;

public interface AchievementRepository
                extends JpaRepository<Achievement, UUID>, JpaSpecificationExecutor<Achievement> {
}
