package com.Vaku.Vaku.apiRest.controller;

import com.Vaku.Vaku.apiRest.model.entity.InventoriesEntity;
import com.Vaku.Vaku.apiRest.model.response.InventoriesResponse;
import com.Vaku.Vaku.apiRest.service.InventoriesService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(path = "inventori")
@Validated
public class InventoriesController {

    @Autowired
    private InventoriesService inventoriesService;

    @Operation(summary = "Obtain information on inventory vaccinations by token and send the employee id to be associated with it")
    @PutMapping(path = "{token}/{emplId}")
    public ResponseEntity <InventoriesEntity> updateInventarie(@Valid @RequestBody InventoriesEntity inventoriesRequest, @PathVariable String token, @PathVariable Long emplId){
        return ResponseEntity.ok(inventoriesService.updateInventarie(inventoriesRequest,token,emplId));
    }

    @Operation(summary = "Update vaccine information in the inventory per inventory token")
    @GetMapping(path = "{token}")
    public ResponseEntity<Set<InventoriesResponse>> getInventoriByToken(@PathVariable String token){
        return ResponseEntity.ok(inventoriesService.getInventoriByToken(token));
    }

    @Operation(summary = "List all the vaccines in the inventory")
    @GetMapping
    public ResponseEntity<Set<InventoriesResponse>> getInventoriAll(){
        return ResponseEntity.ok(inventoriesService.getInventoriAll());
    }
}
