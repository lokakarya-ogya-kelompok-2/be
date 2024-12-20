package ogya.lokakarya.be.service.impl;

import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import ogya.lokakarya.be.config.security.SecurityUtil;
import ogya.lokakarya.be.dto.division.DivisionDto;
import ogya.lokakarya.be.dto.division.DivisionFilter;
import ogya.lokakarya.be.dto.division.DivisionReq;
import ogya.lokakarya.be.entity.Division;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.exception.ResponseException;
import ogya.lokakarya.be.repository.DivisionRepository;
import ogya.lokakarya.be.repository.specification.DivisionSpecification;
import ogya.lokakarya.be.service.DivisionService;

@Slf4j
@Service
public class DivisionServiceimpl implements DivisionService {
    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private DivisionRepository divisionRepository;

    @Autowired
    private DivisionSpecification spec;

    @Override
    public DivisionDto create(DivisionReq data) {
        log.info("Starting DevPlanServiceImpl.create");
        Division divisionEntity = data.toEntity();
        User currentUser = securityUtil.getCurrentUser();
        divisionEntity.setCreatedBy(currentUser);
        divisionEntity = divisionRepository.save(divisionEntity);
        log.info("Starting DevPlanServiceImpl.create");
        return new DivisionDto(divisionEntity, true, false);
    }

    @Override
    public Page<DivisionDto> getAllDivisions(DivisionFilter filter) {
        log.info("Starting DevPlanServiceImpl.getAllDivisions");

        Specification<Division> specification = Specification.where(null);
        if (filter.getNameContains() != null && !filter.getNameContains().isEmpty()) {
            specification = specification.and(spec.nameContains(filter.getNameContains()));
        }

        Page<Division> divisions;
        if (filter.getPageNumber() != null) {
            Pageable pageable = PageRequest.of(Math.max(0, filter.getPageNumber() - 1),
                    Math.max(1, filter.getPageSize()), Sort.by("createdAt").descending());
            divisions = divisionRepository.findAll(specification, pageable);
        } else {
            divisions = new PageImpl<>(
                    divisionRepository.findAll(specification, Sort.by("createdAt").descending()));
        }

        log.info("Ending DevPlanServiceImpl.getAllDivisions");
        return divisions.map(division -> new DivisionDto(division, filter.getWithCreatedBy(),
                filter.getWithUpdatedBy()));
    }

    @Override
    public DivisionDto getDivisionById(UUID id) {
        log.info("Starting DevPlanServiceImpl.getDivisionById");
        Optional<Division> divisionOpt = divisionRepository.findById(id);
        if (divisionOpt.isEmpty()) {
            throw ResponseException.divisionNotFound(id);
        }
        Division data = divisionOpt.get();
        log.info("Ending DevPlanServiceImpl.getDivisionById");
        return convertToDto(data);
    }

    @Override
    public DivisionDto updateDivisionById(UUID id, DivisionReq divisionReq) {
        log.info("Starting DevPlanServiceImpl.updateDivisionById");
        Optional<Division> divisionOpt = divisionRepository.findById(id);
        if (divisionOpt.isEmpty()) {
            throw ResponseException.divisionNotFound(id);
        }
        Division division = divisionOpt.get();
        if (divisionReq.getDivisionName() != null) {
            division.setDivisionName(divisionReq.getDivisionName());
        }
        User currentUser = securityUtil.getCurrentUser();
        division.setUpdatedBy(currentUser);
        division = divisionRepository.save(division);
        log.info("Ending DevPlanServiceImpl.updateDivisionById");
        return convertToDto(division);

    }

    @Override
    public boolean deleteDivisionById(UUID id) {
        log.info("Starting DevPlanServiceImpl.deleteDivisionById");
        Optional<Division> divisionOpt = divisionRepository.findById(id);
        if (divisionOpt.isEmpty()) {
            throw ResponseException.divisionNotFound(id);
        }
        divisionRepository.delete(divisionOpt.get());
        log.info("Ending DevPlanServiceImpl.deleteDivisionById");
        return true;
    }

    private DivisionDto convertToDto(Division data) {
        return new DivisionDto(data, true, true);
    }
}
