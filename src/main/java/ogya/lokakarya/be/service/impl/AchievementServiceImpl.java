package ogya.lokakarya.be.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ogya.lokakarya.be.dto.achievement.AchievementDto;
import ogya.lokakarya.be.dto.achievement.AchievementReq;
import ogya.lokakarya.be.entity.Achievement;
import ogya.lokakarya.be.entity.GroupAchievement;
import ogya.lokakarya.be.exception.ResponseException;
import ogya.lokakarya.be.repository.AchievementRepository;
import ogya.lokakarya.be.repository.GroupAchievementRepository;
import ogya.lokakarya.be.service.AchievementService;

@Service
public class AchievementServiceImpl implements AchievementService {
    @Autowired
    AchievementRepository achievementRepository;
    @Autowired
    private static final Logger LOG = LoggerFactory.getLogger(AchievementServiceImpl.class);
    @Autowired
    private GroupAchievementRepository groupAchievementRepository;

    @Override
    public AchievementDto create(AchievementReq data) {
        LOG.info("Start service: create achievement");
        Optional<GroupAchievement> findGroupAchievement =
                groupAchievementRepository.findById(data.getGroupAchievementId());
        if (findGroupAchievement.isEmpty()) {
            throw ResponseException.groupAchievementNotFound(data.getGroupAchievementId());
        }
        Achievement dataEntity = data.toEntity();
        dataEntity.setGroupAchievement(findGroupAchievement.get());
        Achievement createdData = achievementRepository.save(dataEntity);
        LOG.info("End service: create achievement");
        return new AchievementDto(createdData, false);
    }

    @Override
    public List<AchievementDto> getAllAchievements() {
        List<AchievementDto> listResult = new ArrayList<>();
        List<Achievement> achievementsList = achievementRepository.findAll();
        for (Achievement achievement : achievementsList) {
            AchievementDto result = convertToDto(achievement);
            listResult.add(result);
        }
        return listResult;
    }

    @Override
    public AchievementDto getAchievementsById(UUID id) {
        Optional<Achievement> listData;
        try {
            listData = achievementRepository.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        Achievement data = listData.get();
        return convertToDto(data);
    }

    @Override
    public AchievementDto updateAchievementById(UUID id, AchievementReq achievementReq) {
        Optional<Achievement> listData = achievementRepository.findById(id);
        if (listData.isPresent()) {
            Achievement achievement = listData.get();
            if (!achievement.getAchievement().isBlank()) {
                Optional<GroupAchievement> findGroupAchievement =
                        groupAchievementRepository.findById(achievementReq.getGroupAchievementId());
                if (findGroupAchievement.isPresent()) {
                    achievement.setGroupAchievement(findGroupAchievement.get());
                    achievement.setAchievement(achievementReq.getAchievementName());
                    achievement.setEnabled(achievementReq.getEnabled());
                }
            }
            AchievementDto achievementDto = convertToDto(achievement);
            achievementRepository.save(achievement);
            return achievementDto;
        }
        return null;
    }

    @Override
    public boolean deleteAchievementById(UUID id) {
        Optional<Achievement> listData = achievementRepository.findById(id);
        if (listData.isPresent()) {
            achievementRepository.delete(listData.get());
            return ResponseEntity.ok().build().hasBody();
        } else {
            return ResponseEntity.notFound().build().hasBody();
        }
    }

    public AchievementDto convertToDto(Achievement data) {
        AchievementDto result = new AchievementDto(data, true);
        return result;
    }
}
