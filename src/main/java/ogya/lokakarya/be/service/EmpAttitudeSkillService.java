package ogya.lokakarya.be.service;

import java.util.List;
import java.util.UUID;
import ogya.lokakarya.be.dto.empattitudeskill.EmpAttitudeSkillDto;
import ogya.lokakarya.be.dto.empattitudeskill.EmpAttitudeSkillFilter;
import ogya.lokakarya.be.dto.empattitudeskill.EmpAttitudeSkillReq;

public interface EmpAttitudeSkillService {
    List<EmpAttitudeSkillDto> createBulkEmpAttitudeSkill(List<EmpAttitudeSkillReq> data);

    EmpAttitudeSkillDto create(EmpAttitudeSkillReq data);

    List<EmpAttitudeSkillDto> getAllEmpAttitudeSkills(EmpAttitudeSkillFilter filter);

    EmpAttitudeSkillDto getEmpAttitudeSkillById(UUID id);

    EmpAttitudeSkillDto updateEmpAttitudeSkillById(UUID id,
            EmpAttitudeSkillReq empAttitudeSkillReq);

    boolean deleteEmpAttitudeSkillById(UUID id);
}
