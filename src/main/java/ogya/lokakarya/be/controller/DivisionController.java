package ogya.lokakarya.be.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
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
import lombok.extern.slf4j.Slf4j;
import ogya.lokakarya.be.dto.FilterInfo;
import ogya.lokakarya.be.dto.PageInfo;
import ogya.lokakarya.be.dto.ResponseDto;
import ogya.lokakarya.be.dto.division.DivisionDto;
import ogya.lokakarya.be.dto.division.DivisionFilter;
import ogya.lokakarya.be.dto.division.DivisionReq;
import ogya.lokakarya.be.service.DivisionService;

@Slf4j
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/divisions")
public class DivisionController {
        @Autowired
        DivisionService divisionService;

        @PostMapping
        public ResponseEntity<ResponseDto<DivisionDto>> create(
                        @RequestBody @Valid DivisionReq data) {
                log.info("Starting DivisionController.create");
                var createdDivision = divisionService.create(data);
                log.info("Ending DivisionController.create");
                return ResponseDto.<DivisionDto>builder().success(true).content(createdDivision)
                                .message("Create division successful!").build()
                                .toResponse(HttpStatus.CREATED);
        }

        @GetMapping
        public ResponseEntity<ResponseDto<List<DivisionDto>>> getAllDivisions(
                        @RequestParam(name = "name_contains", required = false) String nameContains,
                        @RequestParam(name = "page_number", required = false) Integer pageNumber,
                        @RequestParam(name = "page_size", required = false,
                                        defaultValue = "5") Integer pageSize,
                        @RequestParam(name = "with_created_by", required = false,
                                        defaultValue = "false") Boolean withCreatedBy,
                        @RequestParam(name = "with_updated_by", required = false,
                                        defaultValue = "false") Boolean withUpdatedBy,
                        @RequestParam(name = "sort_field", required = false,
                                        defaultValue = "createdAt") String sortField,
                        @RequestParam(name = "sort_direction", required = false,
                                        defaultValue = "DESC") Sort.Direction sortDirection) {
                log.info("Starting DivisionController.list");
                DivisionFilter filter = new DivisionFilter();
                filter.setPageNumber(pageNumber);
                filter.setPageSize(pageSize);
                filter.setNameContains(nameContains);
                filter.setWithCreatedBy(withCreatedBy);
                filter.setWithUpdatedBy(withUpdatedBy);
                filter.setSortField(sortField);
                filter.setSortDirection(sortDirection);
                Page<DivisionDto> divisions = divisionService.getAllDivisions(filter);
                log.info("Ending DivisionController.list");
                return ResponseDto.<List<DivisionDto>>builder().success(true)
                                .content(divisions.toList())
                                .pageInfo(new PageInfo(divisions.getNumber() + 1,
                                                divisions.getSize(), divisions.getTotalPages(),
                                                divisions.getTotalElements()))
                                .filterInfo(new FilterInfo("id", "name", "createdAt", "createdBy",
                                                "updatedAt", "updatedBy"))
                                .message("List all division successful!").build()
                                .toResponse(HttpStatus.OK);
        }

        @GetMapping("/{id}")
        public ResponseEntity<ResponseDto<DivisionDto>> getDivisionById(@PathVariable UUID id) {
                log.info("Starting DivisionController.get for id = {}", id);
                DivisionDto division = divisionService.getDivisionById(id);
                log.info("Ending DivisionController.get for id = {}", id);
                return ResponseDto.<DivisionDto>builder().success(true).content(division)
                                .message(String.format("Get division with id %s successful!", id))
                                .build().toResponse(HttpStatus.OK);
        }

        @PutMapping("/{id}")
        public ResponseEntity<ResponseDto<DivisionDto>> updateDivisionById(@PathVariable UUID id,
                        @RequestBody @Valid DivisionReq divisionReq) {
                log.info("Starting DivisionController.update for id = {}", id);
                DivisionDto updatedDivision = divisionService.updateDivisionById(id, divisionReq);
                log.info("Ending DivisionController.update for id = {}", id);
                return ResponseDto.<DivisionDto>builder().success(true).content(updatedDivision)
                                .message(String.format("Update division with id %s successful!",
                                                id))
                                .build().toResponse(HttpStatus.OK);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<ResponseDto<Void>> deleteDivisionById(@PathVariable UUID id) {
                log.info("Starting DivisionController.delete for id = {}", id);
                divisionService.deleteDivisionById(id);
                log.info("Ending DivisionController.delete for id = {}", id);
                return ResponseDto.<Void>builder().success(true).message(
                                String.format("Delete division with id %s successful!", id)).build()
                                .toResponse(HttpStatus.OK);
        }
}
