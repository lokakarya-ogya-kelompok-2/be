package ogya.lokakarya.be.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import ogya.lokakarya.be.config.security.SecurityUtil;
import ogya.lokakarya.be.dto.empdevplan.EmpDevPlanDto;
import ogya.lokakarya.be.dto.empdevplan.EmpDevPlanFilter;
import ogya.lokakarya.be.dto.empdevplan.EmpDevPlanReq;
import ogya.lokakarya.be.entity.DevPlan;
import ogya.lokakarya.be.entity.EmpDevPlan;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.exception.ResponseException;
import ogya.lokakarya.be.repository.DevPlanRepository;
import ogya.lokakarya.be.repository.EmpDevPlanRepository;
import ogya.lokakarya.be.service.EmpDevPlanService;

@Slf4j
@Service
public class EmpDevPlanServiceImpl implements EmpDevPlanService {
    @Autowired
    EmpDevPlanRepository empDevPlanRepository;
    @Autowired
    private DevPlanRepository devPlanRepository;
    @Autowired
    private SecurityUtil securityUtil;

    @Override
    public List<EmpDevPlanDto> createBulkEmpDevPlan(List<EmpDevPlanReq> data) {
        log.info("Starting EmpDevPlanServiceImpl.createBulkEmpDevPlan");
        User currentUser = securityUtil.getCurrentUser();
        List<EmpDevPlan> empDevPlanEntities = new ArrayList<>(data.size());
        Map<UUID, DevPlan> devPlanIds = new HashMap<>();

        for (EmpDevPlanReq d : data) {
            EmpDevPlan empDevPlanEntity = d.toEntity();
            empDevPlanEntity.setCreatedBy(currentUser);
            empDevPlanEntity.setUser(currentUser);

            // Cek apakah DevPlan dengan ID tertentu sudah di-cache
            if (!devPlanIds.containsKey(d.getDevPlanId())) {
                Optional<DevPlan> devPlanOpt = devPlanRepository.findById(d.getDevPlanId());
                if (devPlanOpt.isEmpty()) {
                    throw ResponseException.devPlanNotFound(d.getDevPlanId());
                }
                DevPlan devPlan = devPlanOpt.get();
                devPlanIds.put(d.getDevPlanId(), devPlan);
            }

            empDevPlanEntity.setDevPlan(devPlanIds.get(d.getDevPlanId()));
            empDevPlanEntities.add(empDevPlanEntity);
        }
        empDevPlanEntities = empDevPlanRepository.saveAll(empDevPlanEntities);
        log.info("Ending EmpDevPlanServiceImpl.createBulkEmpDevPlan");
        return empDevPlanEntities.stream()
                .map(empDevPlan -> new EmpDevPlanDto(empDevPlan, true, false)).toList();
    }

    @Override
    public EmpDevPlanDto create(EmpDevPlanReq data) {
        log.info("Starting EmpDevPlanServiceImpl.create");
        User currentUser = securityUtil.getCurrentUser();
        Optional<DevPlan> findDevPlan = devPlanRepository.findById(data.getDevPlanId());
        if (findDevPlan.isEmpty()) {
            throw ResponseException.devPlanNotFound(data.getDevPlanId());
        }
        EmpDevPlan dataEntity = data.toEntity();
        dataEntity.setUser(currentUser);
        dataEntity.setDevPlan(findDevPlan.get());
        EmpDevPlan createdData = empDevPlanRepository.save(dataEntity);
        log.info("Ending EmpDevPlanServiceImpl.create");
        return new EmpDevPlanDto(createdData, true, false);
    }

    @Override
    public List<EmpDevPlanDto> getAllEmpDevPlans(EmpDevPlanFilter filter) {
        log.info("Starting EmpDevPlanServiceImpl.getAllEmpDevPlans");
        List<EmpDevPlan> empDevPlanEntities = empDevPlanRepository.findAllByFilter(filter);
        log.info("Ending EmpDevPlanServiceImpl.getAllEmpDevPlans");
        return empDevPlanEntities.stream().map(empDevPlan -> new EmpDevPlanDto(empDevPlan,
                filter.getWithCreatedBy(), filter.getWithUpdatedBy())).toList();
    }

    @Override
    public EmpDevPlanDto getEmpDevPlanById(UUID id) {
        log.info("Starting EmpDevPlanServiceImpl.getEmpDevPlanById");
        Optional<EmpDevPlan> empDevPlanOpt;
        empDevPlanOpt = empDevPlanRepository.findById(id);
        if (empDevPlanOpt.isEmpty()) {
            throw ResponseException.empDevPlanNotFound(id);
        }
        EmpDevPlan data = empDevPlanOpt.get();
        log.info("Ending EmpDevPlanServiceImpl.getEmpDevPlanById");
        return convertToDto(data);
    }

    @Override
    public EmpDevPlanDto updateEmpDevPlanById(UUID id, EmpDevPlanReq empDevPlanReq) {
        log.info("Starting EmpDevPlanServiceImpl.updateEmpDevPlanById");
        Optional<EmpDevPlan> empDevPlanOpt = empDevPlanRepository.findById(id);
        if (empDevPlanOpt.isEmpty()) {
            throw ResponseException.empDevPlanNotFound(id);
        }
        EmpDevPlan empDevPlan = empDevPlanOpt.get();
        Integer currentYear = LocalDate.now().getYear();
        if (!currentYear.equals(empDevPlan.getAssessmentYear())) {
            throw ResponseException.unauthorized();
        }
        User currentUser = securityUtil.getCurrentUser();
        if (!currentUser.equals(empDevPlan.getUser())) {
            throw ResponseException.unauthorized();
        }
        if (empDevPlanReq.getAssessmentYear() != null) {
            empDevPlan.setAssessmentYear(empDevPlanReq.getAssessmentYear());
        }
        User user = securityUtil.getCurrentUser();
        empDevPlan.setUser(user);
        empDevPlan.setUpdatedBy(user);
        if (empDevPlanReq.getDevPlanId() != null) {
            Optional<DevPlan> devPlan = devPlanRepository.findById(empDevPlanReq.getDevPlanId());
            if (devPlan.isEmpty()) {
                throw ResponseException.devPlanNotFound(empDevPlanReq.getDevPlanId());
            }
            empDevPlan.setDevPlan(devPlan.get());
        }
        empDevPlan = empDevPlanRepository.save(empDevPlan);
        log.info("Ending EmpDevPlanServiceImpl.updateEmpDevPlanById");
        return convertToDto(empDevPlan);
    }

    @Override
    public boolean deleteEmpDevPlanById(UUID id) {
        log.info("Starting EmpDevPlanServiceImpl.deleteEmpDevPlanById");
        Optional<EmpDevPlan> empDevPlanOpt = empDevPlanRepository.findById(id);
        if (empDevPlanOpt.isEmpty()) {
            throw ResponseException.empDevPlanNotFound(id);
        }
        User currentUser = securityUtil.getCurrentUser();
        EmpDevPlan empDevPlan = empDevPlanOpt.get();
        Integer currentYear = LocalDate.now().getYear();
        if (!currentYear.equals(empDevPlan.getAssessmentYear())) {
            throw ResponseException.unauthorized();
        }
        if (!currentUser.equals(empDevPlan.getUser())) {
            throw ResponseException.unauthorized();
        }
        empDevPlanRepository.delete(empDevPlan);
        log.info("Ending EmpDevPlanServiceImpl.deleteEmpDevPlanById");
        return true;
    }

    public EmpDevPlanDto convertToDto(EmpDevPlan data) {
        return new EmpDevPlanDto(data, true, true);
    }
}
