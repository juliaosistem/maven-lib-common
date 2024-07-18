package com.common.lib.api.controller

import com.common.lib.api.dtos.request.AuditRequest
import com.common.lib.api.response.PlantillaResponse
import com.common.lib.infraestructure.services.primary.DefualtService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class DefaultCrudController<RES, RQ, E,I>(
    private val defualtService: DefualtService<RQ,RES,I>,
) : CrudController<RES, RQ, E,I> {

    override fun add(
        entidad: RQ,
        id: I?,
        ip: String,
        dominio: String,
        usuario: String,
        idBussines: Long,
        proceso: String
    ): ResponseEntity<PlantillaResponse<RES>> {
        val audit = AuditRequest.builder()
            .ip(ip).dominio(dominio)
            .proceso(proceso).usuario(usuario)
            .idBussines(idBussines)
            .build()

        val response = defualtService.add(entidad,id, audit)
        return ResponseEntity(response, response.httpStatus)
    }

    override fun all(
        id: I?,
        ip: String,
        dominio: String,
        usuario: String,
        idBussines: Long,
        proceso: String
    ): ResponseEntity<PlantillaResponse<RES>> {
        val audit = AuditRequest.builder()
            .ip(ip).dominio(dominio)
            .proceso(proceso).usuario(usuario)
            .idBussines(idBussines)
            .build()

        val response = defualtService.all(id, idBussines, audit)
        return ResponseEntity(response, response.httpStatus)
    }

    override fun update(
        entidad: RQ,
        ip: String,
        dominio: String,
        usuario: String,
        idBussines: Long,
        proceso: String
    ): ResponseEntity<PlantillaResponse<RES>> {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build()
    }
}
