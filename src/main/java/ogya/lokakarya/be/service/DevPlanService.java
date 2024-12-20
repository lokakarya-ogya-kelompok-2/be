package ogya.lokakarya.be.service;

import java.util.UUID;
import org.springframework.data.domain.Page;
import ogya.lokakarya.be.dto.devplan.DevPlanDto;
import ogya.lokakarya.be.dto.devplan.DevPlanFilter;
import ogya.lokakarya.be.dto.devplan.DevPlanReq;

public interface DevPlanService {
    DevPlanDto create(DevPlanReq data);

    Page<DevPlanDto> getAllDevPlans(DevPlanFilter filter);

    DevPlanDto getDevPlanById(UUID id);

    DevPlanDto updateDevPlanById(UUID id, DevPlanReq devPlanReq);

    boolean deleteDevPlanById(UUID id);
}
