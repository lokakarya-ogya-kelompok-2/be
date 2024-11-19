package ogya.lokakarya.be.controller;

import jakarta.validation.Valid;
import ogya.lokakarya.be.dto.empattitudeskill.EmpAttitudeSkillDto;
import ogya.lokakarya.be.dto.empattitudeskill.EmpAttitudeSkillReq;
import ogya.lokakarya.be.service.EmpAttitudeSkillService;
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

@RequestMapping("/emp-attitude-skills")
@RestController
public class EmpAttitudeSkillController {
    @Autowired
    EmpAttitudeSkillService empAttitudeSkillService;

    @PostMapping
    public ResponseEntity<EmpAttitudeSkillDto> create(@RequestBody @Valid EmpAttitudeSkillReq data) {
        var createdEmpAttitudeSkill= empAttitudeSkillService.create(data);
        return new ResponseEntity<>(createdEmpAttitudeSkill, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<EmpAttitudeSkillDto>> getAllEmpAttitudeSkills() {
        System.out.println("Get All Emp Attitude Skill");
        List<EmpAttitudeSkillDto> response = empAttitudeSkillService.getAllEmpAttitudeSkills();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<EmpAttitudeSkillDto> getEmpAttitudeSkillById(@PathVariable UUID id) {
        EmpAttitudeSkillDto response = empAttitudeSkillService.getEmpAttitudeSkillById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpAttitudeSkillDto> updateEmpAttitudeSkillById
            (@PathVariable UUID id, @RequestBody @Valid EmpAttitudeSkillReq empAttitudeSkillReq) {
        EmpAttitudeSkillDto res= empAttitudeSkillService.updateEmpAttitudeSkillById(id, empAttitudeSkillReq);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteEmpAttitudeSkillById(@PathVariable UUID id) {
        boolean res= empAttitudeSkillService.deleteEmpAttitudeSkillById(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
