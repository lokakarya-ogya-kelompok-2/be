package ogya.lokakarya.be.service;

import ogya.lokakarya.be.dto.groupattitudeskill.GroupAttitudeSkillReq;
import ogya.lokakarya.be.dto.groupattitudeskill.GroupAttitudeSkillDto;
import ogya.lokakarya.be.entity.GroupAttitudeSkill;

import java.util.List;
import java.util.UUID;

public interface GroupAttitudeSkillService {
    GroupAttitudeSkill create(GroupAttitudeSkillReq data);
    List<GroupAttitudeSkillDto> getAllGroupAttitudeSkills();
    GroupAttitudeSkillDto getGroupAttitudeSkillById(UUID id);
    GroupAttitudeSkillDto updateGroupAttitudeSkillById(UUID id, GroupAttitudeSkillReq groupAttitudeSkillReq);
    boolean deleteGroupAttitudeSkillById(UUID id);
}
