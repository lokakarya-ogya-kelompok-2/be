package ogya.lokakarya.be.service.impl;

import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import ogya.lokakarya.be.config.security.SecurityUtil;
import ogya.lokakarya.be.dto.groupachievement.GroupAchievementDto;
import ogya.lokakarya.be.dto.groupachievement.GroupAchievementFilter;
import ogya.lokakarya.be.dto.groupachievement.GroupAchievementReq;
import ogya.lokakarya.be.entity.GroupAchievement;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.exception.ResponseException;
import ogya.lokakarya.be.repository.GroupAchievementRepository;
import ogya.lokakarya.be.repository.specification.GroupAchievementSpecification;
import ogya.lokakarya.be.service.AssessmentSummaryService;
import ogya.lokakarya.be.service.GroupAchievementService;

@Slf4j
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

    @Autowired
    private GroupAchievementSpecification spec;

    @Transactional
    @Override
    public GroupAchievementDto create(GroupAchievementReq data) {
        log.info("Starting GroupAchievementServiceImpl.create");
        User currentUser = securityUtil.getCurrentUser();
        GroupAchievement groupAchievementEntity = data.toEntity();
        groupAchievementEntity.setCreatedBy(currentUser);
        groupAchievementEntity = groupAchievementRepository.save(groupAchievementEntity);
        if (groupAchievementEntity.getWeight() > 0) {
            entityManager.flush();
            assessmentSummarySvc.recalculateAllAssessmentSummaries();
        }
        log.info("Ending GroupAchievementServiceImpl.create");
        return new GroupAchievementDto(groupAchievementEntity, true, false, false, false);
    }

    @Override
    public Page<GroupAchievementDto> getAllGroupAchievements(GroupAchievementFilter filter) {
        log.info("Starting GroupAchievementServiceImpl.getAllGroupAchievements");
        filter.validate();

        Specification<GroupAchievement> specification = Specification.where(null);
        if (filter.getNameContains() != null && !filter.getNameContains().isEmpty()) {
            specification = specification.and(spec.nameContains(filter.getNameContains()));
        }
        if (filter.getMinWeight() != null && filter.getMaxWeight() != null) {
            specification = specification
                    .and(spec.weightBetween(filter.getMinWeight(), filter.getMaxWeight()));
        } else if (filter.getMinWeight() != null) {
            specification = specification.and(spec.weightGte(filter.getMinWeight()));
        } else if (filter.getMaxWeight() != null) {
            specification = specification.and(spec.weightLte(filter.getMaxWeight()));
        }
        if (filter.getEnabledOnly().booleanValue()) {
            specification = specification.and(spec.enabledEquals(true));
        }

        Sort sortBy = Sort.by(filter.getSortDirection(), filter.getSortField());

        Page<GroupAchievement> groupAchievements;
        if (filter.getPageNumber() != null) {
            Pageable pageable =
                    PageRequest.of(filter.getPageNumber() - 1, filter.getPageSize(), sortBy);
            groupAchievements = groupAchievementRepository.findAll(specification, pageable);
        } else {
            groupAchievements =
                    new PageImpl<>(groupAchievementRepository.findAll(specification, sortBy));
        }

        log.info("Ending GroupAchievementServiceImpl.getAllGroupAchievements");
        return groupAchievements.map(groupAchievement -> new GroupAchievementDto(groupAchievement,
                filter.getWithCreatedBy(), filter.getWithUpdatedBy(), filter.getWithAchievements(),
                filter.getWithEnabledChildOnly()));
    }

    @Override
    public GroupAchievementDto getGroupAchievementById(UUID id) {
        log.info("Starting GroupAchievementServiceImpl.getGroupAchievementById");
        Optional<GroupAchievement> groupAchievementOpt = groupAchievementRepository.findById(id);
        if (groupAchievementOpt.isEmpty()) {
            throw ResponseException.groupAchievementNotFound(id);
        }
        GroupAchievement data = groupAchievementOpt.get();
        log.info("Ending GroupAchievementServiceImpl.getGroupAchievementById");
        return convertToDto(data);
    }

    @Transactional
    @Override
    public GroupAchievementDto updateGroupAchievementById(UUID id,
            GroupAchievementReq groupAchievementReq) {
        log.info("Starting GroupAchievementServiceImpl.updateGroupAchievementById");
        Optional<GroupAchievement> groupAchievementOpt = groupAchievementRepository.findById(id);
        if (groupAchievementOpt.isEmpty()) {
            throw ResponseException.groupAchievementNotFound(id);
        }
        GroupAchievement groupAchievement = groupAchievementOpt.get();
        boolean shouldUpdateAssSum = false;
        if (groupAchievementReq.getGroupName() != null) {
            groupAchievement.setName(groupAchievementReq.getGroupName());
            groupAchievement.setEnabled(groupAchievementReq.getEnabled());
            groupAchievement.setWeight(groupAchievementReq.getPercentage());
        }
        if (groupAchievementReq.getEnabled() != null) {
            groupAchievement.setEnabled(groupAchievementReq.getEnabled());
        }
        if (groupAchievementReq.getPercentage() != null) {
            shouldUpdateAssSum =
                    groupAchievementReq.getPercentage().equals(groupAchievement.getWeight());
            groupAchievementReq.setPercentage(groupAchievementReq.getPercentage());
        }
        User currentUser = securityUtil.getCurrentUser();
        groupAchievement.setUpdatedBy(currentUser);

        groupAchievement = groupAchievementRepository.save(groupAchievement);
        if (shouldUpdateAssSum) {
            entityManager.flush();
            assessmentSummarySvc.recalculateAllAssessmentSummaries();
        }
        log.info("Ending GroupAchievementServiceImpl.updateGroupAchievementById");
        return convertToDto(groupAchievement);
    }

    @Transactional
    @Override
    public boolean deleteGroupAchievementById(UUID id) {
        log.info("Starting GroupAchievementServiceImpl.deleteGroupAchievementById");
        Optional<GroupAchievement> groupAchievementOpt = groupAchievementRepository.findById(id);
        if (groupAchievementOpt.isEmpty()) {
            throw ResponseException.groupAchievementNotFound(id);
        }
        groupAchievementRepository.delete(groupAchievementOpt.get());
        entityManager.flush();
        assessmentSummarySvc.recalculateAllAssessmentSummaries();
        log.info("Ending GroupAchievementServiceImpl.deleteGroupAchievementById");
        return true;
    }

    public GroupAchievementDto convertToDto(GroupAchievement data) {
        return new GroupAchievementDto(data, true, true, true, false);
    }
}
