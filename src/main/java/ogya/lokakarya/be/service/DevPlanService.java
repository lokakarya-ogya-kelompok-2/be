package ogya.lokakarya.be.service;

import java.util.List;
import java.util.UUID;
import ogya.lokakarya.be.dto.devplan.DevPlanDto;
import ogya.lokakarya.be.dto.devplan.DevPlanFilter;
import ogya.lokakarya.be.dto.devplan.DevPlanReq;

public interface DevPlanService {
    DevPlanDto create(DevPlanReq data);

    List<DevPlanDto> getAllDevPlans(DevPlanFilter filter);

    DevPlanDto getDevPlanById(UUID id);

    DevPlanDto updateDevPlanById(UUID id, DevPlanReq devPlanReq);

    boolean deleteDevPlanById(UUID id);
}
