package ogya.lokakarya.be.service;

import java.util.UUID;
import org.springframework.data.domain.Page;
import ogya.lokakarya.be.dto.technicalskill.TechnicalSkillDto;
import ogya.lokakarya.be.dto.technicalskill.TechnicalSkillFilter;
import ogya.lokakarya.be.dto.technicalskill.TechnicalSkillReq;

public interface TechnicalSkillService {
    TechnicalSkillDto create(TechnicalSkillReq data);

    Page<TechnicalSkillDto> getAlltechnicalSkills(TechnicalSkillFilter filter);

    TechnicalSkillDto gettechnicalSkillById(UUID id);

    TechnicalSkillDto updateTechnicalSkillById(UUID id, TechnicalSkillReq technicalSkillReq);

    boolean deleteTechnicalSkillById(UUID id);
}
