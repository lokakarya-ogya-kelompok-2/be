package ogya.lokakarya.be.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ogya.lokakarya.be.dto.groupattitudeskill.GroupAttitudeSkillDto;
import ogya.lokakarya.be.dto.groupattitudeskill.GroupAttitudeSkillReq;
import ogya.lokakarya.be.entity.GroupAttitudeSkill;
import ogya.lokakarya.be.repository.GroupAttitudeSkillRepository;
import ogya.lokakarya.be.service.GroupAttitudeSkillService;

@Service
public class GroupAttitudeSKillServiceImpl implements GroupAttitudeSkillService {
    @Autowired
    private GroupAttitudeSkillRepository groupAttitudeSkillRepository;

    @Override
    public GroupAttitudeSkillDto create(GroupAttitudeSkillReq data) {
        GroupAttitudeSkill groupAttitudeSkillEntity = data.toEntity();
        groupAttitudeSkillEntity = groupAttitudeSkillRepository.save(groupAttitudeSkillEntity);
        return new GroupAttitudeSkillDto(groupAttitudeSkillEntity, false);
    }

    @Override
    public List<GroupAttitudeSkillDto> getAllGroupAttitudeSkills() {
        List<GroupAttitudeSkillDto> listResult = new ArrayList<>();
        List<GroupAttitudeSkill> groupAttitudeSkillList = groupAttitudeSkillRepository.findAll();
        for (GroupAttitudeSkill groupAttitudeSkill : groupAttitudeSkillList) {
            GroupAttitudeSkillDto result = convertToDto(groupAttitudeSkill);
            listResult.add(result);
        }
        return listResult;
    }

    @Override
    public GroupAttitudeSkillDto getGroupAttitudeSkillById(UUID id) {
        Optional<GroupAttitudeSkill> listData;
        try {
            listData = groupAttitudeSkillRepository.findById(id);
            System.out.println(listData);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        GroupAttitudeSkill data = listData.get();
        return convertToDto(data);
    }

    @Override
    public GroupAttitudeSkillDto updateGroupAttitudeSkillById(UUID id,
            GroupAttitudeSkillReq groupAttitudeSkillReq) {
        Optional<GroupAttitudeSkill> listData = groupAttitudeSkillRepository.findById(id);
        if (listData.isPresent()) {
            GroupAttitudeSkill groupAttitudeSkill = listData.get();
            if (!groupAttitudeSkillReq.getGroupName().isBlank()) {
                groupAttitudeSkill.setGroupName(groupAttitudeSkillReq.getGroupName());
                groupAttitudeSkill.setPercentage(groupAttitudeSkillReq.getPercentage());
                groupAttitudeSkill.setEnabled(groupAttitudeSkillReq.getEnabled());
            }
            GroupAttitudeSkillDto groupAttitudeSkillDto = convertToDto(groupAttitudeSkill);
            groupAttitudeSkillRepository.save(groupAttitudeSkill);
            return groupAttitudeSkillDto;
        }
        return null;
    }

    @Override
    public boolean deleteGroupAttitudeSkillById(UUID id) {
        Optional<GroupAttitudeSkill> listData = groupAttitudeSkillRepository.findById(id);
        if (listData.isPresent()) {
            groupAttitudeSkillRepository.delete(listData.get());
            return ResponseEntity.ok().build().hasBody();
        } else {
            return ResponseEntity.notFound().build().hasBody();
        }
    }

    private GroupAttitudeSkillDto convertToDto(GroupAttitudeSkill data) {
        return new GroupAttitudeSkillDto(data, true);
    }
}
