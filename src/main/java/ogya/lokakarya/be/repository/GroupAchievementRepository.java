package ogya.lokakarya.be.repository;

import ogya.lokakarya.be.entity.GroupAchievement;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface GroupAchievementRepository extends JpaRepository<GroupAchievement, UUID> {
}