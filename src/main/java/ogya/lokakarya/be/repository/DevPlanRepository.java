package ogya.lokakarya.be.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ogya.lokakarya.be.dto.devplan.DevPlanFilter;
import ogya.lokakarya.be.entity.DevPlan;

public interface DevPlanRepository
        extends JpaRepository<DevPlan, UUID>, FilterRepository<DevPlan, DevPlanFilter> {
}
