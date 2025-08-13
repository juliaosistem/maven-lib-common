package com.common.lib.api.controller;

import com.common.lib.utils.PlantillaResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Interfaz CRUD equivalente a la usada en Nest, para controladores.
 * @param <RES> DTO de respuesta
 * @param <RQ> DTO de request
 */
public interface CrudController<RES, RQ> {

    @PostMapping("/add")
    ResponseEntity<PlantillaResponse<RES>> add(
        @RequestBody RQ request,
        @RequestHeader(value = "id", required = false) String id,
        @RequestHeader("ip") String ip,
        @RequestHeader("dominio") String dominio,
        @RequestHeader("usuario") String usuario,
        @RequestHeader(value = "idbusiness", required = false) Long idbusiness,
        @RequestHeader("proceso") String proceso,
        @RequestHeader("topic") String topic,
        @RequestHeader(value = "token", required = false) String token
    );

    @GetMapping("/all")
    ResponseEntity<PlantillaResponse<RES>> all(
        @RequestHeader(value = "id", required = false) String id,
        @RequestHeader("ip") String ip,
        @RequestHeader("dominio") String dominio,
        @RequestHeader("usuario") String usuario,
        @RequestHeader(value = "idbusiness", required = false) Long idbusiness,
        @RequestHeader("proceso") String proceso,
        @RequestHeader("topic") String topic,
        @RequestHeader(value = "token", required = false) String token,
        @RequestParam(required = false) Map<String, String> filters
    );

    @PutMapping("/update")
    Mono<PlantillaResponse<RES>> update(
        @RequestBody RQ request,
        @RequestHeader(value = "id", required = false) String id,
        @RequestHeader("ip") String ip,
        @RequestHeader("dominio") String dominio,
        @RequestHeader("usuario") String usuario,
        @RequestHeader(value = "idbusiness", required = false) Long idbusiness,
        @RequestHeader("proceso") String proceso,
        @RequestHeader("topic") String topic,
        @RequestHeader(value = "token", required = false) String token
    );

    @DeleteMapping("/delete/{id}")
    ResponseEntity<PlantillaResponse<RES>> delete(
        @PathVariable String id,
        @RequestHeader("ip") String ip,
        @RequestHeader("dominio") String dominio,
        @RequestHeader("usuario") String usuario,
        @RequestHeader(value = "idbusiness", required = false) Long idbusiness,
        @RequestHeader("proceso") String proceso,
        @RequestHeader("topic") String topic,
        @RequestHeader(value = "token", required = false) String token
    );
}


