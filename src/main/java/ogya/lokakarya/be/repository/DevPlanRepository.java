package ogya.lokakarya.be.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ogya.lokakarya.be.entity.DevPlan;

public interface DevPlanRepository
                extends JpaRepository<DevPlan, UUID>, JpaSpecificationExecutor<DevPlan> {
}
