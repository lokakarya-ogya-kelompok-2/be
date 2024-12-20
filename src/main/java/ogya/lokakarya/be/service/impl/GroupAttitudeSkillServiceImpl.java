package ogya.lokakarya.be.service.impl;

import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import ogya.lokakarya.be.config.security.SecurityUtil;
import ogya.lokakarya.be.dto.groupattitudeskill.GroupAttitudeSkillDto;
import ogya.lokakarya.be.dto.groupattitudeskill.GroupAttitudeSkillFilter;
import ogya.lokakarya.be.dto.groupattitudeskill.GroupAttitudeSkillReq;
import ogya.lokakarya.be.entity.GroupAttitudeSkill;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.exception.ResponseException;
import ogya.lokakarya.be.repository.GroupAttitudeSkillRepository;
import ogya.lokakarya.be.repository.specification.GroupAttitudeSkillSpecification;
import ogya.lokakarya.be.service.AssessmentSummaryService;
import ogya.lokakarya.be.service.GroupAttitudeSkillService;

@Slf4j
@Service
public class GroupAttitudeSkillServiceImpl implements GroupAttitudeSkillService {
    @Autowired
    private GroupAttitudeSkillRepository groupAttitudeSkillRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AssessmentSummaryService assessmentSummarySvc;

    @Autowired
    private SecurityUtil securityUtil;

    @Transactional
    @Override
    public GroupAttitudeSkillDto create(GroupAttitudeSkillReq data) {
        log.info("Starting GroupAttitudeSkillServiceImpl.create");
        GroupAttitudeSkill groupAttitudeSkillEntity = data.toEntity();
        User currentUser = securityUtil.getCurrentUser();
        groupAttitudeSkillEntity.setCreatedBy(currentUser);
        groupAttitudeSkillEntity = groupAttitudeSkillRepository.save(groupAttitudeSkillEntity);
        if (groupAttitudeSkillEntity.getPercentage() > 0) {
            entityManager.flush();
            assessmentSummarySvc.recalculateAllAssessmentSummaries();
        }
        log.info("Ending GroupAttitudeSkillServiceImpl.create");
        return new GroupAttitudeSkillDto(groupAttitudeSkillEntity, true, false, false, false);
    }

    @Override
    public Page<GroupAttitudeSkillDto> getAllGroupAttitudeSkills(GroupAttitudeSkillFilter filter) {
        log.info("Starting GroupAttitudeSkillServiceImpl.getAllGroupAttitudeSkills");
        filter.validate();
        Page<GroupAttitudeSkill> groupAttitudeSkills;
        if (filter.getPageNumber() != null) {
            Pageable pageable = PageRequest.of(Math.max(0, filter.getPageNumber() - 1),
                    Math.max(1, filter.getPageSize()), Sort.by("createdAt").descending());
            groupAttitudeSkills = groupAttitudeSkillRepository
                    .findAll(GroupAttitudeSkillSpecification.filter(filter), pageable);
        } else {
            groupAttitudeSkills = new PageImpl<>(groupAttitudeSkillRepository.findAll(
                    GroupAttitudeSkillSpecification.filter(filter),
                    Sort.by("createdAt").descending()));
        }
        log.info("Ending GroupAttitudeSkillServiceImpl.getAllGroupAttitudeSkills");
        return groupAttitudeSkills
                .map(groupAttitudeSkill -> new GroupAttitudeSkillDto(groupAttitudeSkill,
                        filter.getWithCreatedBy(), filter.getWithUpdatedBy(),
                        filter.getWithAttitudeSkills(), filter.getWithEnabledChildOnly()));
    }

    @Override
    public GroupAttitudeSkillDto getGroupAttitudeSkillById(UUID id) {
        log.info("Starting GroupAttitudeSkillServiceImpl.getGroupAttitudeSkillById");
        Optional<GroupAttitudeSkill> groupAttitudeSkillOpt =
                groupAttitudeSkillRepository.findById(id);
        if (groupAttitudeSkillOpt.isEmpty()) {
            throw ResponseException.groupAttitudeSkillNotFound(id);
        }
        log.info("Ending GroupAttitudeSkillServiceImpl.getGroupAttitudeSkillById");
        GroupAttitudeSkill data = groupAttitudeSkillOpt.get();
        return convertToDto(data);
    }

    @Transactional
    @Override
    public GroupAttitudeSkillDto updateGroupAttitudeSkillById(UUID id,
            GroupAttitudeSkillReq groupAttitudeSkillReq) {
        log.info("Starting GroupAttitudeSkillServiceImpl.updateGroupAttitudeSkillById");
        Optional<GroupAttitudeSkill> groupAttitudeSkillOpt =
                groupAttitudeSkillRepository.findById(id);
        if (groupAttitudeSkillOpt.isEmpty()) {
            throw ResponseException.groupAttitudeSkillNotFound(id);
        }
        boolean shouldUpdateAssSum = false;
        GroupAttitudeSkill groupAttitudeSkill = groupAttitudeSkillOpt.get();
        if (groupAttitudeSkillReq.getGroupName() != null) {
            groupAttitudeSkill.setGroupName(groupAttitudeSkillReq.getGroupName());
        }
        if (groupAttitudeSkillReq.getEnabled() != null) {
            groupAttitudeSkill.setEnabled(groupAttitudeSkillReq.getEnabled());
        }
        if (groupAttitudeSkillReq.getPercentage() != null) {
            shouldUpdateAssSum = groupAttitudeSkillReq.getPercentage()
                    .equals(groupAttitudeSkill.getPercentage());
            groupAttitudeSkill.setPercentage(groupAttitudeSkillReq.getPercentage());
        }
        User currentUser = securityUtil.getCurrentUser();
        groupAttitudeSkill.setUpdatedBy(currentUser);

        groupAttitudeSkill = groupAttitudeSkillRepository.save(groupAttitudeSkill);
        if (shouldUpdateAssSum) {
            entityManager.flush();
            assessmentSummarySvc.recalculateAllAssessmentSummaries();
        }
        log.info("Ending GroupAttitudeSkillServiceImpl.updateGroupAttitudeSkillById");
        return convertToDto(groupAttitudeSkill);
    }

    @Transactional
    @Override
    public boolean deleteGroupAttitudeSkillById(UUID id) {
        log.info("Starting GroupAttitudeSkillServiceImpl.deleteGroupAttitudeSkillById");
        Optional<GroupAttitudeSkill> groupAttitudeSkillOpt =
                groupAttitudeSkillRepository.findById(id);
        if (groupAttitudeSkillOpt.isEmpty()) {
            throw ResponseException.groupAttitudeSkillNotFound(id);
        }
        groupAttitudeSkillRepository.delete(groupAttitudeSkillOpt.get());

        entityManager.flush();
        assessmentSummarySvc.recalculateAllAssessmentSummaries();
        log.info("Ending GroupAttitudeSkillServiceImpl.deleteGroupAttitudeSkillById");
        return ResponseEntity.ok().build().hasBody();

    }

    private GroupAttitudeSkillDto convertToDto(GroupAttitudeSkill data) {
        return new GroupAttitudeSkillDto(data, true, true, false, false);
    }
}
