package ogya.lokakarya.be.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
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
import ogya.lokakarya.be.repository.specification.AssessmentSummarySpecification;
import ogya.lokakarya.be.service.AssessmentSummaryService;
import ogya.lokakarya.be.service.EmpAchievementSkillService;

@Slf4j
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

    @Autowired
    private AssessmentSummarySpecification assSpec;

    @Override
    public EmpAchievementSkillDto create(EmpAchievementSkillReq data) {
        log.info("Starting EmpAchievementSkillServiceImpl.create");
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
        log.info("Starting EmpAchievementSkillServiceImpl.create");
        return new EmpAchievementSkillDto(createdData, true, false);
    }

    @Override
    public List<EmpAchievementSkillDto> getAllAchievementSkills(EmpAchievementSkillFilter filter) {
        log.info("Starting EmpAchievementSkillServiceImpl.getAllAchievementSkills");
        List<EmpAchievementSkill> empAchievementEntities =
                empAchievementSkillRepository.findAllByFilter(filter);
        log.info("Ending EmpAchievementSkillServiceImpl.getAllAchievementSkills");
        return empAchievementEntities.stream().map(empAS -> new EmpAchievementSkillDto(empAS,
                filter.getWithCreatedBy(), filter.getWithUpdatedBy())).toList();
    }

    @Override
    public EmpAchievementSkillDto getAchievementSkillById(UUID id) {
        log.info("Starting EmpAchievementSkillServiceImpl.getAchievementSkillById");
        Optional<EmpAchievementSkill> empAchievementOpt =
                empAchievementSkillRepository.findById(id);
        if (empAchievementOpt.isEmpty()) {
            throw ResponseException.empAchievementNotFound(id);
        }
        EmpAchievementSkill data = empAchievementOpt.get();
        log.info("Ending EmpAchievementSkillServiceImpl.getAchievementSkillById");
        return convertToDto(data);
    }

    @Override
    public EmpAchievementSkillDto updateAchievementSkillById(UUID id,
            EmpAchievementSkillReq empAchievementSkillReq) {
        log.info("Starting EmpAchievementSkillServiceImpl.updateAchievementSkillById");

        Optional<EmpAchievementSkill> empAchievementOpt =
                empAchievementSkillRepository.findById(id);
        if (empAchievementOpt.isEmpty()) {
            throw ResponseException.empAchievementNotFound(id);
        }
        EmpAchievementSkill empAchievement = empAchievementOpt.get();
        User currentUser = securityUtil.getCurrentUser();
        boolean isHR = currentUser.getUserRoles().stream()
                .anyMatch(userRole -> userRole.getRole().getRoleName().equalsIgnoreCase("hr"));
        boolean isSPVOfSameDivision = currentUser.getUserRoles().stream()
                .anyMatch(userRole -> userRole.getRole().getRoleName().equalsIgnoreCase("svp"))
                && empAchievement.getUser() != null
                && empAchievement.getUser().getDivision().equals(currentUser.getDivision());
        if (!(isHR || isSPVOfSameDivision)) {
            throw ResponseException.unauthorized();
        }
        Optional<AssessmentSummary> assessmentSummaryOpt = assessmentSummaryRepo
                .findOne(assSpec.userIdIn(List.of(empAchievement.getUser().getId()))
                        .and(assSpec.yearIn(List.of(empAchievement.getAssessmentYear()))));
        if (assessmentSummaryOpt.isEmpty()) {
            throw new ResponseException(String.format(
                    "Assessment summary for user with id %s and year %d could not be found!",
                    empAchievement.getUser().getId(), empAchievement.getAssessmentYear()),
                    HttpStatus.NOT_FOUND);
        }
        AssessmentSummary assessmentSummary = assessmentSummaryOpt.get();
        if (assessmentSummary.getApprovalStatus() == 1) {
            throw new ResponseException("You're not allowed to perform this action!",
                    HttpStatus.FORBIDDEN);
        }
        if (empAchievementSkillReq.getScore() != null) {
            empAchievement.setScore(empAchievementSkillReq.getScore());
        }
        if (empAchievementSkillReq.getAssessmentYear() != null) {
            empAchievement.setAssessmentYear(empAchievementSkillReq.getAssessmentYear());
        }
        if (empAchievementSkillReq.getUserId() != null) {
            Optional<User> userOpt = userRepository.findById(empAchievementSkillReq.getUserId());
            if (userOpt.isEmpty()) {
                throw ResponseException.userNotFound(empAchievementSkillReq.getUserId());
            }
            empAchievement.setUser(userOpt.get());
        }
        if (empAchievementSkillReq.getAchievementId() != null) {
            Optional<Achievement> achievementOpt =
                    achievementRepository.findById(empAchievementSkillReq.getAchievementId());
            if (achievementOpt.isEmpty()) {
                throw ResponseException
                        .achievementNotFound(empAchievementSkillReq.getAchievementId());
            }
            empAchievement.setAchievement(achievementOpt.get());
        }
        empAchievement.setUpdatedBy(currentUser);
        empAchievement = empAchievementSkillRepository.save(empAchievement);

        log.info("Starting EmpAchievementSkillServiceImpl.updateAchievementSkillById");
        return convertToDto(empAchievement);
    }

    @Override
    public boolean deleteAchievementSkillById(UUID id) {
        log.info("Starting EmpAchievementSkillServiceImpl.deleteAchievementSkillById");

        Optional<EmpAchievementSkill> empAchievementOpt =
                empAchievementSkillRepository.findById(id);
        if (empAchievementOpt.isEmpty()) {
            throw ResponseException.empAchievementNotFound(id);
        }
        EmpAchievementSkill empAchievement = empAchievementOpt.get();
        User currentUser = securityUtil.getCurrentUser();
        boolean isHR = currentUser.getUserRoles().stream()
                .anyMatch(userRole -> userRole.getRole().getRoleName().equalsIgnoreCase("hr"));
        boolean isSPVOfSameDivision = currentUser.getUserRoles().stream()
                .anyMatch(userRole -> userRole.getRole().getRoleName().equalsIgnoreCase("svp"))
                && empAchievement.getUser() != null
                && empAchievement.getUser().getDivision().equals(currentUser.getDivision());
        if (!(isHR || isSPVOfSameDivision)) {
            throw ResponseException.unauthorized();
        }
        empAchievementSkillRepository.delete(empAchievement);

        log.info("Ending EmpAchievementSkillServiceImpl.deleteAchievementSkillById");
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
        log.info("Starting EmpAchievementSkillServiceImpl.createBulk");
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
            assessmentSummaries.add(assessmentSummaryEntity);
        });
        assessmentSummaryRepo.saveAll(assessmentSummaries);
        log.info("Ending EmpAchievementSkillServiceImpl.createBulk");
        return empAchievementSkillEntities.stream()
                .map(empAc -> new EmpAchievementSkillDto(empAc, true, false)).toList();
    }
}
