package ogya.lokakarya.be.service.impl;

import ogya.lokakarya.be.dto.division.DivisionDto;
import ogya.lokakarya.be.dto.emptechnicalskill.EmpTechnicalSkillDto;
import ogya.lokakarya.be.dto.emptechnicalskill.EmpTechnicalSkillReq;
import ogya.lokakarya.be.entity.Division;
import ogya.lokakarya.be.entity.EmpTechnicalSkill;
import ogya.lokakarya.be.repository.EmpTechnicalSkillRepository;
import ogya.lokakarya.be.service.EmpTechnicalSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmpTechnicalSkillServiceImpl implements EmpTechnicalSkillService {
    @Autowired
    private EmpTechnicalSkillRepository empTechnicalSkillRepository;

    @Override
    public EmpTechnicalSkill create(EmpTechnicalSkillReq data) {
        return empTechnicalSkillRepository.save(data.toEntity());
    }

    @Override
    public List<EmpTechnicalSkillDto> getAllEmpTechnicalSkills() {
        List<EmpTechnicalSkillDto> listResult=new ArrayList<>();
        List<EmpTechnicalSkill> empTechnicalSkillList=empTechnicalSkillRepository.findAll();
        for(EmpTechnicalSkill empTechnicalSkill : empTechnicalSkillList) {
            EmpTechnicalSkillDto result= convertToDto(empTechnicalSkill);
            listResult.add(result);
        }
        return listResult;
    }
    @Override
    public EmpTechnicalSkillDto getEmpTechnicalSkillById(UUID id) {
        Optional<EmpTechnicalSkill> listData;
        try{
            listData=empTechnicalSkillRepository.findById(id);
            System.out.println(listData);
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
        EmpTechnicalSkill data= listData.get();
        return convertToDto(data);
    }

    @Override
    public EmpTechnicalSkillDto updateEmpTechnicalSkillById(UUID id, EmpTechnicalSkillReq empTechnicalSkillReq) {
        Optional<EmpTechnicalSkill> listData= empTechnicalSkillRepository.findById(id);
        if(listData.isPresent()){
            EmpTechnicalSkill empTechnicalSkill= listData.get();
            if(empTechnicalSkillReq.getScore() !=null){
                empTechnicalSkill.setScore(empTechnicalSkillReq.getScore());
                empTechnicalSkill.setAssessmentYear(empTechnicalSkillReq.getAssessmentYear());
            }
            EmpTechnicalSkillDto empTechnicalSkillDto= convertToDto(empTechnicalSkill);
            empTechnicalSkillRepository.save(empTechnicalSkill);
            return empTechnicalSkillDto;
        }
        return null;
    }

    @Override
    public boolean deleteEmpTechnicalSkillById(UUID id) {
        Optional<EmpTechnicalSkill> listData= empTechnicalSkillRepository.findById(id);
        if(listData.isPresent()){
            empTechnicalSkillRepository.delete(listData.get());
            return ResponseEntity.ok().build().hasBody();
        }else{
            return ResponseEntity.notFound().build().hasBody();
        }
    }

    public EmpTechnicalSkillDto convertToDto(EmpTechnicalSkill data) {
        EmpTechnicalSkillDto result=new EmpTechnicalSkillDto(data);
        return result;
    }
}
