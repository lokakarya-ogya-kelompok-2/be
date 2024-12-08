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
import ogya.lokakarya.be.dto.emptechnicalskill.EmpTechnicalSkillDto;
import ogya.lokakarya.be.dto.emptechnicalskill.EmpTechnicalSkillFilter;
import ogya.lokakarya.be.dto.emptechnicalskill.EmpTechnicalSkillReq;
import ogya.lokakarya.be.entity.EmpTechnicalSkill;
import ogya.lokakarya.be.entity.TechnicalSkill;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.exception.ResponseException;
import ogya.lokakarya.be.repository.EmpTechnicalSkillRepository;
import ogya.lokakarya.be.repository.TechnicalSkillRepository;
import ogya.lokakarya.be.service.EmpTechnicalSkillService;

@Service
public class EmpTechnicalSkillServiceImpl implements EmpTechnicalSkillService {
    @Autowired
    private EmpTechnicalSkillRepository empTechnicalSkillRepository;

    @Autowired
    private TechnicalSkillRepository technicalSkillRepository;

    @Autowired
    private SecurityUtil securityUtil;

    @Override
    public List<EmpTechnicalSkillDto> createBulk(List<EmpTechnicalSkillReq> data) {
        User currentUser = securityUtil.getCurrentUser();
        List<EmpTechnicalSkill> empTechnicalSkillEntities = new ArrayList<>(data.size());
        Map<UUID, TechnicalSkill> technicalSkillIds = new HashMap<>();

        for (EmpTechnicalSkillReq d : data) {
            EmpTechnicalSkill empTechnicalSkillEntity = d.toEntity();
            empTechnicalSkillEntity.setUser(currentUser);
            empTechnicalSkillEntity.setCreatedBy(currentUser);
            if (!technicalSkillIds.containsKey(d.getTechnicalSkillId())) {
                Optional<TechnicalSkill> technicalSkillOpt =
                        technicalSkillRepository.findById(d.getTechnicalSkillId());
                if (technicalSkillOpt.isEmpty()) {
                    throw ResponseException.technicalSkillNotFound(d.getTechnicalSkillId());
                }
                TechnicalSkill technicalSkill = technicalSkillOpt.get();
                technicalSkillIds.put(d.getTechnicalSkillId(), technicalSkill);
            }
            empTechnicalSkillEntity
                    .setTechnicalSkill(technicalSkillIds.get(d.getTechnicalSkillId()));
            empTechnicalSkillEntities.add(empTechnicalSkillEntity);
        }

        empTechnicalSkillEntities = empTechnicalSkillRepository.saveAll(empTechnicalSkillEntities);

        return empTechnicalSkillEntities.stream()
                .map(empTechSkill -> new EmpTechnicalSkillDto(empTechSkill, true, false)).toList();
    }

    @Override
    public EmpTechnicalSkillDto create(EmpTechnicalSkillReq data) {
        EmpTechnicalSkill empTechnicalSkillEntity = data.toEntity();
        User currentUser = securityUtil.getCurrentUser();
        empTechnicalSkillEntity.setUser(currentUser);

        Optional<TechnicalSkill> technicalSkillOpt =
                technicalSkillRepository.findById(data.getTechnicalSkillId());
        if (technicalSkillOpt.isEmpty()) {
            throw ResponseException.technicalSkillNotFound(data.getTechnicalSkillId());
        }
        TechnicalSkill technicalSkill = technicalSkillOpt.get();
        empTechnicalSkillEntity.setTechnicalSkill(technicalSkill);
        empTechnicalSkillEntity.setAssessmentYear(data.getAssessmentYear());
        empTechnicalSkillEntity.setCreatedBy(currentUser);

        empTechnicalSkillEntity = empTechnicalSkillRepository.save(empTechnicalSkillEntity);
        return new EmpTechnicalSkillDto(empTechnicalSkillEntity, true, false);
    }

    @Override
    public List<EmpTechnicalSkillDto> getAllEmpTechnicalSkills(EmpTechnicalSkillFilter filter) {
        List<EmpTechnicalSkill> empTechnicalSkillEntities =
                empTechnicalSkillRepository.findAllByFilter(filter);
        return empTechnicalSkillEntities.stream()
                .map(empAttSkill -> new EmpTechnicalSkillDto(empAttSkill, filter.getWithCreatedBy(),
                        filter.getWithUpdatedBy()))
                .toList();
    }

    @Override
    public EmpTechnicalSkillDto getEmpTechnicalSkillById(UUID id) {
        Optional<EmpTechnicalSkill> listData;
        try {
            listData = empTechnicalSkillRepository.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        EmpTechnicalSkill data = listData.get();
        return convertToDto(data);
    }

    @Override
    public EmpTechnicalSkillDto updateEmpTechnicalSkillById(UUID id,
            EmpTechnicalSkillReq empTechnicalSkillReq) {
        User currentUser = securityUtil.getCurrentUser();
        Optional<EmpTechnicalSkill> empTechnicalSkillOpt = empTechnicalSkillRepository.findById(id);
        if (empTechnicalSkillOpt.isEmpty()) {
            throw ResponseException.empTechnicalSkillNotFound(id);
        }

        EmpTechnicalSkill empTechnicalSkill = empTechnicalSkillOpt.get();
        if (empTechnicalSkillReq.getScore() != null) {
            empTechnicalSkill.setScore(empTechnicalSkillReq.getScore());
        }
        if (empTechnicalSkillReq.getDetail() != null) {
            empTechnicalSkill.setTechnicalSkilLDetail(empTechnicalSkillReq.getDetail());
        }
        empTechnicalSkill.setUpdatedBy(currentUser);

        empTechnicalSkill = empTechnicalSkillRepository.save(empTechnicalSkill);

        return new EmpTechnicalSkillDto(empTechnicalSkill, true, true);
    }

    @Override
    public boolean deleteEmpTechnicalSkillById(UUID id) {
        Optional<EmpTechnicalSkill> listData = empTechnicalSkillRepository.findById(id);
        if (listData.isPresent()) {
            empTechnicalSkillRepository.delete(listData.get());
            return ResponseEntity.ok().build().hasBody();
        } else {
            return ResponseEntity.notFound().build().hasBody();
        }
    }

    public EmpTechnicalSkillDto convertToDto(EmpTechnicalSkill data) {
        return new EmpTechnicalSkillDto(data, true, true);
    }
}
