package ogya.lokakarya.be.service;

import ogya.lokakarya.be.dto.division.DivisionReq;
import ogya.lokakarya.be.dto.division.DivisionDto;
import ogya.lokakarya.be.entity.Division;
import java.util.List;
import java.util.UUID;

public interface DivisionService {
    Division create(DivisionReq data);
    List<DivisionDto> getAllDivisions();
    DivisionDto getDivisionById(UUID id);
    DivisionDto updateDivisionById(UUID id, DivisionReq divisionReq);
    boolean deleteDivisionById(UUID id);
}
