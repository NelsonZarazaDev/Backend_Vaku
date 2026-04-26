package com.Vaku.Vaku.apiRest.controller;

import com.Vaku.Vaku.apiRest.model.entity.PersonsEntity;
import com.Vaku.Vaku.apiRest.service.PersonsService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "persons")
@Validated
public class PersonsController {

    @Autowired
    private PersonsService personsService;

    @Operation(summary = "Create personal information for nurses, head nurse, parents, and children.")
    @PostMapping
    public ResponseEntity<List<PersonsEntity>> post(@RequestBody @Valid List<PersonsEntity> personsRequest) {
        return ResponseEntity.ok(personsService.createPersons(personsRequest));
    }

    @Operation(summary = "Find person by document and avoid duplicate registration")
    @GetMapping(path = "document/{document}")
    public ResponseEntity<PersonsEntity> findByDocument(@PathVariable String document) {
        return ResponseEntity.ok(personsService.findByDocument(document));
    }
}

