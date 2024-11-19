package ogya.lokakarya.be.service;

import ogya.lokakarya.be.dto.empachievementskill.EmpAchievementSkillDto;
import ogya.lokakarya.be.dto.empachievementskill.EmpAchievementSkillReq;

import java.util.List;
import java.util.UUID;

public interface AchievementSkillService {
    EmpAchievementSkillDto create(EmpAchievementSkillReq data);
    List<EmpAchievementSkillDto> getAllAchievementSkills();
    EmpAchievementSkillDto getAchievementSkillById(UUID id);
    EmpAchievementSkillDto updateAchievementSkillById(UUID id, EmpAchievementSkillReq empAchievementSkillReq);
    boolean deleteAchievementSkillById(UUID id);
}