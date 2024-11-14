package ogya.lokakarya.be.controller.division;

import jakarta.validation.Valid;
import ogya.lokakarya.be.dto.division.CreateDivision;
import ogya.lokakarya.be.dto.division.DivisionDto;
import ogya.lokakarya.be.entity.Division;
import ogya.lokakarya.be.service.DivisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RequestMapping("/division")
@RestController
public class DivisionController {
    @Autowired
    DivisionService divisionService;

    @PostMapping
    public ResponseEntity<Division> create(@RequestBody @Valid CreateDivision data) {
        var createdDivision= divisionService.create(data);
        return new ResponseEntity<>(createdDivision, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<DivisionDto>> getAllDivisions() {
        System.out.println("Get All Division");
        List<DivisionDto> response = divisionService.getAllDivisions();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<DivisionDto> getDivisionById(@PathVariable UUID id) {
        DivisionDto response = divisionService.getDivisionById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DivisionDto> updateDivisionById
            (@PathVariable UUID id, @RequestBody @Valid CreateDivision createDivision) {
        DivisionDto res= divisionService.updateDivisionById(id, createDivision);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteDivisionById(@PathVariable UUID id) {
        boolean res= divisionService.deleteDivisionById(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
