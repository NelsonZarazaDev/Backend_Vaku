package com.Vaku.Vaku.apiRest.controller;

import com.Vaku.Vaku.apiRest.model.response.CitysResponse;
import com.Vaku.Vaku.apiRest.service.CitysService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping(path = "citys")
public class CitysController {

    @Autowired
    private CitysService citysService;

    @Operation(summary = "List city for department")
    @GetMapping(path = "{id}")
    public ResponseEntity<Set<CitysResponse>> findByCityDepaId(@PathVariable String id) {
        return ResponseEntity.ok(citysService.findByCityDepaId(id));
    }
}
