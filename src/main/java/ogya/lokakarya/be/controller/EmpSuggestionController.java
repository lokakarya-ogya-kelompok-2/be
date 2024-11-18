package ogya.lokakarya.be.controller;

import jakarta.validation.Valid;
import ogya.lokakarya.be.dto.empsuggestion.EmpSuggestionReq;
import ogya.lokakarya.be.dto.empsuggestion.EmpSuggestionDto;
import ogya.lokakarya.be.entity.EmpSuggestion;
import ogya.lokakarya.be.service.EmpSuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/emp-suggestions")
@RestController
public class EmpSuggestionController {
    @Autowired
    private EmpSuggestionService empSuggestionService;

    @PostMapping
    public ResponseEntity<EmpSuggestion> create(@RequestBody @Valid EmpSuggestionReq data) {
        var createdEmpSuggestion= empSuggestionService.create(data);
        return new ResponseEntity<>(createdEmpSuggestion, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<EmpSuggestionDto>> getAllEmpSuggestions() {
        System.out.println("Get All Emp Suggestion");
        List<EmpSuggestionDto> response = empSuggestionService.getAllEmpSuggestions();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<EmpSuggestionDto> getEmpSuggestionById(@PathVariable UUID id) {
        EmpSuggestionDto response = empSuggestionService.getEmpSuggestionById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpSuggestionDto> updateEmpSuggestionById
            (@PathVariable UUID id, @RequestBody @Valid EmpSuggestionReq empSuggestionReq) {
        EmpSuggestionDto res= empSuggestionService.updateEmpSuggestionById(id, empSuggestionReq);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteEmpSuggestionById(@PathVariable UUID id) {
        boolean res= empSuggestionService.deleteEmpSuggestionById(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
