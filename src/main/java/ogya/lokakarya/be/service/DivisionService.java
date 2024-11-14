package ogya.lokakarya.be.service;

import ogya.lokakarya.be.dto.division.CreateDivision;
import ogya.lokakarya.be.dto.division.DivisionDto;
import ogya.lokakarya.be.entity.Division;

import java.util.List;
import java.util.UUID;

public interface DivisionService {
    Division create(CreateDivision data);
    List<DivisionDto> getAllDivisions();
    DivisionDto getDivisionById(UUID id);
    DivisionDto updateDivisionById(UUID id, CreateDivision createDivision);
    boolean deleteDivisionById(UUID id);
}
