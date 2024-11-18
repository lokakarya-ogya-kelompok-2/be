package ogya.lokakarya.be.controller;

import jakarta.validation.Valid;
import ogya.lokakarya.be.dto.division.DivisionDto;
import ogya.lokakarya.be.dto.division.DivisionReq;
import ogya.lokakarya.be.dto.emptechnicalskill.EmpTechnicalSkillDto;
import ogya.lokakarya.be.dto.emptechnicalskill.EmpTechnicalSkillReq;
import ogya.lokakarya.be.entity.Division;
import ogya.lokakarya.be.entity.EmpTechnicalSkill;
import ogya.lokakarya.be.service.EmpTechnicalSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/emp-technical-skills")
@RestController
public class EmpTechnicalSkillController {
    @Autowired
    private EmpTechnicalSkillService empTechnicalSkillService;

    @PostMapping
    public ResponseEntity<EmpTechnicalSkill> create(@RequestBody @Valid EmpTechnicalSkillReq data) {
        var createdEmpTechnicalSkill= empTechnicalSkillService.create(data);
        return new ResponseEntity<>(createdEmpTechnicalSkill, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<EmpTechnicalSkillDto>> getAllEmpTechnicalSkills() {
        System.out.println("Get All Emp Technical Skill");
        List<EmpTechnicalSkillDto> response = empTechnicalSkillService.getAllEmpTechnicalSkills();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<EmpTechnicalSkillDto> getEmpTechnicalSkillById(@PathVariable UUID id) {
        EmpTechnicalSkillDto response = empTechnicalSkillService.getEmpTechnicalSkillById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpTechnicalSkillDto> updateEmpTechnicalSkillById
            (@PathVariable UUID id, @RequestBody @Valid EmpTechnicalSkillReq empTechnicalSkillReq) {
        EmpTechnicalSkillDto res= empTechnicalSkillService.updateEmpTechnicalSkillById(id, empTechnicalSkillReq);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteEmpTechnicalSkillById(@PathVariable UUID id) {
        boolean res= empTechnicalSkillService.deleteEmpTechnicalSkillById(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
