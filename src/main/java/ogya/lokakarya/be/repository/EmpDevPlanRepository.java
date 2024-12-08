package ogya.lokakarya.be.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ogya.lokakarya.be.dto.empdevplan.EmpDevPlanFilter;
import ogya.lokakarya.be.entity.EmpDevPlan;

public interface EmpDevPlanRepository extends JpaRepository<EmpDevPlan, UUID>,
                FilterRepository<EmpDevPlan, EmpDevPlanFilter> {
}
