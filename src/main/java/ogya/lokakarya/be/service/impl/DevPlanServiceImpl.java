package ogya.lokakarya.be.service.impl;

import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import ogya.lokakarya.be.config.security.SecurityUtil;
import ogya.lokakarya.be.dto.devplan.DevPlanDto;
import ogya.lokakarya.be.dto.devplan.DevPlanFilter;
import ogya.lokakarya.be.dto.devplan.DevPlanReq;
import ogya.lokakarya.be.entity.DevPlan;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.exception.ResponseException;
import ogya.lokakarya.be.repository.DevPlanRepository;
import ogya.lokakarya.be.repository.specification.DevPlanSpecification;
import ogya.lokakarya.be.service.DevPlanService;

@Slf4j
@Service
public class DevPlanServiceImpl implements DevPlanService {
    @Autowired
    private DevPlanRepository devPlanRepository;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private DevPlanSpecification spec;

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
    public Page<DevPlanDto> getAllDevPlans(DevPlanFilter filter) {
        log.info("Starting DevPlanServiceImpl.getAllDevPlans");

        Specification<DevPlan> specification = Specification.where(null);
        if (filter.getNameContains() != null && !filter.getNameContains().isEmpty()) {
            specification = specification.and(spec.nameContains(filter.getNameContains()));
        }
        if (filter.getEnabledOnly().booleanValue()) {
            specification = specification.and(spec.enabledEquals(true));
        }

        Sort sortBy = Sort.by(filter.getSortDirection(), filter.getSortField());

        Page<DevPlan> devPlans;
        if (filter.getPageNumber() != null) {
            Pageable pageable = PageRequest.of(Math.max(0, filter.getPageNumber() - 1),
                    Math.max(1, filter.getPageSize()), sortBy);
            devPlans = devPlanRepository.findAll(specification, pageable);
        } else {
            devPlans = new PageImpl<>(devPlanRepository.findAll(specification, sortBy));
        }

        log.info("Ending DevPlanServiceImpl.getAllDevPlans");
        return devPlans.map(devPlan -> new DevPlanDto(devPlan, filter.getWithCreatedBy(),
                filter.getWithUpdatedBy()));
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
            devPlan.setName(devPlanReq.getPlan());
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
