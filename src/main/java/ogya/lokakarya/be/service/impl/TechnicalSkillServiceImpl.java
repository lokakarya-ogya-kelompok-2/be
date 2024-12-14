package ogya.lokakarya.be.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ogya.lokakarya.be.config.security.SecurityUtil;
import ogya.lokakarya.be.dto.technicalskill.TechnicalSkillDto;
import ogya.lokakarya.be.dto.technicalskill.TechnicalSkillReq;
import ogya.lokakarya.be.entity.TechnicalSkill;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.exception.ResponseException;
import ogya.lokakarya.be.repository.TechnicalSkillRepository;
import ogya.lokakarya.be.service.TechnicalSkillService;

@Service
public class TechnicalSkillServiceImpl implements TechnicalSkillService {
    @Autowired
    private TechnicalSkillRepository technicalSkillRepository;

    @Autowired
    private SecurityUtil securityUtil;

    @Override
    public TechnicalSkillDto create(TechnicalSkillReq data) {
        TechnicalSkill technicalSkill = data.toEntity();
        User currentUser = securityUtil.getCurrentUser();
        technicalSkill.setCreatedBy(currentUser);
        technicalSkill = technicalSkillRepository.save(technicalSkill);
        return new TechnicalSkillDto(technicalSkill, true, false);
    }

    @Override
    public List<TechnicalSkillDto> getAlltechnicalSkills() {
        List<TechnicalSkillDto> listResult = new ArrayList<>();
        List<TechnicalSkill> technicalSkillList = technicalSkillRepository.findAll();
        for (TechnicalSkill technicalSkill : technicalSkillList) {
            TechnicalSkillDto result = convertToDto(technicalSkill);
            listResult.add(result);
        }
        return listResult;
    }

    @Override
    public TechnicalSkillDto gettechnicalSkillById(UUID id) {
        Optional<TechnicalSkill> technicalSkillOpt = technicalSkillRepository.findById(id);
        if (technicalSkillOpt.isEmpty()) {
            throw ResponseException.technicalSkillNotFound(id);
        }
        TechnicalSkill data = technicalSkillOpt.get();
        return convertToDto(data);
    }

    @Override
    public TechnicalSkillDto updateTechnicalSkillById(UUID id,
            TechnicalSkillReq technicalSkillReq) {
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
        return new TechnicalSkillDto(technicalSkill, true, true);
    }

    @Override
    public boolean deleteTechnicalSkillById(UUID id) {
        Optional<TechnicalSkill> technicalSkillOpt = technicalSkillRepository.findById(id);
        if (technicalSkillOpt.isEmpty()) {
            throw ResponseException.technicalSkillNotFound(id);
        }
        technicalSkillRepository.delete(technicalSkillOpt.get());
        return false;
    }

    private TechnicalSkillDto convertToDto(TechnicalSkill data) {
        return new TechnicalSkillDto(data, true, true);
    }
}
