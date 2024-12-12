package ogya.lokakarya.be.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ogya.lokakarya.be.dto.attitudeskill.AttitudeSkillDto;
import ogya.lokakarya.be.dto.attitudeskill.AttitudeSkillReq;
import ogya.lokakarya.be.entity.AttitudeSkill;
import ogya.lokakarya.be.entity.GroupAttitudeSkill;
import ogya.lokakarya.be.exception.ResponseException;
import ogya.lokakarya.be.repository.AttitudeSkillRepository;
import ogya.lokakarya.be.repository.GroupAttitudeSkillRepository;
import ogya.lokakarya.be.service.AttitudeSkillService;

@Service
public class AttitudeSkillServiceImpl implements AttitudeSkillService {
    @Autowired
    private AttitudeSkillRepository attitudeSkillRepository;
    @Autowired
    private GroupAttitudeSkillRepository groupAttitudeSkillRepository;

    @Override
    public AttitudeSkillDto create(AttitudeSkillReq data) {
        Optional<GroupAttitudeSkill> findGroupAttitudeSKill = groupAttitudeSkillRepository
                .findById(data.getGroupAttitudeSkill());
        if (findGroupAttitudeSKill.isEmpty()) {
            throw ResponseException.groupAttitudeSkillNotFound(data.getGroupAttitudeSkill());
        }
        AttitudeSkill dataEntity = data.toEntity();
        dataEntity.setGroupAttitudeSkill(findGroupAttitudeSKill.get());
        AttitudeSkill createdData = attitudeSkillRepository.save(dataEntity);
        return new AttitudeSkillDto(createdData, true, true, false);
    };

    @Override
    public List<AttitudeSkillDto> getAllAttitudeSkills() {
        List<AttitudeSkillDto> listResult = new ArrayList<>();
        List<AttitudeSkill> attitudeSkillList = attitudeSkillRepository.findAll();
        for (AttitudeSkill attitudeSkill : attitudeSkillList) {
            AttitudeSkillDto result = convertToDto(attitudeSkill);
            listResult.add(result);
        }
        return listResult;
    }

    @Override
    public AttitudeSkillDto getAttitudeSkillById(UUID id) {
        Optional<AttitudeSkill> listData;
        try {
            listData = attitudeSkillRepository.findById(id);
            System.out.println(listData);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        AttitudeSkill data = listData.get();
        return convertToDto(data);
    }

    @Override
    public AttitudeSkillDto updateAttitudeSkillById(UUID id, AttitudeSkillReq attitudeSkillReq) {
        Optional<AttitudeSkill> listData = attitudeSkillRepository.findById(id);
        if (listData.isPresent()) {
            AttitudeSkill attitudeSkill = listData.get();
            if (!attitudeSkillReq.getAttitudeSkill().isBlank()) {
                Optional<GroupAttitudeSkill> findGroupAttitudeSKill = groupAttitudeSkillRepository
                        .findById(attitudeSkillReq.getGroupAttitudeSkill());
                if (findGroupAttitudeSKill.isPresent()) {
                    attitudeSkill.setGroupAttitudeSkill(findGroupAttitudeSKill.get());
                    attitudeSkill.setName(attitudeSkillReq.getAttitudeSkill());
                    attitudeSkill.setEnabled(attitudeSkillReq.getEnabled());
                }
            }
            AttitudeSkillDto attitudeSkillDto = convertToDto(attitudeSkill);
            attitudeSkillRepository.save(attitudeSkill);
            return attitudeSkillDto;
        }
        return null;
    }

    @Override
    public boolean deleteAttitudeSkillById(UUID id) {
        Optional<AttitudeSkill> listData = attitudeSkillRepository.findById(id);
        if (listData.isPresent()) {
            attitudeSkillRepository.delete(listData.get());
            return ResponseEntity.ok().build().hasBody();
        } else {
            return ResponseEntity.notFound().build().hasBody();
        }
    }

    private AttitudeSkillDto convertToDto(AttitudeSkill data) {
        return new AttitudeSkillDto(data, true, true, true);
    }

}
