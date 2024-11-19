package ogya.lokakarya.be.service.impl;

import jakarta.persistence.EntityNotFoundException;
import ogya.lokakarya.be.dto.empattitudeskill.EmpAttitudeSkillDto;
import ogya.lokakarya.be.dto.empattitudeskill.EmpAttitudeSkillReq;
import ogya.lokakarya.be.entity.AttitudeSkill;
import ogya.lokakarya.be.entity.EmpAttitudeSkill;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.repository.AttitudeSkillRepository;
import ogya.lokakarya.be.repository.EmpAttitudeSkillRepository;
import ogya.lokakarya.be.repository.UserRepository;
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
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AttitudeSkillRepository attitudeSkillRepository;


    @Override
    public EmpAttitudeSkillDto create(EmpAttitudeSkillReq data) {
        Optional<AttitudeSkill> findAttitudeSkill = attitudeSkillRepository.findById(data.getAttitudeSkillId());
        Optional<User> findUser= userRepository.findById(data.getUserId());
        if(findUser.isEmpty()){
            throw new EntityNotFoundException(String.format("User not found",
                    data.getUserId().toString()));
        }
        if(findAttitudeSkill.isEmpty()){
            throw new EntityNotFoundException(String.format("Attitude Skill not found",
                    data.getAttitudeSkillId().toString()));
        }
        EmpAttitudeSkill dataEntity = data.toEntity();
        dataEntity.setUser(findUser.get());
        dataEntity.setAttitudeSkill(findAttitudeSkill.get());
        EmpAttitudeSkill createData = empAttitudeSkillRepository.save(dataEntity);
        return new EmpAttitudeSkillDto(createData);
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
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
        EmpAttitudeSkill data= listData.get();
//        return convertToDto(data);
        return new EmpAttitudeSkillDto(data);
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
