package ogya.lokakarya.be.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ogya.lokakarya.be.dto.attitudeskill.AttitudeSkillFilter;
import ogya.lokakarya.be.entity.AttitudeSkill;

public interface AttitudeSkillRepository extends JpaRepository<AttitudeSkill, UUID>,
        FilterRepository<AttitudeSkill, AttitudeSkillFilter> {
}
