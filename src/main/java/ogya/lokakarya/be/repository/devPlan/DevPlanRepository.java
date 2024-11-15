package ogya.lokakarya.be.repository.devPlan;

import ogya.lokakarya.be.entity.DevPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface DevPlanRepository extends JpaRepository<DevPlan, UUID> {
}
