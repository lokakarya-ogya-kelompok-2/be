package ogya.lokakarya.be.controller;

import jakarta.validation.Valid;
import ogya.lokakarya.be.dto.technicalskill.TechnicalSkillDto;
import ogya.lokakarya.be.dto.technicalskill.TechnicalSkillReq;
import ogya.lokakarya.be.service.TechnicalSkillService;
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

@RequestMapping("/technical-skills")
@RestController
public class TechnicalSkillController {
    @Autowired
    private TechnicalSkillService technicalSkillService;

    @PostMapping
    public ResponseEntity<TechnicalSkillDto> create(@RequestBody @Valid TechnicalSkillReq data) {
        var createdDivision= technicalSkillService.create(data);
        return new ResponseEntity<>(createdDivision, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<TechnicalSkillDto>> getAllTechnicalSkills() {
        System.out.println("Get All Technical Skill");
        List<TechnicalSkillDto> response = technicalSkillService.getAlltechnicalSkills();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<TechnicalSkillDto> getTechnicalSkillById(@PathVariable UUID id) {
        TechnicalSkillDto response = technicalSkillService.gettechnicalSkillById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TechnicalSkillDto> updatetechnicalSkillById
            (@PathVariable UUID id, @RequestBody @Valid TechnicalSkillReq technicalSkillReq) {
        TechnicalSkillDto res= technicalSkillService.updateTechnicalSkillById(id, technicalSkillReq);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteTechnicalSkillById(@PathVariable UUID id) {
        boolean res= technicalSkillService.deleteTechnicalSkillById(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
