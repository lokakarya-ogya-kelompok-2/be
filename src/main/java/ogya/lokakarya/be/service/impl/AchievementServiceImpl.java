package ogya.lokakarya.be.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
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

@Service
public class AchievementServiceImpl implements AchievementService {
    @Autowired
    AchievementRepository achievementRepository;
    @Autowired
    private static final Logger LOG = LoggerFactory.getLogger(AchievementServiceImpl.class);
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
        LOG.info("Start service: create achievement");
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

        LOG.info("End service: create achievement");
        return new AchievementDto(createdData, true, false, true);
    }

    @Override
    public List<AchievementDto> getAllAchievements(AchievementFilter filter) {
        List<Achievement> achievements = achievementRepository.findAllByFilter(filter);
        return achievements.stream().map(achievement -> new AchievementDto(achievement,
                filter.getWithCreatedBy(), filter.getWithUpdatedBy(), filter.getWithGroup()))
                .toList();
    }

    @Override
    public AchievementDto getAchievementsById(UUID id) {
        Optional<Achievement> achievementOpt = achievementRepository.findById(id);
        if (achievementOpt.isEmpty()) {
            throw ResponseException.achievementNotFound(id);
        }
        Achievement data = achievementOpt.get();
        return convertToDto(data);
    }

    @Transactional
    @Override
    public AchievementDto updateAchievementById(UUID id, AchievementReq achievementReq) {
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

        return convertToDto(achievement);
    }

    @Transactional
    @Override
    public boolean deleteAchievementById(UUID id) {
        Optional<Achievement> achievementOpt = achievementRepository.findById(id);
        if (achievementOpt.isEmpty()) {
            throw ResponseException.achievementNotFound(id);
        }
        achievementRepository.delete(achievementOpt.get());

        entityManager.flush();
        assessmentSummarySvc.recalculateAllAssessmentSummaries();

        return ResponseEntity.ok().build().hasBody();

    }

    public AchievementDto convertToDto(Achievement data) {
        return new AchievementDto(data, true, true, true);
    }
}
