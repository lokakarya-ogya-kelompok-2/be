package ogya.lokakarya.be.controller.devplan;

import jakarta.validation.Valid;
import ogya.lokakarya.be.dto.devplan.DevPlanReq;
import ogya.lokakarya.be.dto.devplan.DevPlanDto;
import ogya.lokakarya.be.entity.DevPlan;
import ogya.lokakarya.be.service.DevPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RequestMapping("/dev-plans")
@RestController
public class DevPlanController {
    @Autowired
    private DevPlanService devPlanService;

    @PostMapping
    public ResponseEntity<DevPlan> create(@RequestBody @Valid DevPlanReq data) {
        var createdDevPlan= devPlanService.create(data);
        return new ResponseEntity<>(createdDevPlan, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<DevPlanDto>> getAllDivisions() {
        System.out.println("Get All DevPlan");
        List<DevPlanDto> response = devPlanService.getAllDevPlans();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<DevPlanDto> getDivisionById(@PathVariable UUID id) {
        DevPlanDto response = devPlanService.getDevPlanById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DevPlanDto> updateDivisionById
            (@PathVariable UUID id, @RequestBody @Valid DevPlanReq devPlanReq) {
        DevPlanDto res= devPlanService.updateDevPlanById(id, devPlanReq);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteDivisionById(@PathVariable UUID id) {
        boolean res= devPlanService.deleteDevPlanById(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
