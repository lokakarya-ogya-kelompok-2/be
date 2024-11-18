package ogya.lokakarya.be.service.impl;

import ogya.lokakarya.be.dto.empattitudeskill.EmpAttitudeSkillDto;
import ogya.lokakarya.be.dto.empattitudeskill.EmpAttitudeSkillReq;
import ogya.lokakarya.be.entity.EmpAttitudeSkill;
import ogya.lokakarya.be.repository.EmpAttitudeSkillRepository;
import ogya.lokakarya.be.service.EmpAttitudeSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmpAttitudeSkillServiceImpl implements EmpAttitudeSkillService {
    @Autowired
    private EmpAttitudeSkillRepository empAttitudeSkillRepository;

    @Override
    public EmpAttitudeSkill create(EmpAttitudeSkillReq data) {
        return empAttitudeSkillRepository.save(data.toEntity());
    }

    @Override
    public List<EmpAttitudeSkillDto> getAllEmpAttitudeSkills() {
        List<EmpAttitudeSkillDto> listResult=new ArrayList<>();
        List<EmpAttitudeSkill> empAttitudeSkillList=empAttitudeSkillRepository.findAll();
        for(EmpAttitudeSkill empAttitudeSkill : empAttitudeSkillList) {
            EmpAttitudeSkillDto result= convertToDto(empAttitudeSkill);
            listResult.add(result);
        }
        return listResult;
    }

    @Override
    public EmpAttitudeSkillDto getEmpAttitudeSkillById(UUID id) {
        Optional<EmpAttitudeSkill> listData;
        try{
            listData=empAttitudeSkillRepository.findById(id);
            System.out.println(listData);
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
        EmpAttitudeSkill data= listData.get();
        return convertToDto(data);
    }

    @Override
    public EmpAttitudeSkillDto updateEmpAttitudeSkillById(UUID id, EmpAttitudeSkillReq empAttitudeSkillReq) {
        Optional<EmpAttitudeSkill> listData= empAttitudeSkillRepository.findById(id);
        if(listData.isPresent()){
            EmpAttitudeSkill empAttitudeSkill= listData.get();
            if(empAttitudeSkillReq.getScore() != null){
                empAttitudeSkill.setScore(empAttitudeSkillReq.getScore());
                empAttitudeSkill.setAssessmentYear(empAttitudeSkillReq.getAssessmentYear());
            }
            EmpAttitudeSkillDto empAttitudeSkillDto= convertToDto(empAttitudeSkill);
            empAttitudeSkillRepository.save(empAttitudeSkill);
            return empAttitudeSkillDto;
        }
        return null;
    }

    @Override
    public boolean deleteEmpAttitudeSkillById(UUID id) {
        Optional<EmpAttitudeSkill> listData= empAttitudeSkillRepository.findById(id);
        if(listData.isPresent()){
            empAttitudeSkillRepository.delete(listData.get());
            return ResponseEntity.ok().build().hasBody();
        }else{
            return ResponseEntity.notFound().build().hasBody();
        }
    }

    public EmpAttitudeSkillDto convertToDto(EmpAttitudeSkill data) {
        EmpAttitudeSkillDto result = new EmpAttitudeSkillDto(data);
        return result;
    }
}
