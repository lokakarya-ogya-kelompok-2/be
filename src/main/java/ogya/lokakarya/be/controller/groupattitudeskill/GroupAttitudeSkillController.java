package ogya.lokakarya.be.controller.groupattitudeskill;

import jakarta.validation.Valid;
import ogya.lokakarya.be.dto.groupattitudeskill.GroupAttitudeSkillReq;
import ogya.lokakarya.be.dto.groupattitudeskill.GroupAttitudeSkillDto;
import ogya.lokakarya.be.entity.GroupAttitudeSkill;
import ogya.lokakarya.be.service.GroupAttitudeSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RequestMapping("/group-attitude-skills")
@RestController
public class GroupAttitudeSkillController {
    @Autowired
    GroupAttitudeSkillService groupAttitudeSkillService;

    @PostMapping
    public ResponseEntity<GroupAttitudeSkill> create(@RequestBody @Valid GroupAttitudeSkillReq data) {
        var createdGroupAttitudeSill= groupAttitudeSkillService.create(data);
        return new ResponseEntity<>(createdGroupAttitudeSill, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<GroupAttitudeSkillDto>> getAllGroupAttitudeSkills() {
        System.out.println("Get All Group Attitude Skills");
        List<GroupAttitudeSkillDto> response = groupAttitudeSkillService.getAllGroupAttitudeSkills();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<GroupAttitudeSkillDto> getGroupAttitudeSkillById(@PathVariable UUID id) {
        GroupAttitudeSkillDto response = groupAttitudeSkillService.getGroupAttitudeSkillById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupAttitudeSkillDto> updateDivisionById
            (@PathVariable UUID id, @RequestBody @Valid GroupAttitudeSkillReq groupAttitudeSkillReq) {
        GroupAttitudeSkillDto res= groupAttitudeSkillService.updateGroupAttitudeSkillById(id, groupAttitudeSkillReq);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteGroupAttitudeById(@PathVariable UUID id) {
        boolean res= groupAttitudeSkillService.deleteGroupAttitudeSkillById(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
