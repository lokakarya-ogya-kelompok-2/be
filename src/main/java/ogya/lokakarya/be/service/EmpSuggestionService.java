package ogya.lokakarya.be.service;

import ogya.lokakarya.be.dto.empsuggestion.EmpSuggestionDto;
import ogya.lokakarya.be.dto.empsuggestion.EmpSuggestionFilter;
import ogya.lokakarya.be.dto.empsuggestion.EmpSuggestionReq;

import java.util.List;
import java.util.UUID;

public interface EmpSuggestionService {
    List<EmpSuggestionDto> createBulk(List<EmpSuggestionReq> data);
    EmpSuggestionDto create(EmpSuggestionReq data);
    List<EmpSuggestionDto> getAllEmpSuggestions(EmpSuggestionFilter filter);
    EmpSuggestionDto getEmpSuggestionById(UUID id);
    EmpSuggestionDto updateEmpSuggestionById(UUID id, EmpSuggestionReq empSuggestionReq);
    boolean deleteEmpSuggestionById(UUID id);
}
