package ogya.lokakarya.be.service.impl;

import ogya.lokakarya.be.dto.groupachievement.GroupAchievementDto;
import ogya.lokakarya.be.dto.groupachievement.GroupAchievementReq;
import ogya.lokakarya.be.entity.GroupAchievement;
import ogya.lokakarya.be.repository.GroupAchievementRepository;
import ogya.lokakarya.be.service.GroupAchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GroupAchievementServiceImpl implements GroupAchievementService {
    @Autowired
    GroupAchievementRepository groupAchievementRepository;

    @Override
    public GroupAchievementDto create(GroupAchievementReq data) {
        return new GroupAchievementDto(groupAchievementRepository.save(data.toEntity()));
    }

    @Override
    public List<GroupAchievementDto> getAllGroupAchievements() {
        List<GroupAchievementDto> listResult=new ArrayList<>();
        List<GroupAchievement> groupAchievementList=groupAchievementRepository.findAll();
        System.out.println(groupAchievementList);
        for(GroupAchievement groupAchievement : groupAchievementList) {
            GroupAchievementDto result= convertToDto(groupAchievement);
            listResult.add(result);
        }
        return listResult;
    }

    @Override
    public GroupAchievementDto getGroupAchievementById(UUID id) {
        Optional<GroupAchievement> listData;
        try{
            listData=groupAchievementRepository.findById(id);
            System.out.println(listData);
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
        GroupAchievement data= listData.get();
        return convertToDto(data);
    }

    @Override
    public GroupAchievementDto updateGroupAchievementById(UUID id, GroupAchievementReq groupAchievementReq) {
        Optional<GroupAchievement> listData= groupAchievementRepository.findById(id);
        if(listData.isPresent()){
            GroupAchievement groupAchievement= listData.get();
            if(!groupAchievementReq.getGroupName().isBlank()){
                groupAchievement.setGroupName(groupAchievementReq.getGroupName());
                groupAchievement.setEnabled(groupAchievementReq.getEnabled());
                groupAchievement.setPercentage(groupAchievementReq.getPercentage());
            }
            GroupAchievementDto groupAchievementDto= convertToDto(groupAchievement);
            groupAchievementRepository.save(groupAchievement);
            return groupAchievementDto;
        }
        return null;
    }

    @Override
    public boolean deleteGroupAchievementById(UUID id) {
        Optional<GroupAchievement> listData= groupAchievementRepository.findById(id);
        if(listData.isPresent()){
            groupAchievementRepository.delete(listData.get());
            return ResponseEntity.ok().build().hasBody();
        }else{
            return ResponseEntity.notFound().build().hasBody();
        }
    }

    public GroupAchievementDto convertToDto(GroupAchievement data) {
        GroupAchievementDto result=new GroupAchievementDto(data);
        return result;
    }
}
