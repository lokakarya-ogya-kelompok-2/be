package ogya.lokakarya.be.service;

import java.util.UUID;
import org.springframework.data.domain.Page;
import ogya.lokakarya.be.dto.attitudeskill.AttitudeSkillDto;
import ogya.lokakarya.be.dto.attitudeskill.AttitudeSkillFilter;
import ogya.lokakarya.be.dto.attitudeskill.AttitudeSkillReq;

public interface AttitudeSkillService {
    AttitudeSkillDto create(AttitudeSkillReq data);

    Page<AttitudeSkillDto> getAllAttitudeSkills(AttitudeSkillFilter filter);

    AttitudeSkillDto getAttitudeSkillById(UUID id);

    AttitudeSkillDto updateAttitudeSkillById(UUID id, AttitudeSkillReq attitudeSkill);

    boolean deleteAttitudeSkillById(UUID id);
}
