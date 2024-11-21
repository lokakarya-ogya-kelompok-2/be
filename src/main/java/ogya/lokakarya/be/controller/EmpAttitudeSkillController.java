package ogya.lokakarya.be.controller;

import jakarta.validation.Valid;
import ogya.lokakarya.be.dto.ResponseDto;
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
    public ResponseEntity<ResponseDto<EmpAttitudeSkillDto>> create(@RequestBody @Valid EmpAttitudeSkillReq data) {
        var createdEmpAttitudeSkill= empAttitudeSkillService.create(data);
        return ResponseDto.<EmpAttitudeSkillDto>builder().content(createdEmpAttitudeSkill)
                .message("Create emp attitude skill successful!").success(true).build()
                .toResponse(HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<ResponseDto<List<EmpAttitudeSkillDto>>> getAllEmpAttitudeSkills() {
        System.out.println("Get All Emp Attitude Skill");
        List<EmpAttitudeSkillDto> response = empAttitudeSkillService.getAllEmpAttitudeSkills();
        return ResponseDto.<List<EmpAttitudeSkillDto>>builder().content(response)
                .message("Get emp attitude skills successful!").success(true).build()
                .toResponse(HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<EmpAttitudeSkillDto>> getEmpAttitudeSkillById(@PathVariable UUID id) {
        EmpAttitudeSkillDto response = empAttitudeSkillService.getEmpAttitudeSkillById(id);
        return ResponseDto.<EmpAttitudeSkillDto>builder().content(response)
                .message(String.format("Get emp attitude skill with id %s successful!", id)).success(true).build()
                .toResponse(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<EmpAttitudeSkillDto>> updateEmpAttitudeSkillById
            (@PathVariable UUID id, @RequestBody @Valid EmpAttitudeSkillReq empAttitudeSkillReq) {
        EmpAttitudeSkillDto res= empAttitudeSkillService.updateEmpAttitudeSkillById(id, empAttitudeSkillReq);
        return ResponseDto.<EmpAttitudeSkillDto>builder().content(res)
                .message(String.format("Update emp attitude skill with id %s successful!", id)).success(true)
                .build().toResponse(HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteEmpAttitudeSkillById(@PathVariable UUID id) {
        boolean res= empAttitudeSkillService.deleteEmpAttitudeSkillById(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
