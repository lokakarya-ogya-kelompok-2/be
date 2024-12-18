package ogya.lokakarya.be.service.impl;

import lombok.extern.slf4j.Slf4j;
import ogya.lokakarya.be.config.security.SecurityUtil;
import ogya.lokakarya.be.dto.division.DivisionDto;
import ogya.lokakarya.be.dto.division.DivisionFilter;
import ogya.lokakarya.be.dto.division.DivisionReq;
import ogya.lokakarya.be.entity.Division;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.exception.ResponseException;
import ogya.lokakarya.be.repository.DivisionRepository;
import ogya.lokakarya.be.service.DivisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class DivisionServiceimpl implements DivisionService {
    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private DivisionRepository divisionRepository;

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
    public List<DivisionDto> getAllDivisions(DivisionFilter filter) {
        log.info("Starting DevPlanServiceImpl.getAllDivisions");
        List<Division> divisions = divisionRepository.findAllByFilter(filter);
        log.info("Ending DevPlanServiceImpl.getAllDivisions");
        return divisions.stream().map(division -> new DivisionDto(division,
                filter.getWithCreatedBy(), filter.getWithUpdatedBy())).toList();
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
