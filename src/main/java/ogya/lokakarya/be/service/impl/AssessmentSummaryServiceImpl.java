package ogya.lokakarya.be.service.impl;

import jakarta.transaction.Transactional;
import ogya.lokakarya.be.dto.assessmentsummary.AssessmentSummaryDto;
import ogya.lokakarya.be.dto.assessmentsummary.AssessmentSummaryReq;
import ogya.lokakarya.be.dto.assessmentsummary.SummaryData;
import ogya.lokakarya.be.dto.empachievementskill.EmpAchievementSkillFilter;
import ogya.lokakarya.be.dto.empattitudeskill.EmpAttitudeSkillFilter;
import ogya.lokakarya.be.dto.user.UserDto;
import ogya.lokakarya.be.entity.AssessmentSummary;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class AssessmentSummaryServiceImpl implements AssessmentSummaryService {
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
        Optional<User> findUser = userRepository.findById(data.getUserId());
        if (findUser.isEmpty()) {
            throw ResponseException.userNotFound(data.getUserId());
        }
        AssessmentSummary dataEntity = data.toEntity();
        dataEntity.setUser(findUser.get());
        AssessmentSummary createData = assessmentSummaryRepository.save(dataEntity);
        return new AssessmentSummaryDto(createData);
    }

    @Override
    public List<AssessmentSummaryDto> getAllAssessmentSummaries() {
        List<AssessmentSummaryDto> listResult = new ArrayList<>();
        List<AssessmentSummary> assessmentSummaryList = assessmentSummaryRepository.findAll();
        for (AssessmentSummary assessmentSummary : assessmentSummaryList) {
            calculateAssessmentSummary(assessmentSummary.getUser().getId(),
                    assessmentSummary.getYear());
            AssessmentSummaryDto result = convertToDto(assessmentSummary);
            listResult.add(result);
        }
        return listResult;
    }

    @Override
    public AssessmentSummaryDto getAssessmentSummaryById(UUID id) {
        Optional<AssessmentSummary> assessmentSummaryOpt = assessmentSummaryRepository.findById(id);
        if (assessmentSummaryOpt.isEmpty()) {
            throw ResponseException.assessmentSummaryNotFound(id);
        }
        return new AssessmentSummaryDto(assessmentSummaryOpt.get());
    }

    @Override
    public AssessmentSummaryDto updateAssessmentSummaryById(UUID id,
            AssessmentSummaryReq assessmentSummaryReq) {
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

        return convertToDto(assessmentSummary);
    }

    @Override
    public boolean deleteAssessmentSummaryById(UUID id) {
        Optional<AssessmentSummary> listData = assessmentSummaryRepository.findById(id);
        if (listData.isPresent()) {
            assessmentSummaryRepository.delete(listData.get());
            return ResponseEntity.ok().build().hasBody();
        } else {
            return ResponseEntity.notFound().build().hasBody();
        }
    }

    private AssessmentSummaryDto convertToDto(AssessmentSummary data) {
        return new AssessmentSummaryDto(data);
    }

    @SuppressWarnings({"java:S6541", "java:S3776"})
    @Transactional
    @Override
    public AssessmentSummaryDto calculateAssessmentSummary(UUID userId, Integer year) {
        Integer totalWeight = 0;

        HashMap<UUID, SummaryData> groupAttitudeSkillIdToSummaryData = new HashMap<>();

        // attitude skills mbuh mumet
        Map<UUID, Object> idToGroup = new HashMap<>();
        List<GroupAttitudeSkill> groupAttitudeSkills = groupAttitudeSkillRepo.findAll();
        for (GroupAttitudeSkill group : groupAttitudeSkills) {
            if (group.getAttitudeSkills() != null) {
                group.getAttitudeSkills().forEach(attS -> idToGroup.put(attS.getId(), group));
            }
            totalWeight += group.getPercentage();
        }

        EmpAttitudeSkillFilter filterAS = new EmpAttitudeSkillFilter();
        filterAS.setUserIds(List.of(userId));
        filterAS.setYears(List.of(year));
        List<EmpAttitudeSkill> empAttitudeSkillsEntity =
                empAttitudeSkillRepo.findAllByFilter(filterAS);

        Map<GroupAttitudeSkill, List<EmpAttitudeSkill>> userEmpAttitudeSkillGrouped =
                new HashMap<>();
        empAttitudeSkillsEntity.forEach(empAS -> {
            GroupAttitudeSkill group =
                    (GroupAttitudeSkill) idToGroup.get(empAS.getAttitudeSkill().getId());

            if (!userEmpAttitudeSkillGrouped.containsKey(group)) {
                userEmpAttitudeSkillGrouped.put(group, new ArrayList<>());
            }
            var curr = userEmpAttitudeSkillGrouped.get(group);
            curr.add(empAS);
            userEmpAttitudeSkillGrouped.put(group, curr);
        });

        // achievements mbuh juga
        idToGroup.clear();

        HashMap<UUID, SummaryData> groupAchievementToSummaryData = new HashMap<>();
        List<GroupAchievement> groupAchievements = groupAchievementRepo.findAll();
        for (GroupAchievement group : groupAchievements) {
            if (group.getAchievements() != null) {
                group.getAchievements().forEach(attS -> idToGroup.put(attS.getId(), group));
            }

            totalWeight += group.getPercentage();
        }


        EmpAchievementSkillFilter filterAc = new EmpAchievementSkillFilter();
        filterAc.setUserIds(List.of(userId));
        filterAc.setYears(List.of(year));
        List<EmpAchievementSkill> empAchievementEntities =
                empAchievementSkillRepo.findAllByFilter(filterAc);
        Map<GroupAchievement, List<EmpAchievementSkill>> userEmpAchievementGrouped =
                new HashMap<>();
        empAchievementEntities.forEach(empAc -> {
            GroupAchievement group =
                    (GroupAchievement) idToGroup.get(empAc.getAchievement().getId());
            if (!userEmpAchievementGrouped.containsKey(group)) {
                userEmpAchievementGrouped.put(group, new ArrayList<>());
            }
            var curr = userEmpAchievementGrouped.get(group);
            curr.add(empAc);
            userEmpAchievementGrouped.put(group, curr);
        });

        for (GroupAchievement group : groupAchievements) {
            SummaryData summaryData = new SummaryData();
            summaryData.setAspect(group.getGroupName());
            summaryData.setWeight((int) Math.round(
                    (group.getPercentage().doubleValue() / totalWeight.doubleValue()) * 100l));
            groupAchievementToSummaryData.put(group.getId(), summaryData);
        }

        for (GroupAttitudeSkill group : groupAttitudeSkills) {
            SummaryData summaryData = new SummaryData();
            summaryData.setAspect(group.getGroupName());
            summaryData.setWeight((int) Math.round(
                    (group.getPercentage().doubleValue() / totalWeight.doubleValue()) * 100l));
            groupAttitudeSkillIdToSummaryData.put(group.getId(), summaryData);
        }

        Optional<AssessmentSummary> assessmentSummaryOpt =
                assessmentSummaryRepository.findByUserIdAndYear(userId, year);
        AssessmentSummary assessmentSummary;
        if (assessmentSummaryOpt.isEmpty()) {
            assessmentSummary = new AssessmentSummary();
            Optional<User> userOpt = userRepository.findById(userId);
            if (userOpt.isEmpty()) {
                throw ResponseException.userNotFound(userId);
            }
            User user = userOpt.get();
            assessmentSummary.setUser(user);
            assessmentSummary.setStatus(user.getEmployeeStatus());
        } else {
            assessmentSummary = assessmentSummaryOpt.get();
        }

        AssessmentSummaryDto assessmentSummaryDto = new AssessmentSummaryDto();
        assessmentSummaryDto.setId(assessmentSummary.getId());
        assessmentSummaryDto.setUser(new UserDto(assessmentSummary.getUser(), false, false, false));
        assessmentSummaryDto.setStatus(assessmentSummary.getStatus());

        Double finalScore = 0d;

        for (Map.Entry<GroupAttitudeSkill, List<EmpAttitudeSkill>> entry : userEmpAttitudeSkillGrouped
                .entrySet()) {
            GroupAttitudeSkill group = entry.getKey();
            List<EmpAttitudeSkill> empASs = entry.getValue();

            Integer currGroupWeight = group.getPercentage();
            Integer childCount =
                    group.getAttitudeSkills() != null ? group.getAttitudeSkills().size() : 0;

            Double currGroupPct = (currGroupWeight.doubleValue() / totalWeight.doubleValue());
            Integer totalScore = 0;
            for (EmpAttitudeSkill EmpAS : empASs) {
                totalScore += EmpAS.getScore();
            }
            Double maxScore = childCount * 100d;
            Double userScore = (double) (totalScore.doubleValue() / maxScore);

            SummaryData summaryData = groupAttitudeSkillIdToSummaryData.get(group.getId());
            summaryData.setFinalScore((int) Math.round(userScore * currGroupPct * 100l));
            summaryData.setScore((int) Math.round(userScore * 100l));

            finalScore += summaryData.getFinalScore();
            groupAttitudeSkillIdToSummaryData.put(group.getId(), summaryData);
        }

        for (Map.Entry<GroupAchievement, List<EmpAchievementSkill>> entry : userEmpAchievementGrouped
                .entrySet()) {
            GroupAchievement group = entry.getKey();
            List<EmpAchievementSkill> empAcs = entry.getValue();

            Integer currGroupWeight = group.getPercentage();
            Integer childCount =
                    group.getAchievements() != null ? group.getAchievements().size() : 0;

            Double currGroupPct = currGroupWeight.doubleValue() / totalWeight.doubleValue();
            Integer totalScore = 0;
            for (EmpAchievementSkill empAc : empAcs) {
                totalScore += empAc.getScore();
            }
            Double maxScore = childCount * 100d;
            Double userScore = (double) (totalScore.doubleValue() / maxScore);

            SummaryData summaryData = groupAchievementToSummaryData.get(group.getId());
            summaryData.setFinalScore((int) Math.round(userScore * currGroupPct * 100l));
            summaryData.setScore((int) Math.round(userScore * 100l));

            finalScore += summaryData.getFinalScore();
            groupAchievementToSummaryData.put(group.getId(), summaryData);
        }

        assessmentSummaryDto.setScore((finalScore.intValue()));
        assessmentSummaryDto.setYear(year);
        assessmentSummaryDto
                .setAchievements(groupAchievementToSummaryData.values().stream().toList());
        assessmentSummaryDto
                .setAttitudeSkills(groupAttitudeSkillIdToSummaryData.values().stream().toList());

        return assessmentSummaryDto;
    }
}
