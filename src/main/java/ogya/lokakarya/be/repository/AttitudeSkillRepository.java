package ogya.lokakarya.be.repository;

import ogya.lokakarya.be.entity.AttitudeSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AttitudeSkillRepository extends JpaRepository<AttitudeSkill, UUID> {
}