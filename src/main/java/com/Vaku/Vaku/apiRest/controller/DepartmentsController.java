package com.Vaku.Vaku.apiRest.controller;

import com.Vaku.Vaku.apiRest.model.entity.DepartmentsEntity;
import com.Vaku.Vaku.apiRest.service.DepartmentsService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "departments")
public class DepartmentsController {

    @Autowired
    private DepartmentsService departmentsService;

    @Operation(summary = "List all departments")
    @GetMapping
    public ResponseEntity<List<DepartmentsEntity>> findAllDepartments(){
        return ResponseEntity.ok(departmentsService.findAllDepartments());
    }
}
