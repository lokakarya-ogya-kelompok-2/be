package ogya.lokakarya.be.service;

import ogya.lokakarya.be.dto.empsuggestion.EmpSuggestionDto;
import ogya.lokakarya.be.dto.empsuggestion.EmpSuggestionReq;

import java.util.List;
import java.util.UUID;

public interface EmpSuggestionService {
    EmpSuggestionDto create(EmpSuggestionReq data);
    List<EmpSuggestionDto> getAllEmpSuggestions();
    EmpSuggestionDto getEmpSuggestionById(UUID id);
    EmpSuggestionDto updateEmpSuggestionById(UUID id, EmpSuggestionReq empSuggestionReq);
    boolean deleteEmpSuggestionById(UUID id);
}
