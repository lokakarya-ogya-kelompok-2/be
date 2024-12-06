package ogya.lokakarya.be.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import ogya.lokakarya.be.config.security.SecurityUtil;
import ogya.lokakarya.be.dto.empattitudeskill.EmpAttitudeSkillDto;
import ogya.lokakarya.be.dto.empattitudeskill.EmpAttitudeSkillFilter;
import ogya.lokakarya.be.dto.empattitudeskill.EmpAttitudeSkillReq;
import ogya.lokakarya.be.entity.AttitudeSkill;
import ogya.lokakarya.be.entity.EmpAttitudeSkill;
import ogya.lokakarya.be.entity.Menu;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.exception.ResponseException;
import ogya.lokakarya.be.repository.AttitudeSkillRepository;
import ogya.lokakarya.be.repository.EmpAttitudeSkillRepository;
import ogya.lokakarya.be.repository.UserRepository;
import ogya.lokakarya.be.service.EmpAttitudeSkillService;

@Service
public class EmpAttitudeSkillServiceImpl implements EmpAttitudeSkillService {
    @Autowired
    private EmpAttitudeSkillRepository empAttitudeSkillRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AttitudeSkillRepository attitudeSkillRepository;
    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private EntityManager entityManager;


    @Override
    public List<EmpAttitudeSkillDto> createBulkEmpAttitudeSkill(List<EmpAttitudeSkillReq> data) {
        User currentUser = securityUtil.getCurrentUser();
        List<EmpAttitudeSkill> empAttitudeSkillsEntities = new ArrayList<>(data.size());
        Map<UUID, AttitudeSkill> attitudeSkillIds = new HashMap<>();
        for (EmpAttitudeSkillReq d : data) {
            EmpAttitudeSkill empAttitudeSkillEntity = d.toEntity();
            empAttitudeSkillEntity.setUser(currentUser);
            empAttitudeSkillEntity.setCreatedBy(currentUser);
            if (!attitudeSkillIds.containsKey(d.getAttitudeSkillId())) {
                Optional<AttitudeSkill> attitudeSkillOpt =
                        attitudeSkillRepository.findById(d.getAttitudeSkillId());
                if (attitudeSkillOpt.isEmpty()) {
                    throw ResponseException.empAttitudeSkillNotFound(d.getAttitudeSkillId());
                }
                AttitudeSkill attitudeSkill = attitudeSkillOpt.get();
                attitudeSkillIds.put(d.getAttitudeSkillId(), attitudeSkill);
            }
            empAttitudeSkillEntity.setAttitudeSkill(attitudeSkillIds.get(d.getAttitudeSkillId()));
            empAttitudeSkillsEntities.add(empAttitudeSkillEntity);
        }
        empAttitudeSkillsEntities = empAttitudeSkillRepository.saveAll(empAttitudeSkillsEntities);
        return empAttitudeSkillsEntities.stream()
                .map(empAttSkill -> new EmpAttitudeSkillDto(empAttSkill, true, false))
                .collect(Collectors.toList());
    }

    @Override
    public EmpAttitudeSkillDto create(EmpAttitudeSkillReq data) {
        Optional<AttitudeSkill> findAttitudeSkill =
                attitudeSkillRepository.findById(data.getAttitudeSkillId());
        User currentUser = securityUtil.getCurrentUser();
        if (findAttitudeSkill.isEmpty()) {
            throw new EntityNotFoundException(String.format("Attitude Skill not found",
                    data.getAttitudeSkillId().toString()));
        }
        EmpAttitudeSkill dataEntity = data.toEntity();
        dataEntity.setUser(currentUser);
        dataEntity.setCreatedBy(currentUser);
        dataEntity.setAttitudeSkill(findAttitudeSkill.get());
        EmpAttitudeSkill createData = empAttitudeSkillRepository.save(dataEntity);
        return new EmpAttitudeSkillDto(createData, true, false);
    }

    @Override
    public List<EmpAttitudeSkillDto> getAllEmpAttitudeSkills(EmpAttitudeSkillFilter filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<EmpAttitudeSkill> query = cb.createQuery(EmpAttitudeSkill.class);
        Root<EmpAttitudeSkill> root = query.from(EmpAttitudeSkill.class);

        List<Predicate> predicates = new ArrayList<>();
        if (filter.getUserIds() != null) {
            Join<EmpAttitudeSkill, User> attitudeSkillUserJoin = root.join("user", JoinType.LEFT);
            predicates.add(attitudeSkillUserJoin.get("id").in(filter.getUserIds()));
        }

        if (filter.getYears() != null) {
            predicates.add(root.get("assessmentYear").in(filter.getYears()));
        }

        if (!predicates.isEmpty()) {
            query.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        }

        if (filter.getWithCreatedBy().booleanValue() || filter.getWithUpdatedBy().booleanValue()) {
            Join<Menu, User> userJoin = null;
            if (filter.getWithCreatedBy().booleanValue()) {
                userJoin = root.join("createdBy", JoinType.LEFT);
            }
            if (filter.getWithUpdatedBy().booleanValue()) {
                if (userJoin == null) {
                    root.join("updatedBy", JoinType.LEFT);
                } else {
                    userJoin.join("updatedBy", JoinType.LEFT);
                }
            }
        }

        query.distinct(true);
        query.select(root);
        List<EmpAttitudeSkill> empAttitudeSkillEntities =
                entityManager.createQuery(query).getResultList();
        System.out.println(empAttitudeSkillEntities.size() + " INI ARRAYNYA");
        empAttitudeSkillEntities.forEach(empas -> {
            // System.out.println("LOOP YG ATAS:");
            // System.out.println(empas.getUser() == null);
        });
        System.out.println(empAttitudeSkillEntities.size() + " ADA BERAPA ELEMEN");

        List<EmpAttitudeSkillDto> empAttitudeSkills =
                new ArrayList<>(empAttitudeSkillEntities.size());
        empAttitudeSkillEntities.forEach(empAS -> {
            empAttitudeSkills.add(new EmpAttitudeSkillDto(empAS, filter.getWithCreatedBy(),
                    filter.getWithCreatedBy()));
            // System.out.println("YANG DARI ENTITY: ");
            // System.out.println(empAS.getUser() == null);
            // System.out.println("YANG DARI DTO: ");
            // System.out.println(empAttitudeSkills.getLast().getUser() == null);
        });
        return empAttitudeSkills;

    }

    @Override
    public EmpAttitudeSkillDto getEmpAttitudeSkillById(UUID id) {
        Optional<EmpAttitudeSkill> listData;
        try {
            listData = empAttitudeSkillRepository.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        EmpAttitudeSkill data = listData.get();
        return new EmpAttitudeSkillDto(data, true, true);
    }

    @Override
    public EmpAttitudeSkillDto updateEmpAttitudeSkillById(UUID id,
            EmpAttitudeSkillReq empAttitudeSkillReq) {
        Optional<EmpAttitudeSkill> listData = empAttitudeSkillRepository.findById(id);
        if (listData.isPresent()) {
            EmpAttitudeSkill empAttitudeSkill = listData.get();
            if (empAttitudeSkillReq.getScore() != null) {
                empAttitudeSkill.setScore(empAttitudeSkillReq.getScore());
                empAttitudeSkill.setAssessmentYear(empAttitudeSkillReq.getAssessmentYear());
            }
            EmpAttitudeSkillDto empAttitudeSkillDto = convertToDto(empAttitudeSkill);
            empAttitudeSkillRepository.save(empAttitudeSkill);
            return empAttitudeSkillDto;
        }
        return null;
    }

    @Override
    public boolean deleteEmpAttitudeSkillById(UUID id) {
        Optional<EmpAttitudeSkill> listData = empAttitudeSkillRepository.findById(id);
        if (listData.isPresent()) {
            empAttitudeSkillRepository.delete(listData.get());
            return ResponseEntity.ok().build().hasBody();
        } else {
            return ResponseEntity.notFound().build().hasBody();
        }
    }

    public EmpAttitudeSkillDto convertToDto(EmpAttitudeSkill data) {
        EmpAttitudeSkillDto result = new EmpAttitudeSkillDto(data, true, true);
        return result;
    }
}
