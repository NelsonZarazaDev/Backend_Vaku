package com.Vaku.Vaku.apiRest.controller;

import com.Vaku.Vaku.apiRest.model.entity.ChildrensEntity;
import com.Vaku.Vaku.apiRest.model.entity.PersonsEntity;
import com.Vaku.Vaku.apiRest.model.response.ChildrensResponse;
import com.Vaku.Vaku.apiRest.service.ChildrensService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(path = "children")
public class ChildrensController {

    @Autowired
    private ChildrensService childrensService;

    @Operation(summary = "Update the child's personal information per token")
    @PutMapping(path = "{token}")
    public ResponseEntity<PersonsEntity> put(@Valid @RequestBody PersonsEntity personRequest, @PathVariable String token){
        return ResponseEntity.ok(childrensService.updateChildren(personRequest,token));
    }
}
