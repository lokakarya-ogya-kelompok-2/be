package ogya.lokakarya.be.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import ogya.lokakarya.be.dto.assessmentsummary.AssessmentSummaryDto;
import ogya.lokakarya.be.dto.assessmentsummary.AssessmentSummaryFilter;
import ogya.lokakarya.be.dto.assessmentsummary.AssessmentSummaryReq;
import ogya.lokakarya.be.dto.assessmentsummary.SummaryData;
import ogya.lokakarya.be.dto.empachievementskill.EmpAchievementSkillFilter;
import ogya.lokakarya.be.dto.empattitudeskill.EmpAttitudeSkillFilter;
import ogya.lokakarya.be.dto.groupachievement.GroupAchievementFilter;
import ogya.lokakarya.be.dto.groupattitudeskill.GroupAttitudeSkillFilter;
import ogya.lokakarya.be.dto.user.UserDto;
import ogya.lokakarya.be.entity.Achievement;
import ogya.lokakarya.be.entity.AssessmentSummary;
import ogya.lokakarya.be.entity.AttitudeSkill;
import ogya.lokakarya.be.entity.EmpAchievementSkill;
import ogya.lokakarya.be.entity.EmpAttitudeSkill;
import ogya.lokakarya.be.entity.GroupAchievement;
import ogya.lokakarya.be.entity.GroupAttitudeSkill;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.exception.ResponseException;
import ogya.lokakarya.be.repository.AssessmentSummaryRepository;
import ogya.lokakarya.be.repository.EmpAchievementSkillRepository;
import ogya.lokakarya.be.repository.EmpAttitudeSkillRepository;
import ogya.lokakarya.be.repository.GroupAchievementRepository;
import ogya.lokakarya.be.repository.GroupAttitudeSkillRepository;
import ogya.lokakarya.be.repository.UserRepository;
import ogya.lokakarya.be.service.AssessmentSummaryService;

@Slf4j
@Service
public class AssessmentSummaryServiceImpl implements AssessmentSummaryService {
    private static final Integer ACHIEVEMENT_PERCENTAGE = 65;
    private static final Integer ATTITUDE_SKILL_PERCENTAGE = 35;
    @Autowired
    private AssessmentSummaryRepository assessmentSummaryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupAttitudeSkillRepository groupAttitudeSkillRepo;

    @Autowired
    private EmpAttitudeSkillRepository empAttitudeSkillRepo;

    @Autowired
    private GroupAchievementRepository groupAchievementRepo;

    @Autowired
    private EmpAchievementSkillRepository empAchievementSkillRepo;


    @Override
    public AssessmentSummaryDto create(AssessmentSummaryReq data) {
        log.info("Starting AssessmentSummaryServiceImpl.getAchievementsById");
        Optional<User> userOpt = userRepository.findById(data.getUserId());
        if (userOpt.isEmpty()) {
            throw ResponseException.userNotFound(data.getUserId());
        }
        AssessmentSummary dataEntity = data.toEntity();
        dataEntity.setUser(userOpt.get());
        dataEntity = assessmentSummaryRepository.save(dataEntity);
        log.info("Ending AssessmentSummaryServiceImpl.getAchievementsById");
        return new AssessmentSummaryDto(dataEntity, true, false);
    }

    @Override
    public List<AssessmentSummaryDto> getAllAssessmentSummaries(AssessmentSummaryFilter filter) {
        log.info("Starting AssessmentSummaryServiceImpl.getAllAssessmentSummaries");
        List<AssessmentSummary> assessmentSummaries =
                assessmentSummaryRepository.findAllByFilter(filter);
        log.info("Ending AssessmentSummaryServiceImpl.getAllAssessmentSummaries");
        return assessmentSummaries.stream()
                .map(assessmentSummary -> new AssessmentSummaryDto(assessmentSummary,
                        filter.getWithCreatedBy(), filter.getWithUpdatedBy()))
                .toList();
    }

    @Override
    public AssessmentSummaryDto getAssessmentSummaryById(UUID id) {
        log.info("Starting AssessmentSummaryServiceImpl.getAssessmentSummaryById");
        Optional<AssessmentSummary> assessmentSummaryOpt = assessmentSummaryRepository.findById(id);
        if (assessmentSummaryOpt.isEmpty()) {
            throw ResponseException.assessmentSummaryNotFound(id);
        }
        log.info("Ending AssessmentSummaryServiceImpl.getAssessmentSummaryById");
        return new AssessmentSummaryDto(assessmentSummaryOpt.get(), true, true);
    }

    @Override
    public AssessmentSummaryDto updateAssessmentSummaryById(UUID id,
            AssessmentSummaryReq assessmentSummaryReq) {
        log.info("Starting AssessmentSummaryServiceImpl.updateAssessmentSummaryById");
        Optional<AssessmentSummary> assessmentSummaryOpt = assessmentSummaryRepository.findById(id);
        if (assessmentSummaryOpt.isEmpty()) {
            throw ResponseException.assessmentSummaryNotFound(id);
        }
        AssessmentSummary assessmentSummary = assessmentSummaryOpt.get();
        if (assessmentSummaryReq.getUserId() != null) {
            Optional<User> userOpt = userRepository.findById(assessmentSummaryReq.getUserId());
            if (userOpt.isEmpty()) {
                throw ResponseException.userNotFound(assessmentSummaryReq.getUserId());
            }
            assessmentSummary.setUser(userOpt.get());
        }
        if (assessmentSummaryReq.getScore() != null) {
            assessmentSummary.setScore(assessmentSummaryReq.getScore());
        }
        if (assessmentSummaryReq.getStatus() != null) {
            assessmentSummary.setStatus(assessmentSummaryReq.getStatus());
        }
        if (assessmentSummaryReq.getYear() != null) {
            assessmentSummary.setYear(assessmentSummaryReq.getYear());
        }
        assessmentSummary = assessmentSummaryRepository.save(assessmentSummary);
        log.info("Ending AssessmentSummaryServiceImpl.updateAssessmentSummaryById");
        return convertToDto(assessmentSummary);
    }

    @Override
    public boolean deleteAssessmentSummaryById(UUID id) {
        log.info("Starting AssessmentSummaryServiceImpl.deleteAssessmentSummaryById");
        Optional<AssessmentSummary> assessmentSummaryOpt = assessmentSummaryRepository.findById(id);
        if (assessmentSummaryOpt.isEmpty()) {
            throw ResponseException.assessmentSummaryNotFound(id);
        }
        assessmentSummaryRepository.delete(assessmentSummaryOpt.get());
        log.info("Ending AssessmentSummaryServiceImpl.deleteAssessmentSummaryById");
        return true;
    }

    private AssessmentSummaryDto convertToDto(AssessmentSummary data) {
        return new AssessmentSummaryDto(data, true, true);
    }

    @Transactional
    @Override
    public AssessmentSummaryDto calculateAssessmentSummaryButValidateTheUserIdFirstBeforeCalculating(
            UUID userId, Integer year) {
        log.info(
                "Starting AssessmentSummaryServiceImpl.calculateAssessmentSummaryButValidateTheUserIdFirstBeforeCalculating");
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw ResponseException.userNotFound(userId);
        }
        log.info(
                "Ending AssessmentSummaryServiceImpl.calculateAssessmentSummaryButValidateTheUserIdFirstBeforeCalculating");
        return calculateAssessmentSummary(userId, year);
    }

    @SuppressWarnings({"java:S6541", "java:S3776"})
    @Transactional
    @Override
    public AssessmentSummaryDto calculateAssessmentSummary(UUID userId, Integer year) {
        log.info("Starting AssessmentSummaryServiceImpl.calculateAssessmentSummary");
        Map<UUID, Object> idToGroup = new HashMap<>();
        // attitude skills mbuh mumet
        Map<UUID, GroupAttitudeSkill> attitudeGroupIdToEntity = new HashMap<>();
        HashMap<UUID, SummaryData> groupAttitudeSkillIdToSummaryData = new HashMap<>();
        GroupAttitudeSkillFilter gasFilter = new GroupAttitudeSkillFilter();
        gasFilter.setEnabledOnly(true);
        gasFilter.setWithAttitudeSkills(true);
        gasFilter.setWithEnabledChildOnly(true);
        List<GroupAttitudeSkill> groupAttitudeSkills =
                groupAttitudeSkillRepo.findAllByFilter(gasFilter);
        Integer attitudeSkillTotalWeight = 0;
        for (GroupAttitudeSkill group : groupAttitudeSkills) {
            if (group.getAttitudeSkills() != null) {
                group.getAttitudeSkills().forEach(attS -> idToGroup.put(attS.getId(), group));
            }
            attitudeGroupIdToEntity.put(group.getId(), group);
            attitudeSkillTotalWeight += group.getPercentage();
        }

        EmpAttitudeSkillFilter easFilter = new EmpAttitudeSkillFilter();
        easFilter.setUserIds(List.of(userId));
        easFilter.setYears(List.of(year));
        easFilter.setEnabledOnly(true);
        List<EmpAttitudeSkill> empAttitudeSkillsEntity =
                empAttitudeSkillRepo.findAllByFilter(easFilter);

        Map<UUID, List<EmpAttitudeSkill>> userEmpAttitudeSkillGrouped = new HashMap<>();
        empAttitudeSkillsEntity.forEach(empAS -> {
            GroupAttitudeSkill group =
                    (GroupAttitudeSkill) idToGroup.get(empAS.getAttitudeSkill().getId());

            if (!userEmpAttitudeSkillGrouped.containsKey(group.getId())) {
                userEmpAttitudeSkillGrouped.put(group.getId(), new ArrayList<>());
            }
            var curr = userEmpAttitudeSkillGrouped.get(group.getId());
            curr.add(empAS);
            userEmpAttitudeSkillGrouped.put(group.getId(), curr);
        });

        // achievements mbuh juga
        Map<UUID, GroupAchievement> achievementGroupIdtoEntity = new HashMap<>();

        Map<UUID, SummaryData> groupAchievementToSummaryData = new HashMap<>();
        GroupAchievementFilter gaFilter = new GroupAchievementFilter();
        gaFilter.setEnabledOnly(true);
        gaFilter.setWithAchievements(true);
        gaFilter.setWithEnabledChildOnly(true);
        List<GroupAchievement> groupAchievements = groupAchievementRepo.findAllByFilter(gaFilter);
        Integer achievementTotalWeight = 0;
        for (GroupAchievement group : groupAchievements) {
            if (group.getAchievements() != null) {
                group.getAchievements().forEach(attS -> idToGroup.put(attS.getId(), group));
            }
            achievementGroupIdtoEntity.put(group.getId(), group);
            achievementTotalWeight += group.getPercentage();
        }

        EmpAchievementSkillFilter eacFilter = new EmpAchievementSkillFilter();
        eacFilter.setUserIds(List.of(userId));
        eacFilter.setYears(List.of(year));
        eacFilter.setEnabledOnly(true);
        List<EmpAchievementSkill> empAchievementEntities =
                empAchievementSkillRepo.findAllByFilter(eacFilter);

        Map<UUID, List<EmpAchievementSkill>> userEmpAchievementGrouped = new HashMap<>();
        empAchievementEntities.forEach(empAc -> {
            GroupAchievement group =
                    (GroupAchievement) idToGroup.get(empAc.getAchievement().getId());
            if (!userEmpAchievementGrouped.containsKey(group.getId())) {
                userEmpAchievementGrouped.put(group.getId(), new ArrayList<>());
            }
            var curr = userEmpAchievementGrouped.get(group.getId());
            curr.add(empAc);
            userEmpAchievementGrouped.put(group.getId(), curr);
        });

        for (GroupAchievement group : groupAchievements) {
            SummaryData summaryData = new SummaryData();
            summaryData.setAspect(group.getGroupName());
            summaryData.setWeight(
                    ((group.getPercentage().doubleValue() / achievementTotalWeight.doubleValue())
                            * ACHIEVEMENT_PERCENTAGE));
            groupAchievementToSummaryData.put(group.getId(), summaryData);
        }

        for (GroupAttitudeSkill group : groupAttitudeSkills) {
            SummaryData summaryData = new SummaryData();
            summaryData.setAspect(group.getGroupName());
            summaryData.setWeight(
                    ((group.getPercentage().doubleValue() / attitudeSkillTotalWeight.doubleValue())
                            * ATTITUDE_SKILL_PERCENTAGE));
            groupAttitudeSkillIdToSummaryData.put(group.getId(), summaryData);
        }

        AssessmentSummaryFilter filter = new AssessmentSummaryFilter();
        filter.setUserIds(List.of(userId));
        filter.setYears(List.of(year));

        List<AssessmentSummary> assessmentSummaries =
                assessmentSummaryRepository.findAllByFilter(filter);
        AssessmentSummary assessmentSummary;
        if (assessmentSummaries.isEmpty()) {
            assessmentSummary = new AssessmentSummary();
            Optional<User> userOpt = userRepository.findById(userId);
            if (userOpt.isEmpty()) {
                throw ResponseException.userNotFound(userId);
            }
            User user = userOpt.get();
            assessmentSummary.setUser(user);
            assessmentSummary.setStatus(user.getEmployeeStatus());
        } else {
            assessmentSummary = assessmentSummaries.getFirst();
        }

        AssessmentSummaryDto assessmentSummaryDto = new AssessmentSummaryDto();
        assessmentSummaryDto.setId(assessmentSummary.getId());
        assessmentSummaryDto.setUser(new UserDto(assessmentSummary.getUser(), false, false, false));
        assessmentSummaryDto.setStatus(assessmentSummary.getStatus());

        Double finalScore = 0d;

        for (Map.Entry<UUID, List<EmpAttitudeSkill>> entry : userEmpAttitudeSkillGrouped
                .entrySet()) {
            GroupAttitudeSkill group = attitudeGroupIdToEntity.get(entry.getKey());
            List<EmpAttitudeSkill> empASs = entry.getValue();

            Integer childCount =
                    group.getAttitudeSkills() != null
                            ? group.getAttitudeSkills().stream().filter(AttitudeSkill::getEnabled)
                                    .toList().size()
                            : 0;

            Integer totalScore = 0;
            for (EmpAttitudeSkill EmpAS : empASs) {
                totalScore += EmpAS.getScore();
            }
            Double maxScore = childCount * 100d;
            Double userScore = (double) (totalScore.doubleValue() / maxScore);

            SummaryData summaryData = groupAttitudeSkillIdToSummaryData.get(group.getId());
            summaryData.setFinalScore((userScore * summaryData.getWeight()));
            summaryData.setScore((userScore * 100l));

            finalScore += summaryData.getFinalScore();
            groupAttitudeSkillIdToSummaryData.put(group.getId(), summaryData);
        }

        for (Map.Entry<UUID, List<EmpAchievementSkill>> entry : userEmpAchievementGrouped
                .entrySet()) {
            GroupAchievement group = achievementGroupIdtoEntity.get(entry.getKey());
            List<EmpAchievementSkill> empAcs = entry.getValue();

            Integer childCount =
                    group.getAchievements() != null
                            ? group.getAchievements().stream().filter(Achievement::getEnabled)
                                    .toList().size()
                            : 0;

            Integer totalScore = 0;
            for (EmpAchievementSkill empAc : empAcs) {
                totalScore += empAc.getScore();
            }
            Double maxScore = childCount * 100d;
            Double userScore = (double) (totalScore.doubleValue() / maxScore);

            SummaryData summaryData = groupAchievementToSummaryData.get(group.getId());
            summaryData.setFinalScore((userScore * summaryData.getWeight()));
            summaryData.setScore((userScore * 100l));

            finalScore += summaryData.getFinalScore();
            groupAchievementToSummaryData.put(group.getId(), summaryData);
        }

        assessmentSummaryDto.setScore(BigDecimal.valueOf(finalScore));
        assessmentSummaryDto.setYear(year);
        assessmentSummaryDto
                .setAchievements(groupAchievementToSummaryData.values().stream().toList());
        assessmentSummaryDto
                .setAttitudeSkills(groupAttitudeSkillIdToSummaryData.values().stream().toList());
        log.info("Ending AssessmentSummaryServiceImpl.calculateAssessmentSummary");
        return assessmentSummaryDto;
    }

    @Transactional
    @Override
    public void recalculateAllAssessmentSummaries() {
        log.info("Starting AssessmentSummaryServiceImpl.recalculateAllAssessmentSummaries");
        List<AssessmentSummary> assessmentSummaries = assessmentSummaryRepository.findAll();
        for (AssessmentSummary assessmentSummary : assessmentSummaries) {
            var newAssessmentSummary = calculateAssessmentSummary(
                    assessmentSummary.getUser().getId(), assessmentSummary.getYear());
            assessmentSummary.setScore(newAssessmentSummary.getScore());
        }
        assessmentSummaryRepository.saveAll(assessmentSummaries);
        log.info("Starting AssessmentSummaryServiceImpl.recalculateAllAssessmentSummaries");
    }

}
