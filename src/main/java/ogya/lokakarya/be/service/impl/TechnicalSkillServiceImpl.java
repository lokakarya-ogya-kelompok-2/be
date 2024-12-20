package ogya.lokakarya.be.service.impl;

import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import ogya.lokakarya.be.config.security.SecurityUtil;
import ogya.lokakarya.be.dto.technicalskill.TechnicalSkillDto;
import ogya.lokakarya.be.dto.technicalskill.TechnicalSkillFilter;
import ogya.lokakarya.be.dto.technicalskill.TechnicalSkillReq;
import ogya.lokakarya.be.entity.TechnicalSkill;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.exception.ResponseException;
import ogya.lokakarya.be.repository.TechnicalSkillRepository;
import ogya.lokakarya.be.repository.specification.TechnicalSkillSpecification;
import ogya.lokakarya.be.service.TechnicalSkillService;

@Slf4j
@Service
public class TechnicalSkillServiceImpl implements TechnicalSkillService {
    @Autowired
    private TechnicalSkillRepository technicalSkillRepository;

    @Autowired
    private SecurityUtil securityUtil;

    @Override
    public TechnicalSkillDto create(TechnicalSkillReq data) {
        log.info("Starting EmpDevPlanServiceImpl.create");
        TechnicalSkill technicalSkill = data.toEntity();
        User currentUser = securityUtil.getCurrentUser();
        technicalSkill.setCreatedBy(currentUser);
        technicalSkill = technicalSkillRepository.save(technicalSkill);
        log.info("Ending EmpDevPlanServiceImpl.create");
        return new TechnicalSkillDto(technicalSkill, true, false);
    }

    @Override
    public Page<TechnicalSkillDto> getAlltechnicalSkills(TechnicalSkillFilter filter) {
        log.info("Starting EmpDevPlanServiceImpl.getAlltechnicalSkills");
        Page<TechnicalSkill> technicalSkills;
        if (filter.getPageNumber() != null) {
            Pageable pageable = PageRequest.of(Math.max(0, filter.getPageNumber() - 1),
                    Math.max(1, filter.getPageSize()), Sort.by("createdAt").descending());
            technicalSkills = technicalSkillRepository
                    .findAll(TechnicalSkillSpecification.filter(filter), pageable);
        } else {
            technicalSkills = new PageImpl<>(technicalSkillRepository.findAll(
                    TechnicalSkillSpecification.filter(filter), Sort.by("createdAt").descending()));
        }
        log.info("Ending EmpDevPlanServiceImpl.getAlltechnicalSkills");
        return technicalSkills.map(technicalSkill -> new TechnicalSkillDto(technicalSkill,
                filter.getWithCreatedBy(), filter.getWithUpdatedBy()));
    }

    @Override
    public TechnicalSkillDto gettechnicalSkillById(UUID id) {
        log.info("Starting EmpDevPlanServiceImpl.gettechnicalSkillById");
        Optional<TechnicalSkill> technicalSkillOpt = technicalSkillRepository.findById(id);
        if (technicalSkillOpt.isEmpty()) {
            throw ResponseException.technicalSkillNotFound(id);
        }
        TechnicalSkill data = technicalSkillOpt.get();
        log.info("Ending EmpDevPlanServiceImpl.gettechnicalSkillById");
        return convertToDto(data);
    }

    @Override
    public TechnicalSkillDto updateTechnicalSkillById(UUID id,
            TechnicalSkillReq technicalSkillReq) {
        log.info("Starting EmpDevPlanServiceImpl.updateTechnicalSkillById");
        Optional<TechnicalSkill> technicalSkillOpt = technicalSkillRepository.findById(id);
        if (technicalSkillOpt.isEmpty()) {
            throw ResponseException.technicalSkillNotFound(id);
        }
        TechnicalSkill technicalSkill = technicalSkillOpt.get();
        if (technicalSkillReq.getTechnicalSKill() != null) {
            technicalSkill.setName(technicalSkillReq.getTechnicalSKill());
        }
        if (technicalSkillReq.getEnabled() != null) {
            technicalSkill.setEnabled(technicalSkillReq.getEnabled());
        }
        User currentUser = securityUtil.getCurrentUser();
        technicalSkill.setUpdatedBy(currentUser);
        technicalSkill = technicalSkillRepository.save(technicalSkill);
        log.info("Ending EmpDevPlanServiceImpl.updateTechnicalSkillById");
        return new TechnicalSkillDto(technicalSkill, true, true);
    }

    @Override
    public boolean deleteTechnicalSkillById(UUID id) {
        log.info("Starting EmpDevPlanServiceImpl.deleteTechnicalSkillById");
        Optional<TechnicalSkill> technicalSkillOpt = technicalSkillRepository.findById(id);
        if (technicalSkillOpt.isEmpty()) {
            throw ResponseException.technicalSkillNotFound(id);
        }
        technicalSkillRepository.delete(technicalSkillOpt.get());
        log.info("Ending EmpDevPlanServiceImpl.deleteTechnicalSkillById");
        return false;
    }

    private TechnicalSkillDto convertToDto(TechnicalSkill data) {
        return new TechnicalSkillDto(data, true, true);
    }
}
