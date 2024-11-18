package ogya.lokakarya.be.service;

import ogya.lokakarya.be.dto.empsuggestion.EmpSuggestionReq;
import ogya.lokakarya.be.dto.empsuggestion.EmpSuggestionDto;
import ogya.lokakarya.be.entity.EmpSuggestion;
import java.util.List;
import java.util.UUID;

public interface EmpSuggestionService {
    EmpSuggestion create(EmpSuggestionReq data);
    List<EmpSuggestionDto> getAllEmpSuggestions();
    EmpSuggestionDto getEmpSuggestionById(UUID id);
    EmpSuggestionDto updateEmpSuggestionById(UUID id, EmpSuggestionReq empSuggestionReq);
    boolean deleteEmpSuggestionById(UUID id);
}
