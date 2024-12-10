package ogya.lokakarya.be.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import ogya.lokakarya.be.dto.ResponseDto;
import ogya.lokakarya.be.dto.empsuggestion.EmpSuggestionDto;
import ogya.lokakarya.be.dto.empsuggestion.EmpSuggestionFilter;
import ogya.lokakarya.be.dto.empsuggestion.EmpSuggestionReq;
import ogya.lokakarya.be.service.EmpSuggestionService;
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

import java.util.List;
import java.util.UUID;

@RequestMapping("/emp-suggestions")
@RestController
@SecurityRequirement(name = "bearerAuth")
public class EmpSuggestionController {
    @Autowired
    private EmpSuggestionService empSuggestionService;

    @PostMapping
    public ResponseEntity<ResponseDto<EmpSuggestionDto>> create(@RequestBody @Valid EmpSuggestionReq data) {
        var createdEmpSuggestion= empSuggestionService.create(data);
        return ResponseDto.<EmpSuggestionDto>builder().content(createdEmpSuggestion)
                .message("Create emp suggestion successful!").success(true).build()
                .toResponse(HttpStatus.CREATED);
    }
    @PostMapping("/bulk-create")
    public ResponseEntity<ResponseDto<List<EmpSuggestionDto>>> createBulk(
            @RequestBody @Valid List<EmpSuggestionReq> data) {
        System.out.println("Data: " + data);
        var createdEmpSuggestion = empSuggestionService.createBulk(data);
        return ResponseDto.<List<EmpSuggestionDto>>builder().success(true)
                .message("Create all emp suggestions successful!")
                .content(createdEmpSuggestion).build().toResponse(HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<ResponseDto<List<EmpSuggestionDto>>> getAllEmpSuggestions(
            @RequestParam(name = "user_ids", required = false) List<UUID> userIds,
            @RequestParam(required = false) List<Integer> years,
            @RequestParam(name = "with_created_by", required = false,
                    defaultValue = "false") Boolean withCreatedBy,
            @RequestParam(name = "with_updated_by", required = false,
                    defaultValue = "false") Boolean withUpdatedBy
    ) {
        System.out.println(userIds+"Aaaaaaaaaaaaaaaaaaaaaaaaa");
        System.out.println("Get All Achievement Skill");
        EmpSuggestionFilter filter = new EmpSuggestionFilter();
        filter.setUserIds(userIds);
        filter.setYears(years);
        filter.setWithCreatedBy(withCreatedBy);
        filter.setWithUpdatedBy(withUpdatedBy);
        List<EmpSuggestionDto> response = empSuggestionService.getAllEmpSuggestions(filter);
        return ResponseDto.<List<EmpSuggestionDto>>builder().content(response)
                .message("Get all emp suggestions successful!").success(true).build()
                .toResponse(HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<EmpSuggestionDto>> getEmpSuggestionById(@PathVariable UUID id) {
        EmpSuggestionDto response = empSuggestionService.getEmpSuggestionById(id);
        return ResponseDto.<EmpSuggestionDto>builder().content(response)
                .message(String.format("Get emp suggestion with id %s successful!", id)).success(true).build()
                .toResponse(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<EmpSuggestionDto>> updateEmpSuggestionById
            (@PathVariable UUID id, @RequestBody @Valid EmpSuggestionReq empSuggestionReq) {
        EmpSuggestionDto res= empSuggestionService.updateEmpSuggestionById(id, empSuggestionReq);
        return ResponseDto.<EmpSuggestionDto>builder().content(res)
                .message(String.format("Update emp suggestion with id %s successful!", id)).success(true)
                .build().toResponse(HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteEmpSuggestionById(@PathVariable UUID id) {
        boolean res= empSuggestionService.deleteEmpSuggestionById(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
