package ogya.lokakarya.be.service.impl;

import ogya.lokakarya.be.dto.emptechnicalskill.EmpTechnicalSkillDto;
import ogya.lokakarya.be.dto.emptechnicalskill.EmpTechnicalSkillReq;
import ogya.lokakarya.be.entity.EmpTechnicalSkill;
import ogya.lokakarya.be.entity.TechnicalSkill;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.repository.EmpTechnicalSkillRepository;
import ogya.lokakarya.be.repository.TechnicalSkillRepository;
import ogya.lokakarya.be.repository.UserRepository;
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
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TechnicalSkillRepository technicalSkillRepository;

    @Override
    public EmpTechnicalSkillDto create(EmpTechnicalSkillReq data) {
        Optional<User> findUser= userRepository.findById(data.getUserId());
        Optional<TechnicalSkill> findTechnicalSkill= technicalSkillRepository.findById(data.getTechnicalSkillId());
        System.out.println(" ID: " + data.getUserId());
        if(findUser.isEmpty()) {
            throw new RuntimeException(String.format("user id could not be found!",
                    data.getUserId().toString()));
        }
        if(findTechnicalSkill.isEmpty()) {
            throw new RuntimeException(String.format("technical skill id could not be found!",
                    data.getTechnicalSkillId().toString()));
        }
        EmpTechnicalSkill dataEntity= data.toEntity();
        dataEntity.setUser(findUser.get());
        dataEntity.setTechnicalSkill(findTechnicalSkill.get());
        EmpTechnicalSkill createdData= empTechnicalSkillRepository.save(dataEntity);
        return new EmpTechnicalSkillDto(createdData);
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
                Optional<User> findUser= userRepository.findById(empTechnicalSkillReq.getUserId());
                if(findUser.isPresent()){
                    empTechnicalSkill.setUser(findUser.get());
                    empTechnicalSkill.setScore(empTechnicalSkillReq.getScore());
                    empTechnicalSkill.setAssessmentYear(empTechnicalSkillReq.getAssessmentYear());
                }
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
