package ogya.lokakarya.be.repository;

import ogya.lokakarya.be.entity.EmpDevPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface EmpDevPlanRepository extends JpaRepository<EmpDevPlan, UUID> {
}
