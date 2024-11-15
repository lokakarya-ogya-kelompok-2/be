package ogya.lokakarya.be.service.impl;

import ogya.lokakarya.be.dto.division.DivisionDto;
import ogya.lokakarya.be.dto.groupAttitudeSkill.CreateGroupAttitudeSkill;
import ogya.lokakarya.be.dto.groupAttitudeSkill.GroupAttitudeSkillDto;
import ogya.lokakarya.be.entity.Division;
import ogya.lokakarya.be.entity.GroupAttitudeSkill;
import ogya.lokakarya.be.repository.groupAttitudeSkill.GroupAttitudeSkillRepository;
import ogya.lokakarya.be.service.GroupAttitudeSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GroupAttitudeSKillServiceImpl implements GroupAttitudeSkillService {
    @Autowired
    private GroupAttitudeSkillRepository groupAttitudeSkillRepository;

    @Override
    public GroupAttitudeSkill create(CreateGroupAttitudeSkill data) {
        return groupAttitudeSkillRepository.save(data.toEntity());
    }

    @Override
    public List<GroupAttitudeSkillDto> getAllGroupAttitudeSkills() {
        List<GroupAttitudeSkillDto> listResult=new ArrayList<>();
        List<GroupAttitudeSkill> groupAttitudeSkillList=groupAttitudeSkillRepository.findAll();
        for(GroupAttitudeSkill groupAttitudeSkill : groupAttitudeSkillList) {
            GroupAttitudeSkillDto result= convertToDto(groupAttitudeSkill);
            listResult.add(result);
        }
        return listResult;
    }

    @Override
    public GroupAttitudeSkillDto getGroupAttitudeSkillById(UUID id) {
        Optional<GroupAttitudeSkill> listData;
        try{
            listData=groupAttitudeSkillRepository.findById(id);
            System.out.println(listData);
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
        GroupAttitudeSkill data= listData.get();
        return convertToDto(data);
    }

    @Override
    public GroupAttitudeSkillDto updateGroupAttitudeSkillById(UUID id, CreateGroupAttitudeSkill createGroupAttitudeSkill) {
        Optional<GroupAttitudeSkill> listData= groupAttitudeSkillRepository.findById(id);
        if(listData.isPresent()){
            GroupAttitudeSkill groupAttitudeSkill= listData.get();
            if(!createGroupAttitudeSkill.getGroupName().isBlank()){
                groupAttitudeSkill.setGroupName(createGroupAttitudeSkill.getGroupName());
            }
            GroupAttitudeSkillDto groupAttitudeSkillDto= convertToDto(groupAttitudeSkill);
            groupAttitudeSkillRepository.save(groupAttitudeSkill);
            return groupAttitudeSkillDto;
        }
        return null;
    }

    @Override
    public boolean deleteGroupAttitudeSkillById(UUID id) {
        Optional<GroupAttitudeSkill> listData= groupAttitudeSkillRepository.findById(id);
        if(listData.isPresent()){
            groupAttitudeSkillRepository.delete(listData.get());
            return ResponseEntity.ok().build().hasBody();
        }else{
            return ResponseEntity.notFound().build().hasBody();
        }
    }

    private GroupAttitudeSkillDto convertToDto(GroupAttitudeSkill data){
        GroupAttitudeSkillDto result = new GroupAttitudeSkillDto(data);
        return result;
    }
}
