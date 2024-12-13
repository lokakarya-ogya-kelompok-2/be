package ogya.lokakarya.be.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import ogya.lokakarya.be.config.security.SecurityUtil;
import ogya.lokakarya.be.dto.empsuggestion.EmpSuggestionDto;
import ogya.lokakarya.be.dto.empsuggestion.EmpSuggestionFilter;
import ogya.lokakarya.be.dto.empsuggestion.EmpSuggestionReq;
import ogya.lokakarya.be.entity.EmpSuggestion;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.exception.ResponseException;
import ogya.lokakarya.be.repository.EmpSuggestionRepository;
import ogya.lokakarya.be.service.EmpSuggestionService;

@Service
public class EmpSuggestionServiceImpl implements EmpSuggestionService {
    @Autowired
    EmpSuggestionRepository empSuggestionRepository;

    @Autowired
    private SecurityUtil securityUtil;

    @Transactional
    @Override
    public List<EmpSuggestionDto> createBulk(List<EmpSuggestionReq> data) {
        User currentUser = securityUtil.getCurrentUser();
        List<EmpSuggestion> empSuggestionEntities = new ArrayList<>(data.size());
        for (EmpSuggestionReq d : data) {
            EmpSuggestion empSuggestionEntity = d.toEntity();
            empSuggestionEntity.setUser(currentUser);
            empSuggestionEntity.setCreatedBy(currentUser);
            empSuggestionEntities.add(empSuggestionEntity);
        }
        empSuggestionEntities = empSuggestionRepository.saveAll(empSuggestionEntities);
        return empSuggestionEntities.stream()
                .map(empSuggestion -> new EmpSuggestionDto(empSuggestion, true, false)).toList();
    }

    @Override
    public EmpSuggestionDto create(EmpSuggestionReq data) {
        User currentUser = securityUtil.getCurrentUser();
        EmpSuggestion dataEmpSuggestion = data.toEntity();
        dataEmpSuggestion.setUser(currentUser);
        EmpSuggestion createdEmpSuggestion = empSuggestionRepository.save(dataEmpSuggestion);
        return new EmpSuggestionDto(createdEmpSuggestion, true, true);
    }

    @Override
    public List<EmpSuggestionDto> getAllEmpSuggestions(EmpSuggestionFilter filter) {
        List<EmpSuggestionDto> listResult = new ArrayList<>();
        List<EmpSuggestion> empSuggestionList = empSuggestionRepository.findAllByFilter(filter);
        for (EmpSuggestion empSuggestion : empSuggestionList) {
            EmpSuggestionDto result = convertToDto(empSuggestion);
            listResult.add(result);
        }
        return listResult;
    }

    @Override
    public EmpSuggestionDto getEmpSuggestionById(UUID id) {
        Optional<EmpSuggestion> empSuggestionOpt = empSuggestionRepository.findById(id);
        if (empSuggestionOpt.isEmpty()) {
            throw ResponseException.empSuggestionNotFound(id);
        }
        EmpSuggestion data = empSuggestionOpt.get();
        return convertToDto(data);
    }

    @Override
    public EmpSuggestionDto updateEmpSuggestionById(UUID id, EmpSuggestionReq empSuggestionReq) {
        Optional<EmpSuggestion> empSuggestionOpt = empSuggestionRepository.findById(id);
        if (empSuggestionOpt.isEmpty()) {
            throw ResponseException.empSuggestionNotFound(id);
        }
        EmpSuggestion empSuggestion = empSuggestionOpt.get();
        if (empSuggestionReq.getSuggestion() != null) {
            empSuggestion.setSuggestion(empSuggestionReq.getSuggestion());
        }
        if (empSuggestion.getAssessmentYear() != null) {
            empSuggestion.setAssessmentYear(empSuggestionReq.getAssessmentYear());
        }
        User currentUser = securityUtil.getCurrentUser();
        empSuggestion.setUpdatedBy(currentUser);

        empSuggestion = empSuggestionRepository.save(empSuggestion);
        return convertToDto(empSuggestion);

    }

    @Override
    public boolean deleteEmpSuggestionById(UUID id) {
        Optional<EmpSuggestion> empSuggestionOpt = empSuggestionRepository.findById(id);
        if (empSuggestionOpt.isEmpty()) {
            throw ResponseException.empSuggestionNotFound(id);
        }
        empSuggestionRepository.delete(empSuggestionOpt.get());
        return true;
    }

    private EmpSuggestionDto convertToDto(EmpSuggestion data) {
        return new EmpSuggestionDto(data, true, true);
    }
}
