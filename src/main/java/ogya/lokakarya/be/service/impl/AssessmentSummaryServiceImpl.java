package ogya.lokakarya.be.service.impl;

import ogya.lokakarya.be.dto.assessmentsummary.AssessmentSummaryDto;
import ogya.lokakarya.be.dto.assessmentsummary.CreateAssessmentSummary;
import ogya.lokakarya.be.entity.AssessmentSummary;
import ogya.lokakarya.be.repository.assessmentsummary.AssessmentSummaryRepository;
import ogya.lokakarya.be.service.AssessmentSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AssessmentSummaryServiceImpl implements AssessmentSummaryService {
    @Autowired
    private AssessmentSummaryRepository assessmentSummaryRepository;

    @Override
    public AssessmentSummary create(CreateAssessmentSummary data) {
        return assessmentSummaryRepository.save(data.toEntity());
    }

    @Override
    public List<AssessmentSummaryDto> getAllAssessmentSummaries() {
        List<AssessmentSummaryDto> listResult=new ArrayList<>();
        List<AssessmentSummary> assessmentSummaryList=assessmentSummaryRepository.findAll();
        for(AssessmentSummary assessmentSummary : assessmentSummaryList) {
            AssessmentSummaryDto result= convertToDto(assessmentSummary);
            listResult.add(result);
        }
        return listResult;
    }

    @Override
    public AssessmentSummaryDto getAssessmentSummaryById(UUID id) {
        Optional<AssessmentSummary> listData;
        try{
            listData=assessmentSummaryRepository.findById(id);
            System.out.println(listData);
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
        AssessmentSummary data= listData.get();
        return convertToDto(data);
    }

    @Override
    public AssessmentSummaryDto updateAssessmentSummaryById(UUID id, CreateAssessmentSummary createAssessmentSummary) {
        Optional<AssessmentSummary> listData= assessmentSummaryRepository.findById(id);
        if(listData.isPresent()){
            AssessmentSummary assessmentSummary= listData.get();
            if(assessmentSummary.getId().equals(id)){
                assessmentSummary.setYear(createAssessmentSummary.getYear());
                assessmentSummary.setScore(createAssessmentSummary.getScore());
                assessmentSummary.setStatus(createAssessmentSummary.getStatus());
            }
            AssessmentSummaryDto assessmentSummaryDto= convertToDto(assessmentSummary);
            assessmentSummaryRepository.save(assessmentSummary);
            return assessmentSummaryDto;
        }
        return null;
    }

    @Override
    public boolean deleteAssessmentSummaryById(UUID id) {
        Optional<AssessmentSummary> listData= assessmentSummaryRepository.findById(id);
        if(listData.isPresent()){
            assessmentSummaryRepository.delete(listData.get());
            return ResponseEntity.ok().build().hasBody();
        }else{
            return ResponseEntity.notFound().build().hasBody();
        }
    }

    private AssessmentSummaryDto convertToDto(AssessmentSummary data){
        AssessmentSummaryDto result = new AssessmentSummaryDto(data);
        return result;
    }
}
