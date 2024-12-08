package ogya.lokakarya.be.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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

        // Simpan semua entitas ke database
        empDevPlanEntities = empDevPlanRepository.saveAll(empDevPlanEntities);

        // Konversi entitas ke DTO untuk response
        return empDevPlanEntities.stream()
                .map(empDevPlan -> new EmpDevPlanDto(empDevPlan, true, true)).toList();
    }

    @Override
    public EmpDevPlanDto create(EmpDevPlanReq data) {
        User currentUser = securityUtil.getCurrentUser();

        Optional<DevPlan> findDevPlan = devPlanRepository.findById(data.getDevPlanId());
        if (findDevPlan.isEmpty()) {
            throw ResponseException.devPlanNotFound(data.getDevPlanId());
        }
        EmpDevPlan dataEntity = data.toEntity();
        dataEntity.setUser(currentUser);
        dataEntity.setDevPlan(findDevPlan.get());
        EmpDevPlan createdData = empDevPlanRepository.save(dataEntity);
        return new EmpDevPlanDto(createdData, true, true);
    }

    @Override
    public List<EmpDevPlanDto> getAllEmpDevPlans(EmpDevPlanFilter filter) {
        List<EmpDevPlan> empDevPlanEntities = empDevPlanRepository.findAllByFilter(filter);

        return empDevPlanEntities.stream()
                .map(empDevPlan -> new EmpDevPlanDto(empDevPlan, true, true)).toList();
    }

    @Override
    public EmpDevPlanDto getEmpDevPlanById(UUID id) {
        Optional<EmpDevPlan> listData;
        try {
            listData = empDevPlanRepository.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        EmpDevPlan data = listData.get();
        return convertToDto(data);
    }

    @Override
    public EmpDevPlanDto updateEmpDevPlanById(UUID id, EmpDevPlanReq empDevPlanReq) {
        Optional<EmpDevPlan> listData = empDevPlanRepository.findById(id);
        if (listData.isPresent()) {
            EmpDevPlan empDevPlan = listData.get();
            if (empDevPlanReq.getAssessmentYear() != null) {
                empDevPlan.setAssessmentYear(empDevPlanReq.getAssessmentYear());
            }
            User user = securityUtil.getCurrentUser();
            empDevPlan.setUser(user);
            empDevPlan.setCreatedBy(user);
            if (empDevPlanReq.getDevPlanId() != null) {
                Optional<DevPlan> devPlan =
                        devPlanRepository.findById(empDevPlanReq.getDevPlanId());
                if (devPlan.isEmpty()) {
                    throw ResponseException.devPlanNotFound(empDevPlanReq.getDevPlanId());
                }
                empDevPlan.setDevPlan(devPlan.get());
            }
            EmpDevPlanDto empDevPlanDto = convertToDto(empDevPlan);
            empDevPlanRepository.save(empDevPlan);
            return empDevPlanDto;
        }
        return null;
    }

    @Override
    public boolean deleteEmpDevPlanById(UUID id) {
        Optional<EmpDevPlan> listData = empDevPlanRepository.findById(id);
        if (listData.isPresent()) {
            empDevPlanRepository.delete(listData.get());
            return ResponseEntity.ok().build().hasBody();
        } else {
            return ResponseEntity.notFound().build().hasBody();
        }
    }

    public EmpDevPlanDto convertToDto(EmpDevPlan data) {
        return new EmpDevPlanDto(data, true, true);
    }
}
