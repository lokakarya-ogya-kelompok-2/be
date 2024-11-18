package ogya.lokakarya.be.service;

import ogya.lokakarya.be.dto.division.DivisionDto;
import ogya.lokakarya.be.dto.division.DivisionReq;
import ogya.lokakarya.be.dto.emptechnicalskill.EmpTechnicalSkillDto;
import ogya.lokakarya.be.dto.emptechnicalskill.EmpTechnicalSkillReq;
import ogya.lokakarya.be.entity.Division;
import ogya.lokakarya.be.entity.EmpTechnicalSkill;

import java.util.List;
import java.util.UUID;

public interface EmpTechnicalSkillService {
    EmpTechnicalSkill create(EmpTechnicalSkillReq data);
    List<EmpTechnicalSkillDto> getAllEmpTechnicalSkills();
    EmpTechnicalSkillDto getEmpTechnicalSkillById(UUID id);
    EmpTechnicalSkillDto updateEmpTechnicalSkillById(UUID id, EmpTechnicalSkillReq empTechnicalSkillReq);
    boolean deleteEmpTechnicalSkillById(UUID id);
}
