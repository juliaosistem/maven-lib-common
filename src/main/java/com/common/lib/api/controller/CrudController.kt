package com.common.lib.api.controller

import com.common.lib.api.response.PlantillaResponse
import com.common.lib.utils.QueryParams
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
        @RequestHeader queryParams: QueryParams,
        res: ResponseEntity<PlantillaResponse<RES>>
    ): ResponseEntity<PlantillaResponse<RES>>

    @GetMapping("/all")
    fun all(
        @RequestHeader queryParams: QueryParams,
        res: ResponseEntity<PlantillaResponse<RES>>,
        @RequestParam(required = false) filters: Map<String, String>?
    ): ResponseEntity<PlantillaResponse<RES>>

    @PutMapping("/update")
    fun update(
        @RequestBody request: RQ,
        @RequestHeader queryParams: QueryParams
    ): Mono<PlantillaResponse<RES>>

    @DeleteMapping("/delete")
    fun delete(
        @RequestHeader queryParams: QueryParams,
        res: ResponseEntity<PlantillaResponse<RES>>
    ): ResponseEntity<PlantillaResponse<RES>>
}
    ): ResponseEntity<PlantillaResponse<RES>>

    @PutMapping("/update")
    fun update(
        @RequestBody request: RQ,
        @RequestHeader id: String?,
        @RequestHeader ip: String,
        @RequestHeader dominio: String,
        @RequestHeader usuario: String,
        @RequestHeader(required = false) idbusiness: Int?,
        @RequestHeader proceso: String,
        @RequestHeader topic: String,
        @RequestHeader(required = false) token: String?
    ): Mono<PlantillaResponse<RES>>

    @DeleteMapping("/delete")
    fun delete(
        @RequestHeader id: String?,
        @RequestHeader ip: String,
        @RequestHeader dominio: String,
        @RequestHeader usuario: String,
        @RequestHeader(required = false) idbusiness: Int?,
        @RequestHeader proceso: String,
        @RequestHeader topic: String,
        @RequestHeader(required = false) token: String?,
        res: ResponseEntity<PlantillaResponse<RES>>
    ): ResponseEntity<PlantillaResponse<RES>>
}
