package ogya.lokakarya.be.service.impl;

import ogya.lokakarya.be.dto.devPlan.CreateDevPlan;
import ogya.lokakarya.be.dto.devPlan.DevPlanDto;
import ogya.lokakarya.be.dto.division.DivisionDto;
import ogya.lokakarya.be.entity.DevPlan;
import ogya.lokakarya.be.entity.Division;
import ogya.lokakarya.be.repository.devPlan.DevPlanRepository;
import ogya.lokakarya.be.service.DevPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DevPlanServiceImpl implements DevPlanService {
    @Autowired
    private DevPlanRepository devPlanRepository;

    @Override
    public DevPlan create(CreateDevPlan data) {
        return devPlanRepository.save(data.toEntity());

    }

    @Override
    public List<DevPlanDto> getAllDevPlans() {
        List<DevPlanDto> listResult=new ArrayList<>();
        List<DevPlan> devPlanList=devPlanRepository.findAll();
        for(DevPlan devPlan : devPlanList) {
            DevPlanDto result= convertToDto(devPlan);
            listResult.add(result);
        }
        return listResult;
    }

    @Override
    public DevPlanDto getDevPlanById(UUID id) {
        Optional<DevPlan> listData;
        try{
            listData=devPlanRepository.findById(id);
            System.out.println(listData);
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
        DevPlan data= listData.get();
        return convertToDto(data);
    }

    @Override
    public DevPlanDto updateDevPlanById(UUID id, CreateDevPlan createDevPlan) {
        Optional<DevPlan> listData= devPlanRepository.findById(id);
        if(listData.isPresent()){
            DevPlan devPlan= listData.get();
            if(!createDevPlan.getPlan().isBlank()){
                devPlan.setPlan(createDevPlan.getPlan());
//                devPlan.setEnabled(createDevPlan.geE;
            }
            DevPlanDto devPlanDto= convertToDto(devPlan);
            devPlanRepository.save(devPlan);
            return devPlanDto;
        }
        return null;
    }

    @Override
    public boolean deleteDevPlanById(UUID id) {
        Optional<DevPlan> listData= devPlanRepository.findById(id);
        if(listData.isPresent()){
            devPlanRepository.delete(listData.get());
            return ResponseEntity.ok().build().hasBody();
        }else{
            return ResponseEntity.notFound().build().hasBody();
        }
    }

    private DevPlanDto convertToDto(DevPlan data){
        DevPlanDto result = new DevPlanDto(data);
        return result;
    }
}
