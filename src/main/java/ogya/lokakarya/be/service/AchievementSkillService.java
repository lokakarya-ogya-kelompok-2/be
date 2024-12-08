package ogya.lokakarya.be.service;

import java.util.List;
import java.util.UUID;
import ogya.lokakarya.be.dto.empachievementskill.EmpAchievementSkillDto;
import ogya.lokakarya.be.dto.empachievementskill.EmpAchievementSkillFilter;
import ogya.lokakarya.be.dto.empachievementskill.EmpAchievementSkillReq;

public interface AchievementSkillService {
    EmpAchievementSkillDto create(EmpAchievementSkillReq data);

    List<EmpAchievementSkillDto> getAllAchievementSkills(EmpAchievementSkillFilter filter);

    EmpAchievementSkillDto getAchievementSkillById(UUID id);

    EmpAchievementSkillDto updateAchievementSkillById(UUID id,
            EmpAchievementSkillReq empAchievementSkillReq);

    boolean deleteAchievementSkillById(UUID id);
}
