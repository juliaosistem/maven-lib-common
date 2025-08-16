package com.common.lib.api.controller;

import com.common.lib.utils.PlantillaResponse;
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
    Mono<PlantillaResponse<RES>> add(
        @RequestBody RQ request,
        @RequestHeader(value = "id", required = false) String id,
        @RequestHeader("ip") String ip,
        @RequestHeader("dominio") String dominio,
        @RequestHeader("usuario") String usuario,
        @RequestHeader(value = "idbusiness", required = false) Integer idbusiness,
        @RequestHeader("proceso") String proceso,
        @RequestHeader("topic") String topic,
        @RequestHeader(value = "token", required = false) String token
    );

    @GetMapping("/all")
    Mono<PlantillaResponse<RES>> all(
        @RequestHeader(value = "id", required = false) String id,
        @RequestHeader("ip") String ip,
        @RequestHeader("dominio") String dominio,
        @RequestHeader("usuario") String usuario,
        @RequestHeader(value = "idbusiness", required = false) Integer idbusiness,
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
        @RequestHeader(value = "idbusiness", required = false) Integer idbusiness,
        @RequestHeader("proceso") String proceso,
        @RequestHeader("topic") String topic,
        @RequestHeader(value = "token", required = false) String token
    );

    @DeleteMapping("/delete")
    Mono<PlantillaResponse<RES>> delete(
        @RequestHeader(value = "id", required = false) String id,
        @RequestHeader("ip") String ip,
        @RequestHeader("dominio") String dominio,
        @RequestHeader("usuario") String usuario,
        @RequestHeader(value = "idbusiness", required = false) Integer idbusiness,
        @RequestHeader("proceso") String proceso,
        @RequestHeader("topic") String topic,
        @RequestHeader(value = "token", required = false) String token
    );
}
