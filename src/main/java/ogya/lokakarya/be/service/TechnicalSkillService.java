package ogya.lokakarya.be.service;

import ogya.lokakarya.be.dto.technicalskill.CreateTechnicalSkill;
import ogya.lokakarya.be.dto.technicalskill.TechnicalSkillDto;
import ogya.lokakarya.be.entity.TechnicalSkill;

import java.util.List;
import java.util.UUID;

public interface TechnicalSkillService {
    TechnicalSkill create(CreateTechnicalSkill data);
    List<TechnicalSkillDto> getAlltechnicalSkills();
    TechnicalSkillDto gettechnicalSkillById(UUID id);
    TechnicalSkillDto updateTechnicalSkillById(UUID id, CreateTechnicalSkill createTechnicalSkill);
    boolean deleteTechnicalSkillById(UUID id);
}
