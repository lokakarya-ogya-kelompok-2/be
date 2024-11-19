package ogya.lokakarya.be.controller;

import jakarta.validation.Valid;
import ogya.lokakarya.be.dto.empdevplan.EmpDevPlanDto;
import ogya.lokakarya.be.dto.empdevplan.EmpDevPlanReq;
import ogya.lokakarya.be.service.EmpDevPlanService;
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

@RequestMapping("/emp-dev-plans")
@RestController
public class EmpDevPlanController {
    @Autowired
    private EmpDevPlanService empDevPlanService;

    @PostMapping
    public ResponseEntity<EmpDevPlanDto> create(@RequestBody @Valid EmpDevPlanReq data) {
        var createdEmpDevPlan= empDevPlanService.create(data);
        return new ResponseEntity<>(createdEmpDevPlan, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<EmpDevPlanDto>> getAllEmpDevPlans() {
        System.out.println("Get All Emp Dev Plan");
        List<EmpDevPlanDto> response = empDevPlanService.getAllEmpDevPlans();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<EmpDevPlanDto> getEmpDevPlanById(@PathVariable UUID id) {
        EmpDevPlanDto response = empDevPlanService.getEmpDevPlanById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpDevPlanDto> updateEmpDevPlanById
            (@PathVariable UUID id, @RequestBody @Valid EmpDevPlanReq empDevPlanReq) {
        EmpDevPlanDto res= empDevPlanService.updateEmpDevPlanById(id, empDevPlanReq);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteEmpDevPlanById(@PathVariable UUID id) {
        boolean res= empDevPlanService.deleteEmpDevPlanById(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}