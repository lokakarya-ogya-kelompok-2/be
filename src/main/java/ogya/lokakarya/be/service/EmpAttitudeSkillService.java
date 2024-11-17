package ogya.lokakarya.be.service;

import ogya.lokakarya.be.dto.empattitudeskill.EmpAttitudeSkillDto;
import ogya.lokakarya.be.dto.empattitudeskill.EmpAttitudeSkillReq;
import ogya.lokakarya.be.entity.EmpAttitudeSkill;

import java.util.List;
import java.util.UUID;

public interface EmpAttitudeSkillService {
    EmpAttitudeSkill create(EmpAttitudeSkillReq data);
    List<EmpAttitudeSkillDto> getAllEmpAttitudeSkills();
    EmpAttitudeSkillDto getEmpAttitudeSkillById(UUID id);
    EmpAttitudeSkillDto updateEmpAttitudeSkillById(UUID id, EmpAttitudeSkillReq empAttitudeSkillReq);
    boolean deleteEmpAttitudeSkillById(UUID id);
}
