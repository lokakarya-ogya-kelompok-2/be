package ogya.lokakarya.be.service.impl;

import lombok.extern.slf4j.Slf4j;
import ogya.lokakarya.be.config.security.SecurityUtil;
import ogya.lokakarya.be.dto.devplan.DevPlanDto;
import ogya.lokakarya.be.dto.devplan.DevPlanFilter;
import ogya.lokakarya.be.dto.devplan.DevPlanReq;
import ogya.lokakarya.be.entity.DevPlan;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.exception.ResponseException;
import ogya.lokakarya.be.repository.DevPlanRepository;
import ogya.lokakarya.be.service.DevPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class DevPlanServiceImpl implements DevPlanService {
    @Autowired
    private DevPlanRepository devPlanRepository;

    @Autowired
    private SecurityUtil securityUtil;

    @Override
    public DevPlanDto create(DevPlanReq data) {
        log.info("Starting DevPlanServiceImpl.create");
        User currentUser = securityUtil.getCurrentUser();
        DevPlan devPlanEntity = data.toEntity();
        devPlanEntity.setCreatedBy(currentUser);
        devPlanEntity = devPlanRepository.save(devPlanEntity);
        log.info("Ending DevPlanServiceImpl.create");
        return new DevPlanDto(devPlanEntity, true, false);
    }

    @Override
    public List<DevPlanDto> getAllDevPlans(DevPlanFilter filter) {
        log.info("Starting DevPlanServiceImpl.getAllDevPlans");
        List<DevPlan> devPlans = devPlanRepository.findAllByFilter(filter);
        log.info("Ending DevPlanServiceImpl.getAllDevPlans");
        return devPlans.stream().map(devPlan -> new DevPlanDto(devPlan, filter.getWithCreatedBy(),
                filter.getWithUpdatedBy())).toList();
    }

    @Override
    public DevPlanDto getDevPlanById(UUID id) {
        log.info("Starting DevPlanServiceImpl.getDevPlanById");
        Optional<DevPlan> devPlanOpt = devPlanRepository.findById(id);
        if (devPlanOpt.isEmpty()) {
            throw ResponseException.devPlanNotFound(id);
        }
        DevPlan data = devPlanOpt.get();
        log.info("Ending DevPlanServiceImpl.getDevPlanById");
        return convertToDto(data);
    }

    @Override
    public DevPlanDto updateDevPlanById(UUID id, DevPlanReq devPlanReq) {
        log.info("Starting DevPlanServiceImpl.updateDevPlanById");
        Optional<DevPlan> devPlanOpt = devPlanRepository.findById(id);
        if (devPlanOpt.isEmpty()) {
            throw ResponseException.devPlanNotFound(id);
        }
        User currentUser = securityUtil.getCurrentUser();
        DevPlan devPlan = devPlanOpt.get();
        if (devPlanReq.getPlan() != null) {
            devPlan.setPlan(devPlanReq.getPlan());
        }
        if (devPlanReq.getEnabled() != null) {
            devPlan.setEnabled(devPlanReq.getEnabled());
        }
        devPlan.setUpdatedBy(currentUser);
        devPlan = devPlanRepository.save(devPlan);
        log.info("Ending DevPlanServiceImpl.updateDevPlanById");
        return convertToDto(devPlan);
    }

    @Override
    public boolean deleteDevPlanById(UUID id) {
        log.info("Starting DevPlanServiceImpl.deleteDevPlanById");
        Optional<DevPlan> devPlanOpt = devPlanRepository.findById(id);
        if (devPlanOpt.isEmpty()) {
            throw ResponseException.devPlanNotFound(id);
        }
        devPlanRepository.delete(devPlanOpt.get());
        log.info("Ending DevPlanServiceImpl.deleteDevPlanById");
        return ResponseEntity.ok().build().hasBody();
    }

    private DevPlanDto convertToDto(DevPlan data) {
        return new DevPlanDto(data, true, true);
    }
}
