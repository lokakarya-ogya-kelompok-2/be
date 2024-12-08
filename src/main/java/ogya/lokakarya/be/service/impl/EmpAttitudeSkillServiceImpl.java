package ogya.lokakarya.be.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ogya.lokakarya.be.config.security.SecurityUtil;
import ogya.lokakarya.be.dto.empattitudeskill.EmpAttitudeSkillDto;
import ogya.lokakarya.be.dto.empattitudeskill.EmpAttitudeSkillFilter;
import ogya.lokakarya.be.dto.empattitudeskill.EmpAttitudeSkillReq;
import ogya.lokakarya.be.entity.AttitudeSkill;
import ogya.lokakarya.be.entity.EmpAttitudeSkill;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.exception.ResponseException;
import ogya.lokakarya.be.repository.AttitudeSkillRepository;
import ogya.lokakarya.be.repository.EmpAttitudeSkillRepository;
import ogya.lokakarya.be.service.EmpAttitudeSkillService;

@Service
public class EmpAttitudeSkillServiceImpl implements EmpAttitudeSkillService {
    @Autowired
    private EmpAttitudeSkillRepository empAttitudeSkillRepository;
    @Autowired
    private AttitudeSkillRepository attitudeSkillRepository;
    @Autowired
    private SecurityUtil securityUtil;

    @Override
    public List<EmpAttitudeSkillDto> createBulkEmpAttitudeSkill(List<EmpAttitudeSkillReq> data) {
        User currentUser = securityUtil.getCurrentUser();
        List<EmpAttitudeSkill> empAttitudeSkillsEntities = new ArrayList<>(data.size());
        Map<UUID, AttitudeSkill> attitudeSkillIds = new HashMap<>();
        for (EmpAttitudeSkillReq d : data) {
            EmpAttitudeSkill empAttitudeSkillEntity = d.toEntity();
            empAttitudeSkillEntity.setUser(currentUser);
            empAttitudeSkillEntity.setCreatedBy(currentUser);
            if (!attitudeSkillIds.containsKey(d.getAttitudeSkillId())) {
                Optional<AttitudeSkill> attitudeSkillOpt =
                        attitudeSkillRepository.findById(d.getAttitudeSkillId());
                if (attitudeSkillOpt.isEmpty()) {
                    throw ResponseException.empAttitudeSkillNotFound(d.getAttitudeSkillId());
                }
                AttitudeSkill attitudeSkill = attitudeSkillOpt.get();
                attitudeSkillIds.put(d.getAttitudeSkillId(), attitudeSkill);
            }
            empAttitudeSkillEntity.setAttitudeSkill(attitudeSkillIds.get(d.getAttitudeSkillId()));
            empAttitudeSkillsEntities.add(empAttitudeSkillEntity);
        }
        empAttitudeSkillsEntities = empAttitudeSkillRepository.saveAll(empAttitudeSkillsEntities);
        return empAttitudeSkillsEntities.stream()
                .map(empAttSkill -> new EmpAttitudeSkillDto(empAttSkill, true, false)).toList();
    }

    @Override
    public EmpAttitudeSkillDto create(EmpAttitudeSkillReq data) {
        Optional<AttitudeSkill> findAttitudeSkill =
                attitudeSkillRepository.findById(data.getAttitudeSkillId());
        User currentUser = securityUtil.getCurrentUser();
        if (findAttitudeSkill.isEmpty()) {
            throw ResponseException.attitudeSkillNotFound(data.getAttitudeSkillId());
        }
        EmpAttitudeSkill dataEntity = data.toEntity();
        dataEntity.setUser(currentUser);
        dataEntity.setCreatedBy(currentUser);
        dataEntity.setAttitudeSkill(findAttitudeSkill.get());
        EmpAttitudeSkill createData = empAttitudeSkillRepository.save(dataEntity);
        return new EmpAttitudeSkillDto(createData, true, false);
    }

    @Override
    public List<EmpAttitudeSkillDto> getAllEmpAttitudeSkills(EmpAttitudeSkillFilter filter) {
        List<EmpAttitudeSkill> empAttitudeSkillEntities =
                empAttitudeSkillRepository.findAllByFilter(filter);
        return empAttitudeSkillEntities.stream()
                .map(empAttSkill -> new EmpAttitudeSkillDto(empAttSkill, filter.getWithCreatedBy(),
                        filter.getWithUpdatedBy()))
                .toList();
    }

    @Override
    public EmpAttitudeSkillDto getEmpAttitudeSkillById(UUID id) {
        Optional<EmpAttitudeSkill> listData;
        try {
            listData = empAttitudeSkillRepository.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        EmpAttitudeSkill data = listData.get();
        return new EmpAttitudeSkillDto(data, true, true);
    }

    @Override
    public EmpAttitudeSkillDto updateEmpAttitudeSkillById(UUID id,
            EmpAttitudeSkillReq empAttitudeSkillReq) {
        Optional<EmpAttitudeSkill> listData = empAttitudeSkillRepository.findById(id);
        if (listData.isPresent()) {
            EmpAttitudeSkill empAttitudeSkill = listData.get();
            if (empAttitudeSkillReq.getScore() != null) {
                empAttitudeSkill.setScore(empAttitudeSkillReq.getScore());
                empAttitudeSkill.setAssessmentYear(empAttitudeSkillReq.getAssessmentYear());
            }
            EmpAttitudeSkillDto empAttitudeSkillDto = convertToDto(empAttitudeSkill);
            empAttitudeSkillRepository.save(empAttitudeSkill);
            return empAttitudeSkillDto;
        }
        return null;
    }

    @Override
    public boolean deleteEmpAttitudeSkillById(UUID id) {
        Optional<EmpAttitudeSkill> listData = empAttitudeSkillRepository.findById(id);
        if (listData.isPresent()) {
            empAttitudeSkillRepository.delete(listData.get());
            return ResponseEntity.ok().build().hasBody();
        } else {
            return ResponseEntity.notFound().build().hasBody();
        }
    }

    public EmpAttitudeSkillDto convertToDto(EmpAttitudeSkill data) {
        return new EmpAttitudeSkillDto(data, true, true);
    }
}
