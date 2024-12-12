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
import ogya.lokakarya.be.dto.groupattitudeskill.GroupAttitudeSkillDto;
import ogya.lokakarya.be.dto.groupattitudeskill.GroupAttitudeSkillReq;
import ogya.lokakarya.be.entity.GroupAttitudeSkill;
import ogya.lokakarya.be.exception.ResponseException;
import ogya.lokakarya.be.repository.GroupAttitudeSkillRepository;
import ogya.lokakarya.be.service.AssessmentSummaryService;
import ogya.lokakarya.be.service.GroupAttitudeSkillService;

@Service
public class GroupAttitudeSKillServiceImpl implements GroupAttitudeSkillService {
    @Autowired
    private GroupAttitudeSkillRepository groupAttitudeSkillRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AssessmentSummaryService assessmentSummarySvc;

    @Transactional
    @Override
    public GroupAttitudeSkillDto create(GroupAttitudeSkillReq data) {
        GroupAttitudeSkill groupAttitudeSkillEntity = data.toEntity();
        groupAttitudeSkillEntity = groupAttitudeSkillRepository.save(groupAttitudeSkillEntity);
        if (groupAttitudeSkillEntity.getPercentage() > 0) {
            entityManager.flush();
            assessmentSummarySvc.recalculateAllAssessmentSummaries();
        }
        return new GroupAttitudeSkillDto(groupAttitudeSkillEntity, false);
    }

    @Override
    public List<GroupAttitudeSkillDto> getAllGroupAttitudeSkills() {
        List<GroupAttitudeSkillDto> listResult = new ArrayList<>();
        List<GroupAttitudeSkill> groupAttitudeSkillList = groupAttitudeSkillRepository.findAll();
        for (GroupAttitudeSkill groupAttitudeSkill : groupAttitudeSkillList) {
            GroupAttitudeSkillDto result = convertToDto(groupAttitudeSkill);
            listResult.add(result);
        }
        return listResult;
    }

    @Override
    public GroupAttitudeSkillDto getGroupAttitudeSkillById(UUID id) {
        Optional<GroupAttitudeSkill> listData;
        try {
            listData = groupAttitudeSkillRepository.findById(id);
            System.out.println(listData);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        GroupAttitudeSkill data = listData.get();
        return convertToDto(data);
    }

    @Transactional
    @Override
    public GroupAttitudeSkillDto updateGroupAttitudeSkillById(UUID id,
            GroupAttitudeSkillReq groupAttitudeSkillReq) {
        Optional<GroupAttitudeSkill> groupAttitudeSkillOpt = groupAttitudeSkillRepository.findById(id);
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
            shouldUpdateAssSum = groupAttitudeSkillReq.getPercentage().equals(groupAttitudeSkill.getPercentage());
            groupAttitudeSkill.setPercentage(groupAttitudeSkillReq.getPercentage());
        }
        groupAttitudeSkill = groupAttitudeSkillRepository.save(groupAttitudeSkill);

        if (shouldUpdateAssSum) {
            entityManager.flush();
            assessmentSummarySvc.recalculateAllAssessmentSummaries();
        }

        return convertToDto(groupAttitudeSkill);
    }

    @Transactional
    @Override
    public boolean deleteGroupAttitudeSkillById(UUID id) {
        Optional<GroupAttitudeSkill> groupAttitudeSkillOpt = groupAttitudeSkillRepository.findById(id);
        if (groupAttitudeSkillOpt.isEmpty()) {
            throw ResponseException.groupAttitudeSkillNotFound(id);
        }
        groupAttitudeSkillRepository.delete(groupAttitudeSkillOpt.get());

        entityManager.flush();
        assessmentSummarySvc.recalculateAllAssessmentSummaries();

        return ResponseEntity.ok().build().hasBody();

    }

    private GroupAttitudeSkillDto convertToDto(GroupAttitudeSkill data) {
        return new GroupAttitudeSkillDto(data, true);
    }
}
