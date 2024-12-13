package ogya.lokakarya.be.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import ogya.lokakarya.be.config.security.SecurityUtil;
import ogya.lokakarya.be.dto.assessmentsummary.AssessmentSummaryDto;
import ogya.lokakarya.be.dto.empachievementskill.EmpAchievementSkillDto;
import ogya.lokakarya.be.dto.empachievementskill.EmpAchievementSkillFilter;
import ogya.lokakarya.be.dto.empachievementskill.EmpAchievementSkillReq;
import ogya.lokakarya.be.entity.Achievement;
import ogya.lokakarya.be.entity.AssessmentSummary;
import ogya.lokakarya.be.entity.EmpAchievementSkill;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.exception.ResponseException;
import ogya.lokakarya.be.repository.AchievementRepository;
import ogya.lokakarya.be.repository.AssessmentSummaryRepository;
import ogya.lokakarya.be.repository.EmpAchievementSkillRepository;
import ogya.lokakarya.be.repository.UserRepository;
import ogya.lokakarya.be.service.AssessmentSummaryService;
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
    private AssessmentSummaryService assessmentSummaryService;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private AssessmentSummaryRepository assessmentSummaryRepo;

    @Override
    public EmpAchievementSkillDto create(EmpAchievementSkillReq data) {
        Optional<Achievement> findAchievement =
                achievementRepository.findById(data.getAchievementId());
        if (findAchievement.isEmpty()) {
            throw ResponseException.achievementNotFound(data.getAchievementId());
        }
        EmpAchievementSkill dataEntity = data.toEntity();
        if (data.getUserId() != null) {
            Optional<User> userOpt = userRepository.findById(data.getUserId());
            if (userOpt.isEmpty()) {
                throw ResponseException.userNotFound(data.getUserId());
            }
            dataEntity.setUser(userOpt.get());
        }
        dataEntity.setAchievement(findAchievement.get());
        User currentUser = securityUtil.getCurrentUser();
        dataEntity.setCreatedBy(currentUser);
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
        Optional<EmpAchievementSkill> empAchievementOpt =
                empAchievementSkillRepository.findById(id);
        if (empAchievementOpt.isEmpty()) {
            throw ResponseException.empAchievementNotFound(id);
        }
        EmpAchievementSkill data = empAchievementOpt.get();
        return convertToDto(data);
    }

    @Override
    public EmpAchievementSkillDto updateAchievementSkillById(UUID id,
            EmpAchievementSkillReq empAchievementSkillReq) {
        Optional<EmpAchievementSkill> empAchievementOpt =
                empAchievementSkillRepository.findById(id);
        if (empAchievementOpt.isEmpty()) {
            throw ResponseException.empAchievementNotFound(id);
        }
        EmpAchievementSkill empAchievementSkill = empAchievementOpt.get();
        if (empAchievementSkillReq.getNotes() != null) {
            empAchievementSkill.setNotes(empAchievementSkillReq.getNotes());
        }
        if (empAchievementSkillReq.getScore() != null) {
            empAchievementSkill.setScore(empAchievementSkillReq.getScore());
        }
        if (empAchievementSkillReq.getAssessmentYear() != null) {
            empAchievementSkill.setAssessmentYear(empAchievementSkillReq.getAssessmentYear());
        }
        if (empAchievementSkillReq.getUserId() != null) {
            Optional<User> userOpt = userRepository.findById(empAchievementSkillReq.getUserId());
            if (userOpt.isEmpty()) {
                throw ResponseException.userNotFound(empAchievementSkillReq.getUserId());
            }
            empAchievementSkill.setUser(userOpt.get());
        }
        if (empAchievementSkillReq.getAchievementId() != null) {
            Optional<Achievement> achievementOpt =
                    achievementRepository.findById(empAchievementSkillReq.getAchievementId());
            if (achievementOpt.isEmpty()) {
                throw ResponseException
                        .achievementNotFound(empAchievementSkillReq.getAchievementId());
            }
            empAchievementSkill.setAchievement(achievementOpt.get());
        }
        User currentUser = securityUtil.getCurrentUser();
        empAchievementSkill.setUpdatedBy(currentUser);

        empAchievementSkill = empAchievementSkillRepository.save(empAchievementSkill);
        return convertToDto(empAchievementSkill);
    }

    @Override
    public boolean deleteAchievementSkillById(UUID id) {
        Optional<EmpAchievementSkill> empAchievementOpt =
                empAchievementSkillRepository.findById(id);
        if (empAchievementOpt.isEmpty()) {
            throw ResponseException.empAchievementNotFound(id);
        }
        empAchievementSkillRepository.delete(empAchievementOpt.get());
        return true;
    }

    public EmpAchievementSkillDto convertToDto(EmpAchievementSkill data) {
        return new EmpAchievementSkillDto(data, true, true);
    }

    @AllArgsConstructor
    @Data
    private class UserIdAndYear {
        private UUID userId;
        private Integer year;
    }

    @Transactional
    @Override
    public List<EmpAchievementSkillDto> createBulk(List<EmpAchievementSkillReq> data) {
        User currentUser = securityUtil.getCurrentUser();

        Set<UserIdAndYear> userIdAndYears = new HashSet<>();

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
            userIdAndYears.add(new UserIdAndYear(empAc.getUserId(), empAc.getAssessmentYear()));
            empAchievementSkillEntity.setAchievement(achievementOpt.get());
            empAchievementSkillEntity.setCreatedBy(currentUser);
            return empAchievementSkillEntity;
        }).toList();
        empAchievementSkillEntities =
                empAchievementSkillRepository.saveAll(empAchievementSkillEntities);
        entityManager.flush();

        List<AssessmentSummary> assessmentSummaries = new ArrayList<>();
        userIdAndYears.forEach(uIdAndYear -> {
            AssessmentSummaryDto assessmentSummary = assessmentSummaryService
                    .calculateAssessmentSummary(uIdAndYear.getUserId(), uIdAndYear.getYear());
            AssessmentSummary assessmentSummaryEntity = new AssessmentSummary();
            assessmentSummaryEntity.setScore(assessmentSummary.getScore());
            User user = new User();
            user.setId(uIdAndYear.getUserId());
            assessmentSummaryEntity.setId(assessmentSummary.getId());
            assessmentSummaryEntity.setUser(user);
            assessmentSummaryEntity.setYear(assessmentSummary.getYear());
            assessmentSummaryEntity.setStatus(assessmentSummary.getUser().getEmployeeStatus());
            assessmentSummaries.add(assessmentSummaryEntity);
        });

        assessmentSummaryRepo.saveAll(assessmentSummaries);

        return empAchievementSkillEntities.stream()
                .map(empAc -> new EmpAchievementSkillDto(empAc, true, false)).toList();
    }
}
