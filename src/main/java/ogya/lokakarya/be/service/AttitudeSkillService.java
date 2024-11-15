package ogya.lokakarya.be.service;

import ogya.lokakarya.be.dto.attitudeskill.AttitudeSkillDto;
import ogya.lokakarya.be.dto.attitudeskill.CreateAttitudeSkill;
import ogya.lokakarya.be.entity.AttitudeSkill;

import java.util.List;
import java.util.UUID;

public interface AttitudeSkillService {
    AttitudeSkill create(CreateAttitudeSkill data);
    List<AttitudeSkillDto> getAllAttitudeSkills();
    AttitudeSkillDto getAttitudeSkillById(UUID id);
    AttitudeSkillDto updateAttitudeSkillById(UUID id, CreateAttitudeSkill attitudeSkill);
    boolean deleteAttitudeSkillById(UUID id);
}
