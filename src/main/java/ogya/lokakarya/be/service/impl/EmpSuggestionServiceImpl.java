package ogya.lokakarya.be.service.impl;

import ogya.lokakarya.be.dto.empsuggestion.EmpSuggestionReq;
import ogya.lokakarya.be.dto.empsuggestion.EmpSuggestionDto;
import ogya.lokakarya.be.entity.EmpSuggestion;
import ogya.lokakarya.be.repository.EmpSuggestionRepository;
import ogya.lokakarya.be.service.EmpSuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmpSuggestionServiceImpl implements EmpSuggestionService {
    @Autowired
    EmpSuggestionRepository empSuggestionRepository;

    @Override
    public EmpSuggestion create(EmpSuggestionReq data) {
        return empSuggestionRepository.save(data.toEntity());
    }

    @Override
    public List<EmpSuggestionDto> getAllEmpSuggestions() {
        List<EmpSuggestionDto> listResult=new ArrayList<>();
        List<EmpSuggestion> empSuggestionList=empSuggestionRepository.findAll();
        for(EmpSuggestion empSuggestion : empSuggestionList) {
            System.out.println(empSuggestion);
            EmpSuggestionDto result= convertToDto(empSuggestion);
            listResult.add(result);
        }
        return listResult;
    }

    @Override
    public EmpSuggestionDto getEmpSuggestionById(UUID id) {
        System.out.println("QUERYING FOR: " + id.toString());
        Optional<EmpSuggestion> listData;
        try{
            listData=empSuggestionRepository.findById(id);
            System.out.println(listData);
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
        if (listData.isEmpty()) {
            return null;
        }
        EmpSuggestion data= listData.get();
        return convertToDto(data);
    }

    @Override
    public EmpSuggestionDto updateEmpSuggestionById(UUID id, EmpSuggestionReq empSuggestionReq) {
        Optional<EmpSuggestion> listData= empSuggestionRepository.findById(id);
        if(listData.isPresent()){
            EmpSuggestion empSuggestion= listData.get();
            if(!empSuggestionReq.getSuggestion().isBlank()){
                empSuggestion.setSuggestion(empSuggestionReq.getSuggestion());
                empSuggestion.setAssessmentYear(empSuggestionReq.getAssessmentYear());
            }
            EmpSuggestionDto empSuggestionDto= convertToDto(empSuggestion);
            empSuggestionRepository.save(empSuggestion);
            return empSuggestionDto;
        }
        return null;
    }

    @Override
    public boolean deleteEmpSuggestionById(UUID id) {
        Optional<EmpSuggestion> listData= empSuggestionRepository.findById(id);
        if(listData.isPresent()){
            empSuggestionRepository.delete(listData.get());
            return ResponseEntity.ok().build().hasBody();
        }else{
            return ResponseEntity.notFound().build().hasBody();
        }
    }

    private EmpSuggestionDto convertToDto(EmpSuggestion data) {
        EmpSuggestionDto result = new EmpSuggestionDto(data);
        return result;
    }
}
