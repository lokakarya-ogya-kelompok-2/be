package ogya.lokakarya.be.service;

import java.util.List;
import java.util.UUID;
import ogya.lokakarya.be.dto.achievement.AchievementDto;
import ogya.lokakarya.be.dto.achievement.AchievementFilter;
import ogya.lokakarya.be.dto.achievement.AchievementReq;

public interface AchievementService {
    AchievementDto create(AchievementReq data);

    List<AchievementDto> getAllAchievements(AchievementFilter filter);

    AchievementDto getAchievementsById(UUID id);

    AchievementDto updateAchievementById(UUID id, AchievementReq achievementReq);

    boolean deleteAchievementById(UUID id);
}
