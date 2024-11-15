package ogya.lokakarya.be.service;

import ogya.lokakarya.be.dto.devplan.CreateDevPlan;
import ogya.lokakarya.be.dto.devplan.DevPlanDto;
import ogya.lokakarya.be.entity.DevPlan;

import java.util.List;
import java.util.UUID;

public interface DevPlanService {
    DevPlan create(CreateDevPlan data);
    List<DevPlanDto> getAllDevPlans();
    DevPlanDto getDevPlanById(UUID id);
    DevPlanDto updateDevPlanById(UUID id, CreateDevPlan createDevPlan);
    boolean deleteDevPlanById(UUID id);
}
