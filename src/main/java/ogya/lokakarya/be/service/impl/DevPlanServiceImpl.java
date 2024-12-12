package ogya.lokakarya.be.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ogya.lokakarya.be.config.security.SecurityUtil;
import ogya.lokakarya.be.dto.devplan.DevPlanDto;
import ogya.lokakarya.be.dto.devplan.DevPlanReq;
import ogya.lokakarya.be.entity.DevPlan;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.exception.ResponseException;
import ogya.lokakarya.be.repository.DevPlanRepository;
import ogya.lokakarya.be.service.DevPlanService;

@Service
public class DevPlanServiceImpl implements DevPlanService {
    @Autowired
    private DevPlanRepository devPlanRepository;

    @Autowired
    private SecurityUtil securityUtil;

    @Override
    public DevPlanDto create(DevPlanReq data) {
        User currentUser = securityUtil.getCurrentUser();
        DevPlan devPlanEntity = data.toEntity();
        devPlanEntity.setCreatedBy(currentUser);
        devPlanEntity = devPlanRepository.save(devPlanEntity);
        return new DevPlanDto(devPlanEntity);
    }

    @Override
    public List<DevPlanDto> getAllDevPlans() {
        List<DevPlanDto> listResult = new ArrayList<>();
        List<DevPlan> devPlanList = devPlanRepository.findAll();
        for (DevPlan devPlan : devPlanList) {
            DevPlanDto result = convertToDto(devPlan);
            listResult.add(result);
        }
        return listResult;
    }

    @Override
    public DevPlanDto getDevPlanById(UUID id) {
        Optional<DevPlan> devPlanOpt = devPlanRepository.findById(id);
        if (devPlanOpt.isEmpty()) {
            throw ResponseException.devPlanNotFound(id);
        }
        DevPlan data = devPlanOpt.get();
        return convertToDto(data);
    }

    @Override
    public DevPlanDto updateDevPlanById(UUID id, DevPlanReq devPlanReq) {
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
        return convertToDto(devPlan);
    }

    @Override
    public boolean deleteDevPlanById(UUID id) {
        Optional<DevPlan> devPlanOpt = devPlanRepository.findById(id);
        if (devPlanOpt.isEmpty()) {
            throw ResponseException.devPlanNotFound(id);
        }
        devPlanRepository.delete(devPlanOpt.get());
        return ResponseEntity.ok().build().hasBody();
    }

    private DevPlanDto convertToDto(DevPlan data) {
        return new DevPlanDto(data);
    }
}
