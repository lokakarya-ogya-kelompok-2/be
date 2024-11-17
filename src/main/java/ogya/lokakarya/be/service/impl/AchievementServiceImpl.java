package ogya.lokakarya.be.service.impl;

import ogya.lokakarya.be.dto.achievement.AchievementDto;
import ogya.lokakarya.be.dto.achievement.AchievementReq;
import ogya.lokakarya.be.entity.Achievement;
import ogya.lokakarya.be.repository.achievement.AchievementRepository;
import ogya.lokakarya.be.service.AchievementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AchievementServiceImpl implements AchievementService {
    @Autowired
    AchievementRepository achievementRepository;
    @Autowired
    private static final Logger LOG = LoggerFactory.getLogger(AchievementServiceImpl.class);

    @Override
    public Achievement create(AchievementReq data) {
        LOG.info("Start service: create achievement");
        System.out.println(data);
        LOG.info("End service: create achievement");
        return achievementRepository.save(data.toEntity());
    }

    @Override
    public List<AchievementDto> getAllAchievements() {
        List<AchievementDto> listResult= new ArrayList<>();
        List<Achievement> achievementsList = achievementRepository.findAll();
        for(Achievement achievement: achievementsList) {
            AchievementDto result= convertToDto(achievement);
            listResult.add(result);
        }
        return listResult;
    }

    @Override
    public AchievementDto getAchievementsById(UUID id) {
        Optional<Achievement> listData;
        try{
            listData = achievementRepository.findById(id);
            System.out.println(listData);
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
        Achievement data = listData.get();
        return convertToDto(data);
    }

    @Override
    public AchievementDto updateAchievementById(UUID id, AchievementReq achievementReq) {
        Optional<Achievement> listData= achievementRepository.findById(id);
        if(listData.isPresent()) {
            Achievement achievement= listData.get();
            if(!achievement.getAchievement().isBlank()){
                achievement.setAchievement(achievementReq.getAchievement());
                achievement.setEnabled(achievementReq.getEnabled());
            }
            AchievementDto achievementDto= convertToDto(achievement);
            achievementRepository.save(achievement);
            return achievementDto;
        }
        return null;
    }

    @Override
    public boolean deleteAchievementById(UUID id) {
        Optional<Achievement> listData= achievementRepository.findById(id);
        if(listData.isPresent()){
            achievementRepository.delete(listData.get());
            return ResponseEntity.ok().build().hasBody();
        }else{
            return ResponseEntity.notFound().build().hasBody();
        }
    }

    public AchievementDto convertToDto(Achievement data) {
        AchievementDto result = new AchievementDto(data);
        return result;
    }
}
