package ogya.lokakarya.be.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import ogya.lokakarya.be.config.security.SecurityUtil;
import ogya.lokakarya.be.dto.groupachievement.GroupAchievementDto;
import ogya.lokakarya.be.dto.groupachievement.GroupAchievementFilter;
import ogya.lokakarya.be.dto.groupachievement.GroupAchievementReq;
import ogya.lokakarya.be.entity.GroupAchievement;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.exception.ResponseException;
import ogya.lokakarya.be.repository.GroupAchievementRepository;
import ogya.lokakarya.be.service.AssessmentSummaryService;
import ogya.lokakarya.be.service.GroupAchievementService;

@Service
public class GroupAchievementServiceImpl implements GroupAchievementService {
    @Autowired
    private GroupAchievementRepository groupAchievementRepository;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private AssessmentSummaryService assessmentSummarySvc;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    @Override
    public GroupAchievementDto create(GroupAchievementReq data) {
        User currentUser = securityUtil.getCurrentUser();
        GroupAchievement groupAchievementEntity = data.toEntity();
        groupAchievementEntity.setCreatedBy(currentUser);
        groupAchievementEntity = groupAchievementRepository.save(groupAchievementEntity);
        if (groupAchievementEntity.getPercentage() > 0) {
            entityManager.flush();
            assessmentSummarySvc.recalculateAllAssessmentSummaries();
        }
        return new GroupAchievementDto(groupAchievementEntity, true, false, false);
    }

    @Override
    public List<GroupAchievementDto> getAllGroupAchievements(GroupAchievementFilter filter) {
        filter.validate();
        List<GroupAchievement> groupAchievements =
                groupAchievementRepository.findAllByFilter(filter);
        return groupAchievements.stream()
                .map(groupAchievement -> new GroupAchievementDto(groupAchievement,
                        filter.getWithCreatedBy(), filter.getWithUpdatedBy(),
                        filter.getWithAchievements()))
                .toList();
    }

    @Override
    public GroupAchievementDto getGroupAchievementById(UUID id) {
        Optional<GroupAchievement> groupAchievementOpt = groupAchievementRepository.findById(id);
        if (groupAchievementOpt.isEmpty()) {
            throw ResponseException.groupAchievementNotFound(id);
        }
        GroupAchievement data = groupAchievementOpt.get();
        return convertToDto(data);
    }

    @Transactional
    @Override
    public GroupAchievementDto updateGroupAchievementById(UUID id,
            GroupAchievementReq groupAchievementReq) {
        Optional<GroupAchievement> groupAchievementOpt = groupAchievementRepository.findById(id);
        if (groupAchievementOpt.isEmpty()) {
            throw ResponseException.groupAchievementNotFound(id);
        }
        GroupAchievement groupAchievement = groupAchievementOpt.get();
        boolean shouldUpdateAssSum = false;
        if (groupAchievementReq.getGroupName() != null) {
            groupAchievement.setGroupName(groupAchievementReq.getGroupName());
            groupAchievement.setEnabled(groupAchievementReq.getEnabled());
            groupAchievement.setPercentage(groupAchievementReq.getPercentage());
        }
        if (groupAchievementReq.getEnabled() != null) {
            groupAchievement.setEnabled(groupAchievementReq.getEnabled());
        }
        if (groupAchievementReq.getPercentage() != null) {
            shouldUpdateAssSum =
                    groupAchievementReq.getPercentage().equals(groupAchievement.getPercentage());
            groupAchievementReq.setPercentage(groupAchievementReq.getPercentage());
        }
        User currentUser = securityUtil.getCurrentUser();
        groupAchievement.setUpdatedBy(currentUser);

        groupAchievement = groupAchievementRepository.save(groupAchievement);
        if (shouldUpdateAssSum) {
            entityManager.flush();
            assessmentSummarySvc.recalculateAllAssessmentSummaries();
        }

        return convertToDto(groupAchievement);
    }

    @Transactional
    @Override
    public boolean deleteGroupAchievementById(UUID id) {
        Optional<GroupAchievement> groupAchievementOpt = groupAchievementRepository.findById(id);
        if (groupAchievementOpt.isEmpty()) {
            throw ResponseException.groupAchievementNotFound(id);
        }
        groupAchievementRepository.delete(groupAchievementOpt.get());

        entityManager.flush();
        assessmentSummarySvc.recalculateAllAssessmentSummaries();

        return true;
    }

    public GroupAchievementDto convertToDto(GroupAchievement data) {
        return new GroupAchievementDto(data, true, true, true);
    }
}
