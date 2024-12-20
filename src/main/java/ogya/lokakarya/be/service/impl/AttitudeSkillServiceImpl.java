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
import ogya.lokakarya.be.dto.attitudeskill.AttitudeSkillDto;
import ogya.lokakarya.be.dto.attitudeskill.AttitudeSkillFilter;
import ogya.lokakarya.be.dto.attitudeskill.AttitudeSkillReq;
import ogya.lokakarya.be.entity.AttitudeSkill;
import ogya.lokakarya.be.entity.GroupAttitudeSkill;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.exception.ResponseException;
import ogya.lokakarya.be.repository.AttitudeSkillRepository;
import ogya.lokakarya.be.repository.GroupAttitudeSkillRepository;
import ogya.lokakarya.be.service.AssessmentSummaryService;
import ogya.lokakarya.be.service.AttitudeSkillService;
import ogya.lokakarya.be.specification.AttitudeSkillSpecification;

@Slf4j
@Service
public class AttitudeSkillServiceImpl implements AttitudeSkillService {
    @Autowired
    private AttitudeSkillRepository attitudeSkillRepository;
    @Autowired
    private GroupAttitudeSkillRepository groupAttitudeSkillRepository;

    @Autowired
    private AssessmentSummaryService assessmentSummarySvc;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private SecurityUtil securityUtil;

    @Transactional
    @Override
    public AttitudeSkillDto create(AttitudeSkillReq data) {
        log.info("Starting AttitudeSkillServiceImpl.create");
        Optional<GroupAttitudeSkill> groupAttitudeOpt =
                groupAttitudeSkillRepository.findById(data.getGroupAttitudeSkillId());
        if (groupAttitudeOpt.isEmpty()) {
            throw ResponseException.groupAttitudeSkillNotFound(data.getGroupAttitudeSkillId());
        }
        AttitudeSkill dataEntity = data.toEntity();
        dataEntity.setGroupAttitudeSkill(groupAttitudeOpt.get());
        User currentUser = securityUtil.getCurrentUser();
        dataEntity.setCreatedBy(currentUser);
        AttitudeSkill createdData = attitudeSkillRepository.save(dataEntity);
        entityManager.flush();
        assessmentSummarySvc.recalculateAllAssessmentSummaries();
        log.info("Ending AttitudeSkillServiceImpl.create");
        return new AttitudeSkillDto(createdData, true, false, true);
    }

    @Override
    public Page<AttitudeSkillDto> getAllAttitudeSkills(AttitudeSkillFilter filter) {
        log.info("Starting AttitudeSkillServiceImpl.getAllAttitudeSkills");
        Page<AttitudeSkill> attitudeSkills;
        if (filter.getPageNumber() != null) {
            Pageable pageable = PageRequest.of(Math.max(0, filter.getPageNumber() - 1),
                    Math.max(1, filter.getPageSize()), Sort.by("createdAt").descending());
            attitudeSkills = attitudeSkillRepository
                    .findAll(AttitudeSkillSpecification.filter(filter), pageable);
        } else {
            attitudeSkills = new PageImpl<>(attitudeSkillRepository.findAll(
                    AttitudeSkillSpecification.filter(filter), Sort.by("createdAt").descending()));
        }
        log.info("Ending AttitudeSkillServiceImpl.getAllAttitudeSkills");
        return attitudeSkills.map(attitudeSkill -> new AttitudeSkillDto(attitudeSkill,
                filter.getWithCreatedBy(), filter.getWithUpdatedBy(), filter.getWithGroup()));
    }

    @Override
    public AttitudeSkillDto getAttitudeSkillById(UUID id) {
        log.info("Starting AttitudeSkillServiceImpl.getAttitudeSkillById");
        Optional<AttitudeSkill> attitudeSkillOpt = attitudeSkillRepository.findById(id);
        if (attitudeSkillOpt.isEmpty()) {
            throw ResponseException.attitudeSkillNotFound(id);
        }
        AttitudeSkill data = attitudeSkillOpt.get();
        log.info("Ending AttitudeSkillServiceImpl.getAttitudeSkillById");
        return convertToDto(data);
    }

    @Transactional
    @Override
    public AttitudeSkillDto updateAttitudeSkillById(UUID id, AttitudeSkillReq attitudeSkillReq) {
        log.info("Starting AttitudeSkillServiceImpl.updateAttitudeSkillById");
        Optional<AttitudeSkill> attitudeSkillOpt = attitudeSkillRepository.findById(id);
        if (attitudeSkillOpt.isEmpty()) {
            throw ResponseException.attitudeSkillNotFound(id);
        }
        boolean shouldRecalculateAllAssSum = false;
        AttitudeSkill attitudeSkill = attitudeSkillOpt.get();
        if (attitudeSkillReq.getAttitudeSkill() != null) {
            attitudeSkill.setName(attitudeSkillReq.getAttitudeSkill());
        }
        if (attitudeSkillReq.getEnabled() != null) {
            shouldRecalculateAllAssSum =
                    !attitudeSkillReq.getEnabled().equals(attitudeSkill.getEnabled());
            attitudeSkill.setEnabled(attitudeSkillReq.getEnabled());
        }
        if (attitudeSkillReq.getGroupAttitudeSkillId() != null) {
            Optional<GroupAttitudeSkill> groupAttitudeSkillOpt = groupAttitudeSkillRepository
                    .findById(attitudeSkillReq.getGroupAttitudeSkillId());
            if (groupAttitudeSkillOpt.isEmpty()) {
                throw ResponseException
                        .groupAttitudeSkillNotFound(attitudeSkillReq.getGroupAttitudeSkillId());
            }
            attitudeSkill.setGroupAttitudeSkill(groupAttitudeSkillOpt.get());
        }
        User currentUser = securityUtil.getCurrentUser();
        attitudeSkill.setUpdatedBy(currentUser);
        attitudeSkill = attitudeSkillRepository.save(attitudeSkill);
        if (shouldRecalculateAllAssSum) {
            entityManager.flush();
            this.assessmentSummarySvc.recalculateAllAssessmentSummaries();
        }
        log.info("Ending AttitudeSkillServiceImpl.updateAttitudeSkillById");
        return convertToDto(attitudeSkill);
    }

    @Transactional
    @Override
    public boolean deleteAttitudeSkillById(UUID id) {
        log.info("Starting AttitudeSkillServiceImpl.deleteAttitudeSkillById");
        Optional<AttitudeSkill> attitudeSkillOpt = attitudeSkillRepository.findById(id);
        if (attitudeSkillOpt.isEmpty()) {
            throw ResponseException.attitudeSkillNotFound(id);
        }
        attitudeSkillRepository.delete(attitudeSkillOpt.get());
        entityManager.flush();
        assessmentSummarySvc.recalculateAllAssessmentSummaries();
        log.info("Ending AttitudeSkillServiceImpl.deleteAttitudeSkillById");
        return ResponseEntity.ok().build().hasBody();
    }

    private AttitudeSkillDto convertToDto(AttitudeSkill data) {
        return new AttitudeSkillDto(data, true, true, true);
    }

}
