package ogya.lokakarya.be.controller;

import jakarta.validation.Valid;
import ogya.lokakarya.be.dto.empachievementskill.EmpAchievementSkillDto;
import ogya.lokakarya.be.dto.empachievementskill.EmpAchievementSkillReq;
import ogya.lokakarya.be.entity.EmpAchievementSkill;
import ogya.lokakarya.be.service.AchievementSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RequestMapping("/emp-achievement-skills")
@RestController
public class EmpAchievementSkillController {
    @Autowired
    AchievementSkillService achievementSkillService;

    @PostMapping
    public ResponseEntity<EmpAchievementSkill> create(@RequestBody @Valid EmpAchievementSkillReq data) {
        var createdAchievementSkill= achievementSkillService.create(data);
        return new ResponseEntity<>(createdAchievementSkill, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<EmpAchievementSkillDto>> getAllAchievementSkills() {
        System.out.println("Get All Achievement Skill");
        List<EmpAchievementSkillDto> response = achievementSkillService.getAllAchievementSkills();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<EmpAchievementSkillDto> getAchievementSkillById(@PathVariable UUID id) {
        EmpAchievementSkillDto response = achievementSkillService.getAchievementSkillById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpAchievementSkillDto> updateAchievementSkillById
            (@PathVariable UUID id, @RequestBody @Valid EmpAchievementSkillReq empAchievementSkillReq) {
        EmpAchievementSkillDto res= achievementSkillService.updateAchievementSkillById(id, empAchievementSkillReq);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteAchievementSkillById(@PathVariable UUID id) {
        boolean res= achievementSkillService.deleteAchievementSkillById(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
