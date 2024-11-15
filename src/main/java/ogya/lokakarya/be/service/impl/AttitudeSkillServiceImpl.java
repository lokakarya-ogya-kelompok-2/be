package ogya.lokakarya.be.service.impl;

import ogya.lokakarya.be.dto.attitudeskill.AttitudeSkillDto;
import ogya.lokakarya.be.dto.attitudeskill.CreateAttitudeSkill;
import ogya.lokakarya.be.entity.AttitudeSkill;
import ogya.lokakarya.be.repository.attitudeskill.AttitudeSkillRepository;
import ogya.lokakarya.be.service.AttitudeSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AttitudeSkillServiceImpl implements AttitudeSkillService {
    @Autowired
    private AttitudeSkillRepository attitudeSkillRepository;

    @Override
    public AttitudeSkill create(CreateAttitudeSkill data) {
        return attitudeSkillRepository.save(data.toEntity());
    }

    @Override
    public List<AttitudeSkillDto> getAllAttitudeSkills() {
        List<AttitudeSkillDto> listResult=new ArrayList<>();
        List<AttitudeSkill> attitudeSkillList=attitudeSkillRepository.findAll();
        for(AttitudeSkill attitudeSkill : attitudeSkillList) {
            AttitudeSkillDto result= convertToDto(attitudeSkill);
            listResult.add(result);
        }
        return listResult;
    }

    @Override
    public AttitudeSkillDto getAttitudeSkillById(UUID id) {
        Optional<AttitudeSkill> listData;
        try{
            listData=attitudeSkillRepository.findById(id);
            System.out.println(listData);
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
        AttitudeSkill data= listData.get();
        return convertToDto(data);
    }

    @Override
    public AttitudeSkillDto updateAttitudeSkillById(UUID id, CreateAttitudeSkill createAttitudeSkill) {
        Optional<AttitudeSkill> listData= attitudeSkillRepository.findById(id);
        if(listData.isPresent()){
            AttitudeSkill attitudeSkill= listData.get();
            if(!createAttitudeSkill.getAttitudeSkill().isBlank()){
                attitudeSkill.setAttitudeSkill(createAttitudeSkill.getAttitudeSkill());
                attitudeSkill.setEnabled(createAttitudeSkill.getEnabled());
            }
            AttitudeSkillDto attitudeSkillDto= convertToDto(attitudeSkill);
            attitudeSkillRepository.save(attitudeSkill);
            return attitudeSkillDto;
        }
        return null;
    }

    @Override
    public boolean deleteAttitudeSkillById(UUID id) {
        Optional<AttitudeSkill> listData= attitudeSkillRepository.findById(id);
        if(listData.isPresent()){
            attitudeSkillRepository.delete(listData.get());
            return ResponseEntity.ok().build().hasBody();
        }else{
            return ResponseEntity.notFound().build().hasBody();
        }
    }

    private AttitudeSkillDto convertToDto(AttitudeSkill data){
        AttitudeSkillDto result = new AttitudeSkillDto(data);
        return result;
    }

}
