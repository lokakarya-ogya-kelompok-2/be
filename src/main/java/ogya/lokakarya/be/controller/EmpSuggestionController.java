package ogya.lokakarya.be.controller;

import jakarta.validation.Valid;
import ogya.lokakarya.be.dto.ResponseDto;
import ogya.lokakarya.be.dto.empsuggestion.EmpSuggestionDto;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequestMapping("/emp-suggestions")
@RestController
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
    @GetMapping
    public ResponseEntity<ResponseDto<List<EmpSuggestionDto>>> getAllEmpSuggestions() {
        System.out.println("Get All Emp Suggestion");
        List<EmpSuggestionDto> response = empSuggestionService.getAllEmpSuggestions();
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
