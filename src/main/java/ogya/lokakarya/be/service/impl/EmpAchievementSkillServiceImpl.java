package ogya.lokakarya.be.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ogya.lokakarya.be.config.security.SecurityUtil;
import ogya.lokakarya.be.dto.empachievementskill.EmpAchievementSkillDto;
import ogya.lokakarya.be.dto.empachievementskill.EmpAchievementSkillFilter;
import ogya.lokakarya.be.dto.empachievementskill.EmpAchievementSkillReq;
import ogya.lokakarya.be.entity.Achievement;
import ogya.lokakarya.be.entity.EmpAchievementSkill;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.exception.ResponseException;
import ogya.lokakarya.be.repository.AchievementRepository;
import ogya.lokakarya.be.repository.EmpAchievementSkillRepository;
import ogya.lokakarya.be.repository.UserRepository;
import ogya.lokakarya.be.service.AchievementSkillService;

@Service
public class EmpAchievementSkillServiceImpl implements AchievementSkillService {
    @Autowired
    private EmpAchievementSkillRepository achievementSkillRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AchievementRepository achievementRepository;

    @Autowired
    private SecurityUtil securityUtil;

    @Override
    public EmpAchievementSkillDto create(EmpAchievementSkillReq data) {
        User currentUser = securityUtil.getCurrentUser();
        Optional<Achievement> findAchievement =
                achievementRepository.findById(data.getAchievementId());
        if (findAchievement.isEmpty()) {
            throw ResponseException.achievementNotFound(data.getAchievementId());
        }
        EmpAchievementSkill dataEntity = data.toEntity();
        dataEntity.setUser(currentUser);
        dataEntity.setAchievement(findAchievement.get());
        EmpAchievementSkill createdData = achievementSkillRepository.save(dataEntity);
        return new EmpAchievementSkillDto(createdData, true, false);
    }

    @Override
    public List<EmpAchievementSkillDto> getAllAchievementSkills(EmpAchievementSkillFilter filter) {
        List<EmpAchievementSkill> empAchievementEntities =
                achievementSkillRepository.findAllByFilter(filter);
        return empAchievementEntities.stream().map(empAS -> new EmpAchievementSkillDto(empAS,
                filter.getWithCreatedBy(), filter.getWithUpdatedBy())).toList();
    }

    @Override
    public EmpAchievementSkillDto getAchievementSkillById(UUID id) {
        Optional<EmpAchievementSkill> listData;
        try {
            listData = achievementSkillRepository.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        EmpAchievementSkill data = listData.get();
        return convertToDto(data);
    }

    @Override
    public EmpAchievementSkillDto updateAchievementSkillById(UUID id,
            EmpAchievementSkillReq empAchievementSkillReq) {
        Optional<EmpAchievementSkill> listData = achievementSkillRepository.findById(id);
        if (listData.isPresent()) {
            EmpAchievementSkill empAchievementSkill = listData.get();
            if (!empAchievementSkillReq.getNotes().isBlank()) {
                Optional<User> findUser =
                        userRepository.findById(empAchievementSkillReq.getUserId());
                Optional<Achievement> findAchievement =
                        achievementRepository.findById(empAchievementSkillReq.getAchievementId());
                if (findUser.isPresent() && findAchievement.isPresent()) {
                    empAchievementSkill.setAchievement(findAchievement.get());
                    empAchievementSkill.setUser(findUser.get());
                    empAchievementSkill.setNotes(empAchievementSkillReq.getNotes());
                    empAchievementSkill.setScore(empAchievementSkillReq.getScore());
                    empAchievementSkill
                            .setAssessmentYear(empAchievementSkillReq.getAssessmentYear());
                }
            }
            EmpAchievementSkillDto achievementSkillDto = convertToDto(empAchievementSkill);
            achievementSkillRepository.save(empAchievementSkill);
            return achievementSkillDto;
        }
        return null;
    }

    @Override
    public boolean deleteAchievementSkillById(UUID id) {
        Optional<EmpAchievementSkill> listData = achievementSkillRepository.findById(id);
        if (listData.isPresent()) {
            achievementSkillRepository.delete(listData.get());
            return ResponseEntity.ok().build().hasBody();
        } else {
            return ResponseEntity.notFound().build().hasBody();
        }
    }

    public EmpAchievementSkillDto convertToDto(EmpAchievementSkill data) {
        return new EmpAchievementSkillDto(data, true, true);
    }
}
