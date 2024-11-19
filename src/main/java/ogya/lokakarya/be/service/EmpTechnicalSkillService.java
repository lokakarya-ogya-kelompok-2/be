package ogya.lokakarya.be.service;

import ogya.lokakarya.be.dto.emptechnicalskill.EmpTechnicalSkillDto;
import ogya.lokakarya.be.dto.emptechnicalskill.EmpTechnicalSkillReq;

import java.util.List;
import java.util.UUID;

public interface EmpTechnicalSkillService {
    EmpTechnicalSkillDto create(EmpTechnicalSkillReq data);
    List<EmpTechnicalSkillDto> getAllEmpTechnicalSkills();
    EmpTechnicalSkillDto getEmpTechnicalSkillById(UUID id);
    EmpTechnicalSkillDto updateEmpTechnicalSkillById(UUID id, EmpTechnicalSkillReq empTechnicalSkillReq);
    boolean deleteEmpTechnicalSkillById(UUID id);
}
