package com.Vaku.Vaku.apiRest.controller;

import com.Vaku.Vaku.apiRest.model.entity.VaccinesAppliedEntity;
import com.Vaku.Vaku.apiRest.service.VaccinesAppliedService;
import com.Vaku.Vaku.dto.VaccineAppliedRequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "vaccineApplied")
public class VaccinesAppliedController {

    @Autowired
    private VaccinesAppliedService vaccinesAppliedService;

    @Operation(summary = "Create the vaccine applied to the child using request body payload")
    @PostMapping
    public ResponseEntity<VaccinesAppliedEntity> createVaccinesAppliedEntity(
            @Valid @RequestBody VaccineAppliedRequest request
    ) {
        return ResponseEntity.ok(vaccinesAppliedService.createVaccinesAppliedEntity(
                request.getVaccinesApplied(),
                request.getEmailFather()
        ));
    }

    @Operation(summary = "Create the vaccine applied to the child")
    @PostMapping(path = "{emailFather}")
    public ResponseEntity<VaccinesAppliedEntity> createVaccinesAppliedEntity(@Valid @RequestBody VaccinesAppliedEntity vaccinesApplied, @PathVariable String emailFather){
        return ResponseEntity.ok(vaccinesAppliedService.createVaccinesAppliedEntity(vaccinesApplied, emailFather));
    }
}
