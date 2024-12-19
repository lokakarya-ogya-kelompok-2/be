package ogya.lokakarya.be.service;

import java.util.UUID;
import org.springframework.data.domain.Page;
import ogya.lokakarya.be.dto.division.DivisionDto;
import ogya.lokakarya.be.dto.division.DivisionFilter;
import ogya.lokakarya.be.dto.division.DivisionReq;

public interface DivisionService {
    DivisionDto create(DivisionReq data);

    Page<DivisionDto> getAllDivisions(DivisionFilter filter);

    DivisionDto getDivisionById(UUID id);

    DivisionDto updateDivisionById(UUID id, DivisionReq divisionReq);

    boolean deleteDivisionById(UUID id);
}
