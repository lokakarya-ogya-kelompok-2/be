package ogya.lokakarya.be.service;

import ogya.lokakarya.be.dto.attitudeSkill.AttitudeSkillDto;
import ogya.lokakarya.be.dto.attitudeSkill.CreateAttitudeSkill;
import ogya.lokakarya.be.dto.division.CreateDivision;
import ogya.lokakarya.be.dto.division.DivisionDto;
import ogya.lokakarya.be.entity.AttitudeSkill;
import ogya.lokakarya.be.entity.Division;

import java.util.List;
import java.util.UUID;

public interface AttitudeSkillService {
    AttitudeSkill create(CreateAttitudeSkill data);
    List<AttitudeSkillDto> getAllAttitudeSkills();
    AttitudeSkillDto getAttitudeSkillById(UUID id);
    AttitudeSkillDto updateAttitudeSkillById(UUID id, CreateAttitudeSkill attitudeSkill);
    boolean deleteAttitudeSkillById(UUID id);
}
