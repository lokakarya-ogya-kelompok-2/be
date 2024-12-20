package ogya.lokakarya.be.service;

import java.util.UUID;
import org.springframework.data.domain.Page;
import ogya.lokakarya.be.dto.groupattitudeskill.GroupAttitudeSkillDto;
import ogya.lokakarya.be.dto.groupattitudeskill.GroupAttitudeSkillFilter;
import ogya.lokakarya.be.dto.groupattitudeskill.GroupAttitudeSkillReq;

public interface GroupAttitudeSkillService {
    GroupAttitudeSkillDto create(GroupAttitudeSkillReq data);

    Page<GroupAttitudeSkillDto> getAllGroupAttitudeSkills(GroupAttitudeSkillFilter filter);

    GroupAttitudeSkillDto getGroupAttitudeSkillById(UUID id);

    GroupAttitudeSkillDto updateGroupAttitudeSkillById(UUID id,
            GroupAttitudeSkillReq groupAttitudeSkillReq);

    boolean deleteGroupAttitudeSkillById(UUID id);
}
