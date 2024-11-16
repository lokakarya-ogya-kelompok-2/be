package ogya.lokakarya.be.service;

import ogya.lokakarya.be.dto.devplan.DevPlanReq;
import ogya.lokakarya.be.dto.devplan.DevPlanDto;
import ogya.lokakarya.be.entity.DevPlan;

import java.util.List;
import java.util.UUID;

public interface DevPlanService {
    DevPlan create(DevPlanReq data);
    List<DevPlanDto> getAllDevPlans();
    DevPlanDto getDevPlanById(UUID id);
    DevPlanDto updateDevPlanById(UUID id, DevPlanReq devPlanReq);
    boolean deleteDevPlanById(UUID id);
}
