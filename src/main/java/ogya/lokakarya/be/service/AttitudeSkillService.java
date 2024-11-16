package ogya.lokakarya.be.service;

import ogya.lokakarya.be.dto.attitudeskill.AttitudeSkillDto;
import ogya.lokakarya.be.dto.attitudeskill.AttitudeSkillReq;
import ogya.lokakarya.be.entity.AttitudeSkill;

import java.util.List;
import java.util.UUID;

public interface AttitudeSkillService {
    AttitudeSkill create(AttitudeSkillReq data);
    List<AttitudeSkillDto> getAllAttitudeSkills();
    AttitudeSkillDto getAttitudeSkillById(UUID id);
    AttitudeSkillDto updateAttitudeSkillById(UUID id, AttitudeSkillReq attitudeSkill);
    boolean deleteAttitudeSkillById(UUID id);
}
