package ogya.lokakarya.be.service;

import java.util.List;
import java.util.UUID;
import ogya.lokakarya.be.dto.technicalskill.TechnicalSkillDto;
import ogya.lokakarya.be.dto.technicalskill.TechnicalSkillFilter;
import ogya.lokakarya.be.dto.technicalskill.TechnicalSkillReq;

public interface TechnicalSkillService {
    TechnicalSkillDto create(TechnicalSkillReq data);

    List<TechnicalSkillDto> getAlltechnicalSkills(TechnicalSkillFilter filter);

    TechnicalSkillDto gettechnicalSkillById(UUID id);

    TechnicalSkillDto updateTechnicalSkillById(UUID id, TechnicalSkillReq technicalSkillReq);

    boolean deleteTechnicalSkillById(UUID id);
}
