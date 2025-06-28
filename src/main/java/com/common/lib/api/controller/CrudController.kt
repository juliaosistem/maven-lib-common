package com.common.lib.api.controller

import com.common.lib.api.response.PlantillaResponse
import com.common.lib.utils.RequestHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import reactor.core.publisher.Mono

/**
 * Recibe en el parámetro E la entidad del body. Es encargada de construir los controladores.
 * @Author: Daniel Juliao
 * @param RES Clase Response
 * @param RQ Clase request
 * @param E Entidad
 * @param I Tipo de dato de la entidad
 * @property PlantillaResponse
 * @return ResponseEntity<Mono<PlantillaResponse<RES>>> Objeto de respuesta estándar reactivo
 * @version 2
 */
interface CrudController<RES, RQ, E, I> {

    @PostMapping("/add")
    fun add(
        @RequestBody(required = true) entidad: RQ,
        @RequestHeader headers: RequestHeaders<I>
    ): ResponseEntity<Mono<PlantillaResponse<RES>>> {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build()
    }

    @GetMapping("/all")
    fun all(
        @RequestParam filters: Map<String, String>,
        @RequestHeader headers: RequestHeaders<I>
    ): ResponseEntity<Mono<PlantillaResponse<RES>>> {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build()

    }

    @PutMapping("/update")
    fun update(
        @RequestBody entidad: RQ,
        @RequestHeader headers: RequestHeaders<I>
    ): ResponseEntity<Mono<PlantillaResponse<RES>>> {
        // Lógica para actualizar
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build()

    }

    @DeleteMapping("delete/{id}")
    fun delete(
        @PathVariable id: I,
        @RequestHeader headers: RequestHeaders<I>
    ): ResponseEntity<Mono<PlantillaResponse<RES>>> {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build()

    }
}
