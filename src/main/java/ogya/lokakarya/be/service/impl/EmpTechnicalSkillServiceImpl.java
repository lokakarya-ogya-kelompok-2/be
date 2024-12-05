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
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import ogya.lokakarya.be.config.security.SecurityUtil;
import ogya.lokakarya.be.dto.emptechnicalskill.EmpTechnicalSkillDto;
import ogya.lokakarya.be.dto.emptechnicalskill.EmpTechnicalSkillFilter;
import ogya.lokakarya.be.dto.emptechnicalskill.EmpTechnicalSkillReq;
import ogya.lokakarya.be.entity.EmpTechnicalSkill;
import ogya.lokakarya.be.entity.Menu;
import ogya.lokakarya.be.entity.TechnicalSkill;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.exception.ResponseException;
import ogya.lokakarya.be.repository.EmpTechnicalSkillRepository;
import ogya.lokakarya.be.repository.TechnicalSkillRepository;
import ogya.lokakarya.be.service.EmpTechnicalSkillService;

@Service
public class EmpTechnicalSkillServiceImpl implements EmpTechnicalSkillService {
    @Autowired
    private EmpTechnicalSkillRepository empTechnicalSkillRepository;


    @Autowired
    private TechnicalSkillRepository technicalSkillRepository;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<EmpTechnicalSkillDto> createBulk(List<EmpTechnicalSkillReq> data) {
        User currentUser = securityUtil.getCurrentUser();
        List<EmpTechnicalSkill> empTechnicalSkillEntities = new ArrayList<>(data.size());
        Map<UUID, TechnicalSkill> technicalSkillIds = new HashMap<>();

        for (EmpTechnicalSkillReq d : data) {
            EmpTechnicalSkill empTechnicalSkillEntity = d.toEntity();
            empTechnicalSkillEntity.setUser(currentUser);
            empTechnicalSkillEntity.setCreatedBy(currentUser);
            if (!technicalSkillIds.containsKey(d.getTechnicalSkillId())) {
                Optional<TechnicalSkill> technicalSkillOpt =
                        technicalSkillRepository.findById(d.getTechnicalSkillId());
                if (technicalSkillOpt.isEmpty()) {
                    throw ResponseException.technicalSkillNotFound(d.getTechnicalSkillId());
                }
                TechnicalSkill technicalSkill = technicalSkillOpt.get();
                technicalSkillIds.put(d.getTechnicalSkillId(), technicalSkill);
            }
            empTechnicalSkillEntity
                    .setTechnicalSkill(technicalSkillIds.get(d.getTechnicalSkillId()));
            empTechnicalSkillEntities.add(empTechnicalSkillEntity);
        }

        empTechnicalSkillEntities = empTechnicalSkillRepository.saveAll(empTechnicalSkillEntities);

        return empTechnicalSkillEntities.stream()
                .map(empTechSkill -> new EmpTechnicalSkillDto(empTechSkill, true, false))
                .collect(Collectors.toList());
    }

    @Override
    public EmpTechnicalSkillDto create(EmpTechnicalSkillReq data) {
        EmpTechnicalSkill empTechnicalSkillEntity = data.toEntity();
        User currentUser = securityUtil.getCurrentUser();
        empTechnicalSkillEntity.setUser(currentUser);

        Optional<TechnicalSkill> technicalSkillOpt =
                technicalSkillRepository.findById(data.getTechnicalSkillId());
        if (technicalSkillOpt.isEmpty()) {
            throw ResponseException.technicalSkillNotFound(data.getTechnicalSkillId());
        }
        TechnicalSkill technicalSkill = technicalSkillOpt.get();
        empTechnicalSkillEntity.setTechnicalSkill(technicalSkill);
        empTechnicalSkillEntity.setAssessmentYear(data.getAssessmentYear());
        empTechnicalSkillEntity.setCreatedBy(currentUser);

        empTechnicalSkillEntity = empTechnicalSkillRepository.save(empTechnicalSkillEntity);
        return new EmpTechnicalSkillDto(empTechnicalSkillEntity, true, false);
    }

    @Override
    public List<EmpTechnicalSkillDto> getAllEmpTechnicalSkills(EmpTechnicalSkillFilter filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<EmpTechnicalSkill> query = cb.createQuery(EmpTechnicalSkill.class);
        Root<EmpTechnicalSkill> root = query.from(EmpTechnicalSkill.class);

        List<Predicate> predicates = new ArrayList<>();
        if (filter.getUserIds() != null) {
            Join<EmpTechnicalSkill, User> techSkillUserJoin = root.join("user", JoinType.LEFT);
            predicates.add(techSkillUserJoin.get("id").in(filter.getUserIds()));
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

        List<EmpTechnicalSkill> empTechSkillEntities =
                entityManager.createQuery(query).getResultList();
        List<EmpTechnicalSkillDto> empTechSkills = new ArrayList<>(empTechSkillEntities.size());
        empTechSkillEntities.forEach(empTS -> empTechSkills.add(new EmpTechnicalSkillDto(empTS,
                filter.getWithCreatedBy(), filter.getWithCreatedBy())));
        return empTechSkills;
    }

    @Override
    public EmpTechnicalSkillDto getEmpTechnicalSkillById(UUID id) {
        Optional<EmpTechnicalSkill> listData;
        try {
            listData = empTechnicalSkillRepository.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        EmpTechnicalSkill data = listData.get();
        return convertToDto(data);
    }

    @Override
    public EmpTechnicalSkillDto updateEmpTechnicalSkillById(UUID id,
            EmpTechnicalSkillReq empTechnicalSkillReq) {
        User currentUser = securityUtil.getCurrentUser();
        Optional<EmpTechnicalSkill> empTechnicalSkillOpt = empTechnicalSkillRepository.findById(id);
        if (empTechnicalSkillOpt.isEmpty()) {
            throw ResponseException.empTechnicalSkillNotFound(id);
        }

        EmpTechnicalSkill empTechnicalSkill = empTechnicalSkillOpt.get();
        if (empTechnicalSkillReq.getScore() != null) {
            empTechnicalSkill.setScore(empTechnicalSkillReq.getScore());
        }
        if (empTechnicalSkillReq.getDetail() != null) {
            empTechnicalSkill.setTechnicalSkilLDetail(empTechnicalSkillReq.getDetail());
        }
        empTechnicalSkill.setUpdatedBy(currentUser);

        empTechnicalSkill = empTechnicalSkillRepository.save(empTechnicalSkill);

        return new EmpTechnicalSkillDto(empTechnicalSkill, true, true);
    }

    @Override
    public boolean deleteEmpTechnicalSkillById(UUID id) {
        Optional<EmpTechnicalSkill> listData = empTechnicalSkillRepository.findById(id);
        if (listData.isPresent()) {
            empTechnicalSkillRepository.delete(listData.get());
            return ResponseEntity.ok().build().hasBody();
        } else {
            return ResponseEntity.notFound().build().hasBody();
        }
    }

    public EmpTechnicalSkillDto convertToDto(EmpTechnicalSkill data) {
        return new EmpTechnicalSkillDto(data, true, true);
    }
}