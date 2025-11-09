package com.common.lib.api.controller;

import com.common.lib.utils.PlantillaResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Interfaz CRUD reactiva generica.
 * @param <RES> DTO de respuesta
 * @param <RQ> DTO de request
 */
public interface CrudController<RES, RQ> {

    @PostMapping("/add")
    Mono<ResponseEntity<PlantillaResponse<RES>>> add(
        @RequestBody RQ request,
        @RequestHeader HttpHeaders headers
    );

    @GetMapping("/all")
    Mono<ResponseEntity<PlantillaResponse<RES>>> all(
        @RequestHeader HttpHeaders headers,
        @RequestParam(required = false) Map<String, String> filters,
        @RequestParam(required = false) Object id
    );

    @PutMapping("/update")
    Mono<ResponseEntity<PlantillaResponse<RES>>> update(
        @RequestBody RQ request,
        @RequestHeader HttpHeaders headers,
        @RequestParam Object id
    );

    @DeleteMapping("/delete")
    Mono<ResponseEntity<PlantillaResponse<RES>>> delete(
        @RequestHeader HttpHeaders headers,
        @RequestParam(required = false) Object id
    );
}
