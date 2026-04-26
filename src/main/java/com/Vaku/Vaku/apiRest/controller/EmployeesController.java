package com.Vaku.Vaku.apiRest.controller;

import com.Vaku.Vaku.apiRest.model.entity.PersonsEntity;
import com.Vaku.Vaku.apiRest.model.response.EmployeesResponse;
import com.Vaku.Vaku.apiRest.service.EmployessService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(path = "employee")
@Validated
public class EmployeesController {

    @Autowired
    private EmployessService employessService;

    @Operation(summary = "Update employee's personal information by token")
    @PutMapping(path = "{token}")
    public ResponseEntity<PersonsEntity> put(@Valid @RequestBody PersonsEntity personRequest, @PathVariable String token, @RequestParam boolean state){
        return ResponseEntity.ok(employessService.updateEmployees(personRequest,token,state));
    }

    @Operation(summary = "Retrieve employee's personal information by email")
    @GetMapping(path = "{email}")
    public ResponseEntity<Set<EmployeesResponse>> findEmployeesByEmail(@PathVariable String email){
        return ResponseEntity.ok(employessService.findByJsonEmployeeEmail(email));
    }

    @Operation(summary = "List all employees registered in the system")
    @GetMapping
    public ResponseEntity<Page<EmployeesResponse>> findByAllEmployee(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size
    ){
        int normalizedPage = Math.max(page, 0);
        int normalizedSize = Math.min(Math.max(size, 1), 200);
        return ResponseEntity.ok(employessService.findByAllEmployee(normalizedPage, normalizedSize));
    }
}
