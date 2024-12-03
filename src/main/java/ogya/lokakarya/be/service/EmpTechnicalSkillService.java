package ogya.lokakarya.be.service;

import java.util.List;
import java.util.UUID;
import ogya.lokakarya.be.dto.emptechnicalskill.EmpTechnicalSkillDto;
import ogya.lokakarya.be.dto.emptechnicalskill.EmpTechnicalSkillReq;

public interface EmpTechnicalSkillService {
    List<EmpTechnicalSkillDto> createBulk(List<EmpTechnicalSkillReq> data);

    EmpTechnicalSkillDto create(EmpTechnicalSkillReq data);

    List<EmpTechnicalSkillDto> getAllEmpTechnicalSkills();

    EmpTechnicalSkillDto getEmpTechnicalSkillById(UUID id);

    EmpTechnicalSkillDto updateEmpTechnicalSkillById(UUID id,
            EmpTechnicalSkillReq empTechnicalSkillReq);

    boolean deleteEmpTechnicalSkillById(UUID id);
}
