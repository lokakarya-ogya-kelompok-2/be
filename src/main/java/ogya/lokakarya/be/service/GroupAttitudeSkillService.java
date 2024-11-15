package ogya.lokakarya.be.service;

import ogya.lokakarya.be.dto.groupattitudeskill.CreateGroupAttitudeSkill;
import ogya.lokakarya.be.dto.groupattitudeskill.GroupAttitudeSkillDto;
import ogya.lokakarya.be.entity.GroupAttitudeSkill;

import java.util.List;
import java.util.UUID;

public interface GroupAttitudeSkillService {
    GroupAttitudeSkill create(CreateGroupAttitudeSkill data);
    List<GroupAttitudeSkillDto> getAllGroupAttitudeSkills();
    GroupAttitudeSkillDto getGroupAttitudeSkillById(UUID id);
    GroupAttitudeSkillDto updateGroupAttitudeSkillById(UUID id, CreateGroupAttitudeSkill createGroupAttitudeSkill);
    boolean deleteGroupAttitudeSkillById(UUID id);
}
