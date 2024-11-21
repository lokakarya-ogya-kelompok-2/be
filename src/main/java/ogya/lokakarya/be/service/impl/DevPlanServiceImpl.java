package ogya.lokakarya.be.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ogya.lokakarya.be.dto.devplan.DevPlanDto;
import ogya.lokakarya.be.dto.devplan.DevPlanReq;
import ogya.lokakarya.be.entity.DevPlan;
import ogya.lokakarya.be.repository.DevPlanRepository;
import ogya.lokakarya.be.service.DevPlanService;

@Service
public class DevPlanServiceImpl implements DevPlanService {
    @Autowired
    private DevPlanRepository devPlanRepository;

    @Override
    public DevPlanDto create(DevPlanReq data) {
        DevPlan devPlanEntity = data.toEntity();
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
        Optional<DevPlan> listData;
        try {
            listData = devPlanRepository.findById(id);
            System.out.println(listData);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        DevPlan data = listData.get();
        return convertToDto(data);
    }

    @Override
    public DevPlanDto updateDevPlanById(UUID id, DevPlanReq devPlanReq) {
        Optional<DevPlan> listData = devPlanRepository.findById(id);
        if (listData.isPresent()) {
            DevPlan devPlan = listData.get();
            if (!devPlanReq.getPlan().isBlank()) {
                devPlan.setPlan(devPlanReq.getPlan());
                // devPlan.setEnabled(devPlanReq.geE;
            }
            DevPlanDto devPlanDto = convertToDto(devPlan);
            devPlanRepository.save(devPlan);
            return devPlanDto;
        }
        return null;
    }

    @Override
    public boolean deleteDevPlanById(UUID id) {
        Optional<DevPlan> listData = devPlanRepository.findById(id);
        if (listData.isPresent()) {
            devPlanRepository.delete(listData.get());
            return ResponseEntity.ok().build().hasBody();
        } else {
            return ResponseEntity.notFound().build().hasBody();
        }
    }

    private DevPlanDto convertToDto(DevPlan data) {
        DevPlanDto result = new DevPlanDto(data);
        return result;
    }
}
