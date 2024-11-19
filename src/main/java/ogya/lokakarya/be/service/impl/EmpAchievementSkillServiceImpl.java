package ogya.lokakarya.be.service.impl;

import ogya.lokakarya.be.dto.empachievementskill.EmpAchievementSkillDto;
import ogya.lokakarya.be.dto.empachievementskill.EmpAchievementSkillReq;
import ogya.lokakarya.be.entity.Achievement;
import ogya.lokakarya.be.entity.EmpAchievementSkill;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.repository.AchievementRepository;
import ogya.lokakarya.be.repository.AchievementSkillRepository;
import ogya.lokakarya.be.repository.UserRepository;
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
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AchievementRepository achievementRepository;

    @Override
    public EmpAchievementSkillDto create(EmpAchievementSkillReq data) {
        Optional<User> findUser= userRepository.findById(data.getUserId());
        Optional<Achievement> findAchievement= achievementRepository.findById(data.getAchievementId());
        if(findUser.isEmpty()) {
            throw new RuntimeException(String.format("user id could not be found!",
                    data.getUserId().toString()));
        }
        if(findAchievement.isEmpty()) {
            throw new RuntimeException(String.format("achievement id could not be found!",
                    data.getAchievementId().toString()));
        }
        EmpAchievementSkill dataEntity= data.toEntity();
        dataEntity.setUser(findUser.get());
        dataEntity.setAchievement(findAchievement.get());
        EmpAchievementSkill createdData=  achievementSkillRepository.save(dataEntity);
        return new EmpAchievementSkillDto(createdData);
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
                Optional<User> findUser= userRepository.findById(empAchievementSkillReq.getUserId());
                Optional<Achievement> findAchievement= achievementRepository.findById(empAchievementSkillReq.getAchievementId());
                if(findUser.isPresent() && findAchievement.isPresent()) {
                    empAchievementSkill.setAchievement(findAchievement.get());
                    empAchievementSkill.setUser(findUser.get());
                    empAchievementSkill.setNotes(empAchievementSkillReq.getNotes());
                    empAchievementSkill.setScore(empAchievementSkillReq.getScore());
                    empAchievementSkill.setAssessmentYear(empAchievementSkillReq.getAssessmentYear());
                }
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
