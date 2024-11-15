package ogya.lokakarya.be.service.impl;

import ogya.lokakarya.be.dto.technicalskill.CreateTechnicalSkill;
import ogya.lokakarya.be.dto.technicalskill.TechnicalSkillDto;
import ogya.lokakarya.be.entity.TechnicalSkill;
import ogya.lokakarya.be.repository.technicalskill.TechnicalSkillRepository;
import ogya.lokakarya.be.service.TechnicalSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TechnicalSkillServiceImpl implements TechnicalSkillService {
    @Autowired
    private TechnicalSkillRepository technicalSkillRepository;

    @Override
    public TechnicalSkill create(CreateTechnicalSkill data) {
        return technicalSkillRepository.save(data.toEntity());
    }

    @Override
    public List<TechnicalSkillDto> getAlltechnicalSkills() {
        List<TechnicalSkillDto> listResult=new ArrayList<>();
        List<TechnicalSkill> technicalSkillList=technicalSkillRepository.findAll();
        for(TechnicalSkill technicalSkill: technicalSkillList) {
            TechnicalSkillDto result= convertToDto(technicalSkill);
            listResult.add(result);
        }
        return listResult;
    }

    @Override
    public TechnicalSkillDto gettechnicalSkillById(UUID id) {
        Optional<TechnicalSkill> listData;
        try{
            listData=technicalSkillRepository.findById(id);
            System.out.println(listData);
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
        TechnicalSkill data= listData.get();
        return convertToDto(data);
    }

    @Override
    public TechnicalSkillDto updateTechnicalSkillById(UUID id, CreateTechnicalSkill createTechnicalSkill) {
        Optional<TechnicalSkill> listData= technicalSkillRepository.findById(id);
        if(listData.isPresent()){
            TechnicalSkill technicalSkill= listData.get();
            if(!createTechnicalSkill.getTechnicalSKill().isBlank()){
                technicalSkill.setTechnicalSkill(createTechnicalSkill.getTechnicalSKill());
            }
            technicalSkillRepository.save(technicalSkill);
            TechnicalSkillDto technicalSkillDto= convertToDto(technicalSkill);
            return technicalSkillDto;
        }
        return null;
    }

    @Override
    public boolean deleteTechnicalSkillById(UUID id) {
        Optional<TechnicalSkill> listData= technicalSkillRepository.findById(id);
        if(listData.isPresent()){
            technicalSkillRepository.delete(listData.get());
            return ResponseEntity.ok().build().hasBody();
        }else{
            return ResponseEntity.notFound().build().hasBody();
        }
    }

    private TechnicalSkillDto convertToDto(TechnicalSkill data){
        TechnicalSkillDto result = new TechnicalSkillDto(data);
        return result;
    }
}
