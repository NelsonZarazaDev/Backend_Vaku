package com.Vaku.Vaku.apiRest.controller;

import com.Vaku.Vaku.apiRest.model.entity.VaccinesEntity;
import com.Vaku.Vaku.apiRest.model.response.EmployeesResponse;
import com.Vaku.Vaku.apiRest.model.response.VaccinesResponse;
import com.Vaku.Vaku.apiRest.service.VaccinesService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "vaccines")
public class VaccinesController {

    @Autowired
    private VaccinesService vaccinesService;

    @Operation(summary = "Bring up the vaccine information by id")
    @GetMapping(path = "{id}")
    public ResponseEntity<Set<VaccinesResponse>> getVaccines(@Valid @PathVariable Long id){
        return ResponseEntity.ok(vaccinesService.getVaccines(id));
    }

    @Operation(summary = "")
    @GetMapping
    public ResponseEntity<List<VaccinesEntity>> findAll(){
        return ResponseEntity.ok(vaccinesService.findByAllVacinnes());
    }
}
