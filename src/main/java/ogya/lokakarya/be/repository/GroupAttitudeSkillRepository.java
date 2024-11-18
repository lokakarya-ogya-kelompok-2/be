package ogya.lokakarya.be.repository;

import ogya.lokakarya.be.entity.GroupAttitudeSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GroupAttitudeSkillRepository extends JpaRepository<GroupAttitudeSkill, UUID> {
}
