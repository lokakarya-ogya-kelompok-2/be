package ogya.lokakarya.be.controller.attitudeSkill;

import jakarta.validation.Valid;
import ogya.lokakarya.be.dto.attitudeSkill.AttitudeSkillDto;
import ogya.lokakarya.be.dto.attitudeSkill.CreateAttitudeSkill;
import ogya.lokakarya.be.dto.division.CreateDivision;
import ogya.lokakarya.be.dto.division.DivisionDto;
import ogya.lokakarya.be.entity.AttitudeSkill;
import ogya.lokakarya.be.service.AttitudeSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RequestMapping("/attitude-skill")
@RestController
public class AttitudeSkillController {
    @Autowired
    AttitudeSkillService attitudeSkillService;

    @PostMapping
    public ResponseEntity<AttitudeSkill> create(@RequestBody @Valid CreateAttitudeSkill data) {
        var createdAttitudeSkill= attitudeSkillService.create(data);
        return new ResponseEntity<>(createdAttitudeSkill, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<AttitudeSkillDto>> getAllAttitudeSKills() {
        System.out.println("Get All Attitude Skill");
        List<AttitudeSkillDto> response = attitudeSkillService.getAllAttitudeSkills();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttitudeSkillDto> getAttitudeSkillById(@PathVariable UUID id) {
        AttitudeSkillDto response = attitudeSkillService.getAttitudeSkillById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<AttitudeSkillDto> updateDivisionById
            (@PathVariable UUID id, @RequestBody @Valid CreateAttitudeSkill createAttitudeSkill) {
        AttitudeSkillDto res= attitudeSkillService.updateAttitudeSkillById(id, createAttitudeSkill);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteAttitudeSkillById(@PathVariable UUID id) {
        boolean res= attitudeSkillService.deleteAttitudeSkillById(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
