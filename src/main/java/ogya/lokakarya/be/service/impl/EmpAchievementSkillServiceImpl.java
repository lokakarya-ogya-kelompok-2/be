package ogya.lokakarya.be.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
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
import ogya.lokakarya.be.service.EmpAchievementSkillService;

@Service
public class EmpAchievementSkillServiceImpl implements EmpAchievementSkillService {
    @Autowired
    private EmpAchievementSkillRepository empAchievementSkillRepository;

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
        EmpAchievementSkill createdData = empAchievementSkillRepository.save(dataEntity);
        return new EmpAchievementSkillDto(createdData, true, false);
    }

    @Override
    public List<EmpAchievementSkillDto> getAllAchievementSkills(EmpAchievementSkillFilter filter) {
        List<EmpAchievementSkill> empAchievementEntities =
                empAchievementSkillRepository.findAllByFilter(filter);
        return empAchievementEntities.stream().map(empAS -> new EmpAchievementSkillDto(empAS,
                filter.getWithCreatedBy(), filter.getWithUpdatedBy())).toList();
    }

    @Override
    public EmpAchievementSkillDto getAchievementSkillById(UUID id) {
        Optional<EmpAchievementSkill> listData;
        try {
            listData = empAchievementSkillRepository.findById(id);
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
        Optional<EmpAchievementSkill> listData = empAchievementSkillRepository.findById(id);
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
            empAchievementSkillRepository.save(empAchievementSkill);
            return achievementSkillDto;
        }
        return null;
    }

    @Override
    public boolean deleteAchievementSkillById(UUID id) {
        Optional<EmpAchievementSkill> listData = empAchievementSkillRepository.findById(id);
        if (listData.isPresent()) {
            empAchievementSkillRepository.delete(listData.get());
            return ResponseEntity.ok().build().hasBody();
        } else {
            return ResponseEntity.notFound().build().hasBody();
        }
    }

    public EmpAchievementSkillDto convertToDto(EmpAchievementSkill data) {
        return new EmpAchievementSkillDto(data, true, true);
    }

    @Transactional
    @Override
    public List<EmpAchievementSkillDto> createBulk(List<EmpAchievementSkillReq> data) {
        User currentUser = securityUtil.getCurrentUser();
        List<EmpAchievementSkill> empAchievementSkillEntities = data.stream().map(empAc -> {
            EmpAchievementSkill empAchievementSkillEntity = empAc.toEntity();
            Optional<User> userOpt = userRepository.findById(empAc.getUserId());
            if (userOpt.isEmpty()) {
                throw ResponseException.userNotFound(empAc.getUserId());
            }
            empAchievementSkillEntity.setUser(userOpt.get());
            Optional<Achievement> achievementOpt =
                    achievementRepository.findById(empAc.getAchievementId());
            if (achievementOpt.isEmpty()) {
                throw ResponseException.achievementNotFound(empAc.getAchievementId());
            }
            empAchievementSkillEntity.setAchievement(achievementOpt.get());
            empAchievementSkillEntity.setCreatedBy(currentUser);
            return empAchievementSkillEntity;
        }).toList();
        empAchievementSkillEntities =
                empAchievementSkillRepository.saveAll(empAchievementSkillEntities);
        return empAchievementSkillEntities.stream()
                .map(empAc -> new EmpAchievementSkillDto(empAc, true, false)).toList();
    }
}
