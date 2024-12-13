package ogya.lokakarya.be.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import ogya.lokakarya.be.config.security.SecurityUtil;
import ogya.lokakarya.be.dto.attitudeskill.AttitudeSkillDto;
import ogya.lokakarya.be.dto.attitudeskill.AttitudeSkillReq;
import ogya.lokakarya.be.entity.AttitudeSkill;
import ogya.lokakarya.be.entity.GroupAttitudeSkill;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.exception.ResponseException;
import ogya.lokakarya.be.repository.AttitudeSkillRepository;
import ogya.lokakarya.be.repository.GroupAttitudeSkillRepository;
import ogya.lokakarya.be.service.AssessmentSummaryService;
import ogya.lokakarya.be.service.AttitudeSkillService;

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

        return new AttitudeSkillDto(createdData, true, true, false);
    }

    @Override
    public List<AttitudeSkillDto> getAllAttitudeSkills() {
        List<AttitudeSkillDto> listResult = new ArrayList<>();
        List<AttitudeSkill> attitudeSkillList = attitudeSkillRepository.findAll();
        for (AttitudeSkill attitudeSkill : attitudeSkillList) {
            AttitudeSkillDto result = convertToDto(attitudeSkill);
            listResult.add(result);
        }
        return listResult;
    }

    @Override
    public AttitudeSkillDto getAttitudeSkillById(UUID id) {
        Optional<AttitudeSkill> attitudeSkillOpt = attitudeSkillRepository.findById(id);
        if (attitudeSkillOpt.isEmpty()) {
            throw ResponseException.attitudeSkillNotFound(id);
        }
        AttitudeSkill data = attitudeSkillOpt.get();
        return convertToDto(data);
    }

    @Override
    public AttitudeSkillDto updateAttitudeSkillById(UUID id, AttitudeSkillReq attitudeSkillReq) {
        Optional<AttitudeSkill> attitudeSkillOpt = attitudeSkillRepository.findById(id);
        if (attitudeSkillOpt.isEmpty()) {
            throw ResponseException.attitudeSkillNotFound(id);
        }

        AttitudeSkill attitudeSkill = attitudeSkillOpt.get();
        if (attitudeSkillReq.getAttitudeSkill() != null) {
            attitudeSkill.setName(attitudeSkillReq.getAttitudeSkill());
        }
        if (attitudeSkillReq.getEnabled() != null) {
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
        return convertToDto(attitudeSkill);

    }

    @Transactional
    @Override
    public boolean deleteAttitudeSkillById(UUID id) {
        Optional<AttitudeSkill> attitudeSkillOpt = attitudeSkillRepository.findById(id);
        if (attitudeSkillOpt.isEmpty()) {
            throw ResponseException.attitudeSkillNotFound(id);
        }
        attitudeSkillRepository.delete(attitudeSkillOpt.get());

        entityManager.flush();
        assessmentSummarySvc.recalculateAllAssessmentSummaries();

        return ResponseEntity.ok().build().hasBody();
    }

    private AttitudeSkillDto convertToDto(AttitudeSkill data) {
        return new AttitudeSkillDto(data, true, true, true);
    }

}
