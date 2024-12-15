package ogya.lokakarya.be.controller;

import java.util.List;
import java.util.UUID;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import ogya.lokakarya.be.dto.ResponseDto;
import ogya.lokakarya.be.dto.technicalskill.TechnicalSkillDto;
import ogya.lokakarya.be.dto.technicalskill.TechnicalSkillFilter;
import ogya.lokakarya.be.dto.technicalskill.TechnicalSkillReq;
import ogya.lokakarya.be.service.TechnicalSkillService;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/technical-skills")
public class TechnicalSkillController {
    @Autowired
    private TechnicalSkillService technicalSkillService;

    @PostMapping
    public ResponseEntity<ResponseDto<TechnicalSkillDto>> create(
            @RequestBody @Valid TechnicalSkillReq data) {
        var createdTechnicalSkill = technicalSkillService.create(data);
        return ResponseDto.<TechnicalSkillDto>builder().content(createdTechnicalSkill)
                .message("Create Technical Skill Succesful").success(true).build()
                .toResponse(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<TechnicalSkillDto>>> getAllTechnicalSkills(
            @RequestParam(name = "name_contains", required = false) String nameContains,
            @RequestParam(name = "enabled_only", required = false,
                    defaultValue = "false") Boolean enabledOnly,
            @RequestParam(name = "with_created_by", required = false,
                    defaultValue = "false") Boolean withCreatedBy,
            @RequestParam(name = "with_updated_by", required = false,
                    defaultValue = "false") Boolean withUpdatedBy) {
        System.out.println("Get All Technical Skill");
        TechnicalSkillFilter filter = new TechnicalSkillFilter();
        filter.setNameContains(nameContains);
        filter.setEnabledOnly(enabledOnly);
        filter.setWithCreatedBy(withCreatedBy);
        filter.setWithUpdatedBy(withUpdatedBy);
        List<TechnicalSkillDto> response = technicalSkillService.getAlltechnicalSkills(filter);
        return ResponseDto.<List<TechnicalSkillDto>>builder().content(response)
                .message("Get All Technical Skill successful!").success(true).build()
                .toResponse(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<TechnicalSkillDto>> getTechnicalSkillById(
            @PathVariable UUID id) {
        TechnicalSkillDto response = technicalSkillService.gettechnicalSkillById(id);
        return ResponseDto.<TechnicalSkillDto>builder().content(response)
                .message(String.format("Get Technical Skill with id %s successful!", id))
                .success(true).build().toResponse(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<TechnicalSkillDto>> updatetechnicalSkillById(
            @PathVariable UUID id, @RequestBody @Valid TechnicalSkillReq technicalSkillReq) {
        TechnicalSkillDto res =
                technicalSkillService.updateTechnicalSkillById(id, technicalSkillReq);
        return ResponseDto.<TechnicalSkillDto>builder().content(res)
                .message(String.format("Update user with id %s successful!", id)).success(true)
                .build().toResponse(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteTechnicalSkillById(@PathVariable UUID id) {
        technicalSkillService.deleteTechnicalSkillById(id);
        return ResponseDto.<Void>builder().success(true)
                .message(String.format("Delete technical skill with id %s successful!", id)).build()
                .toResponse(HttpStatus.OK);
    }
}
