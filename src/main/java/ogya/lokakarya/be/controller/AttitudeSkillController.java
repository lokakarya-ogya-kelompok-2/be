package ogya.lokakarya.be.controller;

import jakarta.validation.Valid;
import ogya.lokakarya.be.dto.attitudeskill.AttitudeSkillDto;
import ogya.lokakarya.be.dto.attitudeskill.AttitudeSkillReq;
import ogya.lokakarya.be.service.AttitudeSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequestMapping("/attitude-skills")
@RestController
public class AttitudeSkillController {
    @Autowired
    AttitudeSkillService attitudeSkillService;

    @PostMapping
    public ResponseEntity<AttitudeSkillDto> create(@RequestBody @Valid AttitudeSkillReq data) {
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
            (@PathVariable UUID id, @RequestBody @Valid AttitudeSkillReq attitudeSkillReq) {
        AttitudeSkillDto res= attitudeSkillService.updateAttitudeSkillById(id, attitudeSkillReq);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteAttitudeSkillById(@PathVariable UUID id) {
        boolean res= attitudeSkillService.deleteAttitudeSkillById(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
