package ogya.lokakarya.be.service.impl;

import ogya.lokakarya.be.dto.empachievementskill.EmpAchievementSkillDto;
import ogya.lokakarya.be.dto.empachievementskill.EmpAchievementSkillReq;
import ogya.lokakarya.be.entity.EmpAchievementSkill;
import ogya.lokakarya.be.repository.AchievementSkillRepository;
import ogya.lokakarya.be.service.AchievementSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmpAchievementSkillServiceImpl implements AchievementSkillService {
    @Autowired
    private AchievementSkillRepository achievementSkillRepository;

    @Override
    public EmpAchievementSkill create(EmpAchievementSkillReq data) {
        return achievementSkillRepository.save(data.toEntity());
    }

    @Override
    public List<EmpAchievementSkillDto> getAllAchievementSkills() {
        List<EmpAchievementSkillDto> listResult=new ArrayList<>();
        List<EmpAchievementSkill> achievementSkillList=achievementSkillRepository.findAll();
        for(EmpAchievementSkill empAchievementSkill : achievementSkillList) {
            EmpAchievementSkillDto result= convertToDto(empAchievementSkill);
            listResult.add(result);
        }
        System.out.println(listResult);
        return listResult;
    }

    @Override
    public EmpAchievementSkillDto getAchievementSkillById(UUID id) {
        Optional<EmpAchievementSkill> listData;
        try{
            listData=achievementSkillRepository.findById(id);
            System.out.println(listData);
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
        EmpAchievementSkill data= listData.get();
        return convertToDto(data);
    }

    @Override
    public EmpAchievementSkillDto updateAchievementSkillById(UUID id, EmpAchievementSkillReq empAchievementSkillReq) {
        Optional<EmpAchievementSkill> listData= achievementSkillRepository.findById(id);
        if(listData.isPresent()){
            EmpAchievementSkill empAchievementSkill= listData.get();
            if(!empAchievementSkillReq.getNotes().isBlank()){
                empAchievementSkill.setNotes(empAchievementSkillReq.getNotes());
                empAchievementSkill.setScore(empAchievementSkillReq.getScore());
                empAchievementSkill.setAssessmentYear(empAchievementSkillReq.getAssessmentYear());
            }
            EmpAchievementSkillDto achievementSkillDto= convertToDto(empAchievementSkill);
            achievementSkillRepository.save(empAchievementSkill);
            return achievementSkillDto;
        }
        return null;
    }

    @Override
    public boolean deleteAchievementSkillById(UUID id) {
        Optional<EmpAchievementSkill> listData= achievementSkillRepository.findById(id);
        if(listData.isPresent()){
            achievementSkillRepository.delete(listData.get());
            return ResponseEntity.ok().build().hasBody();
        }else{
            return ResponseEntity.notFound().build().hasBody();
        }
    }
    public EmpAchievementSkillDto convertToDto(EmpAchievementSkill data) {
        EmpAchievementSkillDto result= new EmpAchievementSkillDto(data);
        return result;
    }
}
