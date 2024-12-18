package ogya.lokakarya.be.service;

import java.util.List;
import java.util.UUID;
import ogya.lokakarya.be.dto.division.DivisionDto;
import ogya.lokakarya.be.dto.division.DivisionFilter;
import ogya.lokakarya.be.dto.division.DivisionReq;

public interface DivisionService {
    DivisionDto create(DivisionReq data);

    List<DivisionDto> getAllDivisions(DivisionFilter filter);

    DivisionDto getDivisionById(UUID id);

    DivisionDto updateDivisionById(UUID id, DivisionReq divisionReq);

    boolean deleteDivisionById(UUID id);
}
