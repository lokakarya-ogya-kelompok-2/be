package ogya.lokakarya.be.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
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
        log.info("Starting EmpTechnicalSkillServiceImpl.createBulk");
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
        log.info("Ending EmpTechnicalSkillServiceImpl.createBulk");
        return empTechnicalSkillEntities.stream()
                .map(empTechSkill -> new EmpTechnicalSkillDto(empTechSkill, true, false)).toList();
    }

    @Override
    public EmpTechnicalSkillDto create(EmpTechnicalSkillReq data) {
        log.info("Starting EmpTechnicalSkillServiceImpl.create");
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
        log.info("Ending EmpTechnicalSkillServiceImpl.create");
        return new EmpTechnicalSkillDto(empTechnicalSkillEntity, true, false);
    }

    @Override
    public List<EmpTechnicalSkillDto> getAllEmpTechnicalSkills(EmpTechnicalSkillFilter filter) {
        log.info("Starting EmpTechnicalSkillServiceImpl.getAllEmpTechnicalSkills");
        List<EmpTechnicalSkill> empTechnicalSkillEntities =
                empTechnicalSkillRepository.findAllByFilter(filter);
        log.info("Ending EmpTechnicalSkillServiceImpl.getAllEmpTechnicalSkills");
        return empTechnicalSkillEntities.stream()
                .map(empAttSkill -> new EmpTechnicalSkillDto(empAttSkill, filter.getWithCreatedBy(),
                        filter.getWithUpdatedBy()))
                .toList();
    }

    @Override
    public EmpTechnicalSkillDto getEmpTechnicalSkillById(UUID id) {
        log.info("Starting EmpTechnicalSkillServiceImpl.getEmpTechnicalSkillById");
        Optional<EmpTechnicalSkill> empTechnicalSkillOpt = empTechnicalSkillRepository.findById(id);
        if (empTechnicalSkillOpt.isEmpty()) {
            throw ResponseException.empTechnicalSkillNotFound(id);
        }
        EmpTechnicalSkill data = empTechnicalSkillOpt.get();
        log.info("Ending EmpTechnicalSkillServiceImpl.getEmpTechnicalSkillById");
        return convertToDto(data);
    }

    @Override
    public EmpTechnicalSkillDto updateEmpTechnicalSkillById(UUID id,
            EmpTechnicalSkillReq empTechnicalSkillReq) {
        log.info("Starting EmpTechnicalSkillServiceImpl.updateEmpTechnicalSkillById");
        Optional<EmpTechnicalSkill> empTechnicalSkillOpt = empTechnicalSkillRepository.findById(id);
        if (empTechnicalSkillOpt.isEmpty()) {
            throw ResponseException.empTechnicalSkillNotFound(id);
        }
        User currentUser = securityUtil.getCurrentUser();
        EmpTechnicalSkill empTechnicalSkill = empTechnicalSkillOpt.get();
        if (!currentUser.equals(empTechnicalSkill.getUser())) {
            throw ResponseException.unauthorized();
        }
        Integer currentYear = LocalDate.now().getYear();
        if (!currentYear.equals(empTechnicalSkill.getAssessmentYear())) {
            throw ResponseException.unauthorized();
        }

        if (empTechnicalSkillReq.getScore() != null) {
            empTechnicalSkill.setScore(empTechnicalSkillReq.getScore());
        }
        if (empTechnicalSkillReq.getDetail() != null) {
            empTechnicalSkill.setTechnicalSkilLDetail(empTechnicalSkillReq.getDetail());
        }
        empTechnicalSkill.setUpdatedBy(currentUser);

        empTechnicalSkill = empTechnicalSkillRepository.save(empTechnicalSkill);
        log.info("Ending EmpTechnicalSkillServiceImpl.updateEmpTechnicalSkillById");
        return new EmpTechnicalSkillDto(empTechnicalSkill, true, true);
    }

    @Override
    public boolean deleteEmpTechnicalSkillById(UUID id) {
        log.info("Starting EmpTechnicalSkillServiceImpl.deleteEmpTechnicalSkillById");
        Optional<EmpTechnicalSkill> empTechnicalSkillOpt = empTechnicalSkillRepository.findById(id);
        if (empTechnicalSkillOpt.isEmpty()) {
            throw ResponseException.empTechnicalSkillNotFound(id);
        }
        User currentUser = securityUtil.getCurrentUser();
        EmpTechnicalSkill empTechnicalSkill = empTechnicalSkillOpt.get();
        if (!currentUser.equals(empTechnicalSkill.getUser())) {
            throw ResponseException.unauthorized();
        }
        Integer currentYear = LocalDate.now().getYear();
        if (!currentYear.equals(empTechnicalSkill.getAssessmentYear())) {
            throw ResponseException.unauthorized();
        }
        empTechnicalSkillRepository.delete(empTechnicalSkill);
        log.info("Ending EmpTechnicalSkillServiceImpl.deleteEmpTechnicalSkillById");

        return true;
    }

    public EmpTechnicalSkillDto convertToDto(EmpTechnicalSkill data) {
        return new EmpTechnicalSkillDto(data, true, true);
    }
}
