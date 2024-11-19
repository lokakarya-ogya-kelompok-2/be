package ogya.lokakarya.be.service;

import ogya.lokakarya.be.dto.achievement.AchievementDto;
import ogya.lokakarya.be.dto.achievement.AchievementReq;

import java.util.List;
import java.util.UUID;

public interface AchievementService {
    AchievementDto create (AchievementReq data);
    List<AchievementDto> getAllAchievements();
    AchievementDto getAchievementsById(UUID id);
    AchievementDto updateAchievementById(UUID id, AchievementReq achievementReq);
    boolean deleteAchievementById(UUID id);
}
