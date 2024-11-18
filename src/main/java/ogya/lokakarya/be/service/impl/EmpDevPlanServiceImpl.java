package ogya.lokakarya.be.service.impl;

import ogya.lokakarya.be.dto.empdevplan.EmpDevPlanDto;
import ogya.lokakarya.be.dto.empdevplan.EmpDevPlanReq;
import ogya.lokakarya.be.entity.EmpDevPlan;
import ogya.lokakarya.be.repository.EmpDevPlanRepository;
import ogya.lokakarya.be.service.EmpDevPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmpDevPlanServiceImpl implements EmpDevPlanService {
    @Autowired
    EmpDevPlanRepository empDevPlanRepository;

    @Override
    public EmpDevPlan create(EmpDevPlanReq data) {
        return empDevPlanRepository.save(data.toEntity());
    }

    @Override
    public List<EmpDevPlanDto> getAllEmpDevPlans() {
        List<EmpDevPlanDto> listResult=new ArrayList<>();
        List<EmpDevPlan> empDevPlanList=empDevPlanRepository.findAll();
        for(EmpDevPlan empDevPlan : empDevPlanList) {
            EmpDevPlanDto result= convertToDto(empDevPlan);
            listResult.add(result);
        }
        return listResult;
    }

    @Override
    public EmpDevPlanDto getEmpDevPlanById(UUID id) {
        Optional<EmpDevPlan> listData;
        try{
            listData=empDevPlanRepository.findById(id);
            System.out.println(listData);
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
        EmpDevPlan data= listData.get();
        return convertToDto(data);
    }

    @Override
    public EmpDevPlanDto updateEmpDevPlanById(UUID id, EmpDevPlanReq empDevPlanReq) {
        Optional<EmpDevPlan> listData= empDevPlanRepository.findById(id);
        if(listData.isPresent()){
            EmpDevPlan empDevPlan= listData.get();
            if(empDevPlanReq.getAssessmentYear() !=null){
                empDevPlan.setAssessmentYear(empDevPlanReq.getAssessmentYear());
            }
            EmpDevPlanDto empDevPlanDto= convertToDto(empDevPlan);
            empDevPlanRepository.save(empDevPlan);
            return empDevPlanDto;
        }
        return null;
    }

    @Override
    public boolean deleteEmpDevPlanById(UUID id) {
        Optional<EmpDevPlan> listData= empDevPlanRepository.findById(id);
        if(listData.isPresent()){
            empDevPlanRepository.delete(listData.get());
            return ResponseEntity.ok().build().hasBody();
        }else{
            return ResponseEntity.notFound().build().hasBody();
        }
    }

    public EmpDevPlanDto convertToDto(EmpDevPlan data) {
        EmpDevPlanDto result=new EmpDevPlanDto(data);
        return result;
    }
}
