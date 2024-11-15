package ogya.lokakarya.be.service;

import ogya.lokakarya.be.dto.devPlan.CreateDevPlan;
import ogya.lokakarya.be.dto.devPlan.DevPlanDto;
import ogya.lokakarya.be.dto.division.CreateDivision;
import ogya.lokakarya.be.dto.division.DivisionDto;
import ogya.lokakarya.be.entity.DevPlan;
import ogya.lokakarya.be.entity.Division;
import java.util.List;
import java.util.UUID;

public interface DevPlanService {
    DevPlan create(CreateDevPlan data);
    List<DevPlanDto> getAllDevPlans();
    DevPlanDto getDevPlanById(UUID id);
    DevPlanDto updateDevPlanById(UUID id, CreateDevPlan createDevPlan);
    boolean deleteDevPlanById(UUID id);
}
