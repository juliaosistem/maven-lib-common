package com.common.lib.api.controller

import com.common.lib.utils.PlantillaResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

/**
 * Interfaz para los controladores CRUD, equivalente a la interfaz TypeScript.
 * @param RES Clase de respuesta
 * @param RQ Clase de solicitud
 */
interface CrudController<RES, RQ> {

    @PostMapping("/add")
    fun add(
        @RequestBody request: RQ,
        @RequestHeader("id") id: String?,
        @RequestHeader("ip") ip: String,
        @RequestHeader("dominio") dominio: String,
        @RequestHeader("usuario") usuario: String,
        @RequestHeader("idbusiness", required = false) idbusiness: Int?,
        @RequestHeader("proceso") proceso: String,
        @RequestHeader("topic") topic: String,
        @RequestHeader("token", required = false) token: String?
    ): ResponseEntity<PlantillaResponse<RES>>

    @GetMapping("/all")
    fun all(
        @RequestHeader("id") id: String?,
        @RequestHeader("ip") ip: String,
        @RequestHeader("dominio") dominio: String,
        @RequestHeader("usuario") usuario: String,
        @RequestHeader("idbusiness", required = false) idbusiness: Int?,
        @RequestHeader("proceso") proceso: String,
        @RequestHeader("topic") topic: String,
        @RequestHeader("token", required = false) token: String?,
        @RequestParam(required = false) filters: Map<String, String>?
    ): ResponseEntity<PlantillaResponse<RES>>

    @PutMapping("/update")
    fun update(
        @RequestBody request: RQ,
        @RequestHeader("id") id: String?,
        @RequestHeader("ip") ip: String,
        @RequestHeader("dominio") dominio: String,
        @RequestHeader("usuario") usuario: String,
        @RequestHeader("idbusiness", required = false) idbusiness: Int?,
        @RequestHeader("proceso") proceso: String,
        @RequestHeader("topic") topic: String,
        @RequestHeader("token", required = false) token: String?
    ): Mono<PlantillaResponse<RES>>

    @DeleteMapping("/delete/{id}")
    fun delete(
        @PathVariable id: String,
        @RequestHeader("ip") ip: String,
        @RequestHeader("dominio") dominio: String,
        @RequestHeader("usuario") usuario: String,
        @RequestHeader("idbusiness", required = false) idbusiness: Int?,
        @RequestHeader("proceso") proceso: String,
        @RequestHeader("topic") topic: String,
        @RequestHeader("token", required = false) token: String?
    ): ResponseEntity<PlantillaResponse<RES>>
}
