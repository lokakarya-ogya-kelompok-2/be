package ogya.lokakarya.be.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import ogya.lokakarya.be.config.security.SecurityUtil;
import ogya.lokakarya.be.dto.achievement.AchievementDto;
import ogya.lokakarya.be.dto.achievement.AchievementFilter;
import ogya.lokakarya.be.dto.achievement.AchievementReq;
import ogya.lokakarya.be.entity.Achievement;
import ogya.lokakarya.be.entity.GroupAchievement;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.exception.ResponseException;
import ogya.lokakarya.be.repository.AchievementRepository;
import ogya.lokakarya.be.repository.GroupAchievementRepository;
import ogya.lokakarya.be.service.AchievementService;
import ogya.lokakarya.be.service.AssessmentSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Slf4j
@Service
public class AchievementServiceImpl implements AchievementService {
    @Autowired
    AchievementRepository achievementRepository;
    @Autowired
    private GroupAchievementRepository groupAchievementRepository;

    @Autowired
    private AssessmentSummaryService assessmentSummarySvc;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private SecurityUtil securityUtil;

    @Transactional
    @Override
    public AchievementDto create(AchievementReq data) {
        log.info("Starting AchievementServiceImpl.create");
        Optional<GroupAchievement> groupAchievementOpt =
                groupAchievementRepository.findById(data.getGroupAchievementId());
        if (groupAchievementOpt.isEmpty()) {
            throw ResponseException.groupAchievementNotFound(data.getGroupAchievementId());
        }
        Achievement dataEntity = data.toEntity();
        dataEntity.setGroupAchievement(groupAchievementOpt.get());
        User currentUser = securityUtil.getCurrentUser();
        dataEntity.setCreatedBy(currentUser);
        Achievement createdData = achievementRepository.save(dataEntity);

        entityManager.flush();
        assessmentSummarySvc.recalculateAllAssessmentSummaries();

        log.info("Ending AchievementServiceImpl.create");
        return new AchievementDto(createdData, true, false, true);
    }

    @Override
    public List<AchievementDto> getAllAchievements(AchievementFilter filter) {
        log.info("Starting AchievementServiceImpl.getAllAchievements");
        List<Achievement> achievements = achievementRepository.findAllByFilter(filter);
        log.info("Ending AchievementServiceImpl.getAllAchievements");
        return achievements.stream().map(achievement -> new AchievementDto(achievement,
                filter.getWithCreatedBy(), filter.getWithUpdatedBy(), filter.getWithGroup()))
                .toList();
    }

    @Override
    public AchievementDto getAchievementsById(UUID id) {
        log.info("Starting AchievementServiceImpl.getAchievementsById");
        Optional<Achievement> achievementOpt = achievementRepository.findById(id);
        if (achievementOpt.isEmpty()) {
            throw ResponseException.achievementNotFound(id);
        }
        Achievement data = achievementOpt.get();
        log.info("Ending AchievementServiceImpl.getAchievementsById");
        return convertToDto(data);
    }

    @Transactional
    @Override
    public AchievementDto updateAchievementById(UUID id, AchievementReq achievementReq) {
        log.info("Starting AchievementServiceImpl.updateAchievementById");
        Optional<Achievement> achievementOpt = achievementRepository.findById(id);
        if (achievementOpt.isEmpty()) {
            throw ResponseException.achievementNotFound(id);
        }

        boolean shouldRecalculateAllAssSum = false;

        Achievement achievement = achievementOpt.get();
        if (achievementReq.getAchievementName() != null) {
            achievement.setName(achievementReq.getAchievementName());
        }
        if (achievementReq.getEnabled() != null) {
            shouldRecalculateAllAssSum =
                    !achievementReq.getEnabled().equals(achievement.getEnabled());
            achievement.setEnabled(achievementReq.getEnabled());
        }
        if (achievementReq.getGroupAchievementId() != null) {
            Optional<GroupAchievement> groupAchievementOpt =
                    groupAchievementRepository.findById(achievementReq.getGroupAchievementId());
            if (groupAchievementOpt.isEmpty()) {
                throw ResponseException
                        .groupAchievementNotFound(achievementReq.getGroupAchievementId());
            }
            achievement.setGroupAchievement(groupAchievementOpt.get());
        }
        User currentUser = securityUtil.getCurrentUser();
        achievement.setUpdatedBy(currentUser);
        achievement = achievementRepository.save(achievement);
        if (shouldRecalculateAllAssSum) {
            entityManager.flush();
            this.assessmentSummarySvc.recalculateAllAssessmentSummaries();
        }

        log.info("Ending AchievementServiceImpl.updateAchievementById");
        return convertToDto(achievement);
    }

    @Transactional
    @Override
    public boolean deleteAchievementById(UUID id) {
        log.info("Starting AchievementServiceImpl.deleteAchievementById");
        Optional<Achievement> achievementOpt = achievementRepository.findById(id);
        if (achievementOpt.isEmpty()) {
            throw ResponseException.achievementNotFound(id);
        }
        achievementRepository.delete(achievementOpt.get());
        entityManager.flush();
        assessmentSummarySvc.recalculateAllAssessmentSummaries();
        log.info("Ending AchievementServiceImpl.deleteAchievementById");
        return ResponseEntity.ok().build().hasBody();

    }

    public AchievementDto convertToDto(Achievement data) {
        return new AchievementDto(data, true, true, true);
    }
}
