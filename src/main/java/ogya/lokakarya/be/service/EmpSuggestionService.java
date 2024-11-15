package ogya.lokakarya.be.service;

import ogya.lokakarya.be.dto.division.CreateDivision;
import ogya.lokakarya.be.dto.division.DivisionDto;
import ogya.lokakarya.be.dto.empsuggestion.CreateEmpSuggestion;
import ogya.lokakarya.be.dto.empsuggestion.EmpSuggestionDto;
import ogya.lokakarya.be.entity.Division;
import ogya.lokakarya.be.entity.EmpSuggestion;

import java.util.List;
import java.util.UUID;

public interface EmpSuggestionService {
    EmpSuggestion create(CreateEmpSuggestion data);
    List<EmpSuggestionDto> getAllEmpSuggestions();
    EmpSuggestionDto getEmpSuggestionById(UUID id);
    EmpSuggestionDto updateEmpSuggestionById(UUID id, CreateEmpSuggestion createEmpSuggestion);
    boolean deleteEmpSuggestionById(UUID id);
}
