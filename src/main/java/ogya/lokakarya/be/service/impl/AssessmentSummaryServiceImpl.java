package ogya.lokakarya.be.service.impl;

import ogya.lokakarya.be.dto.assessmentsummary.AssessmentSummaryDto;
import ogya.lokakarya.be.dto.assessmentsummary.AssessmentSummaryReq;
import ogya.lokakarya.be.entity.AssessmentSummary;
import ogya.lokakarya.be.entity.User;
import ogya.lokakarya.be.repository.AssessmentSummaryRepository;
import ogya.lokakarya.be.repository.UserRepository;
import ogya.lokakarya.be.service.AssessmentSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AssessmentSummaryServiceImpl implements AssessmentSummaryService {
    @Autowired
    private AssessmentSummaryRepository assessmentSummaryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private View error;

    @Override
    public AssessmentSummaryDto create(AssessmentSummaryReq data) {
        Optional<User> findUser= userRepository.findById(data.getUserId());
        if(findUser.isEmpty()) {
            throw new RuntimeException(String.format("user id could not be found!",
                    data.getUserId().toString()));
        }
        AssessmentSummary dataEntity = data.toEntity();
        dataEntity.setUser(findUser.get());
        AssessmentSummary createData= assessmentSummaryRepository.save(dataEntity);
        return new AssessmentSummaryDto(createData);
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
    public AssessmentSummaryDto updateAssessmentSummaryById(UUID id, AssessmentSummaryReq assessmentSummaryReq) {
        Optional<AssessmentSummary> listData= assessmentSummaryRepository.findById(id);
        if(listData.isPresent()){
            AssessmentSummary assessmentSummary= listData.get();
            if(assessmentSummary.getId().equals(id)){
                assessmentSummary.setYear(assessmentSummaryReq.getYear());
                assessmentSummary.setScore(assessmentSummaryReq.getScore());
                assessmentSummary.setStatus(assessmentSummaryReq.getStatus());
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
