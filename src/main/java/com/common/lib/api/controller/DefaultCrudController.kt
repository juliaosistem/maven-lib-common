package com.common.lib.api.controller

import com.common.lib.utils.PlantillaResponse
import com.common.lib.utils.ResponseType
import com.common.lib.utils.ResponseTypeEnum
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

/**
 * Implementación por defecto del controlador CRUD
 * @param RES Clase de respuesta
 * @param RQ Clase de solicitud
 * @author Daniel juliao
 * @version 1
 */
@RestController
abstract class DefaultCrudController<RES, RQ> : CrudController<RES, RQ> {

    override fun add(
        request: RQ,
        id: String?,
        ip: String,
        dominio: String,
        usuario: String,
        idbusiness: Int?,
        proceso: String,
        topic: String,
        token: String?
    ): ResponseEntity<PlantillaResponse<RES>> {
        // Implementación por defecto - debe ser sobrescrita en las clases hijas
        val response = PlantillaResponse<RES>(
            true,
            "Operación no implementada",
            HttpStatus.NOT_IMPLEMENTED,
            null,
            null
        )
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(response)
    }

    override fun all(
        id: String?,
        ip: String,
        dominio: String,
        usuario: String,
        idbusiness: Int?,
        proceso: String,
        topic: String,
        token: String?,
        filters: Map<String, String>?
    ): ResponseEntity<PlantillaResponse<RES>> {
        // Implementación por defecto - debe ser sobrescrita en las clases hijas
        val response = PlantillaResponse<RES>(
            true,
            "Operación no implementada",
            HttpStatus.NOT_IMPLEMENTED,
            null,
            null
        )
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(response)
    }

    override fun update(
        request: RQ,
        id: String?,
        ip: String,
        dominio: String,
        usuario: String,
        idbusiness: Int?,
        proceso: String,
        topic: String,
        token: String?
    ): Mono<PlantillaResponse<RES>> {
        // Implementación por defecto - debe ser sobrescrita en las clases hijas
        val response = PlantillaResponse<RES>(
            true,
            "Operación no implementada",
            HttpStatus.NOT_IMPLEMENTED,
            null,
            null
        )
        return Mono.just(response)
    }

    override fun delete(
        id: String,
        ip: String,
        dominio: String,
        usuario: String,
        idbusiness: Int?,
        proceso: String,
        topic: String,
        token: String?
    ): ResponseEntity<PlantillaResponse<RES>> {
        // Implementación por defecto - debe ser sobrescrita en las clases hijas
        val response = PlantillaResponse<RES>(
            true,
            "Operación no implementada",
            HttpStatus.NOT_IMPLEMENTED,
            null,
            null
        )
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(response)
    }
}