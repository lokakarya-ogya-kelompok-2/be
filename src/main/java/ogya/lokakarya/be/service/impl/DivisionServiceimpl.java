package ogya.lokakarya.be.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ogya.lokakarya.be.config.security.SecurityUtil;
import ogya.lokakarya.be.dto.division.DivisionDto;
import ogya.lokakarya.be.dto.division.DivisionFilter;
import ogya.lokakarya.be.dto.division.DivisionReq;
import ogya.lokakarya.be.entity.Division;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.exception.ResponseException;
import ogya.lokakarya.be.repository.DivisionRepository;
import ogya.lokakarya.be.service.DivisionService;

@Service
public class DivisionServiceimpl implements DivisionService {

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private DivisionRepository divisionRepository;

    @Override
    public DivisionDto create(DivisionReq data) {
        Division divisionEntity = data.toEntity();
        User currentUser = securityUtil.getCurrentUser();
        divisionEntity.setCreatedBy(currentUser);
        divisionEntity = divisionRepository.save(divisionEntity);
        return new DivisionDto(divisionEntity, true, false);
    }

    @Override
    public List<DivisionDto> getAllDivisions(DivisionFilter filter) {
        List<Division> divisions = divisionRepository.findAllByFilter(filter);
        return divisions.stream().map(division -> new DivisionDto(division,
                filter.getWithCreatedBy(), filter.getWithUpdatedBy())).toList();
    }

    @Override
    public DivisionDto getDivisionById(UUID id) {
        Optional<Division> divisionOpt = divisionRepository.findById(id);
        if (divisionOpt.isEmpty()) {
            throw ResponseException.divisionNotFound(id);
        }
        Division data = divisionOpt.get();
        return convertToDto(data);
    }

    @Override
    public DivisionDto updateDivisionById(UUID id, DivisionReq divisionReq) {
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
        return convertToDto(division);

    }

    @Override
    public boolean deleteDivisionById(UUID id) {
        Optional<Division> divisionOpt = divisionRepository.findById(id);
        if (divisionOpt.isEmpty()) {
            throw ResponseException.divisionNotFound(id);
        }
        divisionRepository.delete(divisionOpt.get());
        return true;
    }

    private DivisionDto convertToDto(Division data) {
        return new DivisionDto(data, true, true);
    }
}
