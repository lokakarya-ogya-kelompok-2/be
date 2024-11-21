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
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import ogya.lokakarya.be.dto.ResponseDto;
import ogya.lokakarya.be.dto.division.DivisionDto;
import ogya.lokakarya.be.dto.division.DivisionReq;
import ogya.lokakarya.be.service.DivisionService;

@RequestMapping("/divisions")
@RestController
public class DivisionController {
    @Autowired
    DivisionService divisionService;

    @PostMapping
    public ResponseEntity<ResponseDto<DivisionDto>> create(@RequestBody @Valid DivisionReq data) {
        var createdDivision = divisionService.create(data);
        return ResponseDto.<DivisionDto>builder().success(true).content(createdDivision)
                .message("Create division successful!").build().toResponse(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<DivisionDto>>> getAllDivisions() {
        List<DivisionDto> divisions = divisionService.getAllDivisions();
        return ResponseDto.<List<DivisionDto>>builder().success(true).content(divisions)
                .message("List all division successful!").build().toResponse(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<DivisionDto>> getDivisionById(@PathVariable UUID id) {
        DivisionDto division = divisionService.getDivisionById(id);
        return ResponseDto.<DivisionDto>builder().success(true).content(division)
                .message(String.format("Get division with id %s successful!", id)).build()
                .toResponse(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<DivisionDto>> updateDivisionById(@PathVariable UUID id,
            @RequestBody @Valid DivisionReq divisionReq) {
        DivisionDto updatedDivision = divisionService.updateDivisionById(id, divisionReq);
        return ResponseDto.<DivisionDto>builder().success(true).content(updatedDivision)
                .message(String.format("Update division with id %s successful!", id)).build()
                .toResponse(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteDivisionById(@PathVariable UUID id) {
        divisionService.deleteDivisionById(id);
        return ResponseDto.<Void>builder().success(true)
                .message(String.format("Delete division with id %s successful!", id)).build()
                .toResponse(HttpStatus.OK);
    }
}
