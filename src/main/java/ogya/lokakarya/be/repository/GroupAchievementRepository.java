package ogya.lokakarya.be.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ogya.lokakarya.be.dto.groupachievement.GroupAchievementFilter;
import ogya.lokakarya.be.entity.GroupAchievement;

public interface GroupAchievementRepository extends JpaRepository<GroupAchievement, UUID>,
        FilterRepository<GroupAchievement, GroupAchievementFilter> {
}
