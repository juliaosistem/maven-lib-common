package com.common.lib.api.controller


import com.common.lib.api.response.PlantillaResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Recibe en el parámetro E la entidad del body. Es encargada de construir los controladores.
 * @Author: Daniel Juliao
 * @param RES Class Response
 * @param RQ Class request
 * @param E entidad
 * @param I Tipo de dato de la entidad
 * @property PlantillaResponse
 * @return  ResponseEntity<PlantillaResponse<E>> objeto de respuesta estándar
 * @version 2
 */

interface CrudController<RES,RQ,E,I> {

    @PostMapping("/add")
    fun add(
        @RequestBody(required = true) entidad: RQ,
        @RequestHeader("ip", required = true)id: I?,
        @RequestHeader("ip", required = true)ip: String,
        @RequestHeader("dominio" , required = true) dominio: String,
        @RequestHeader("usuario" , required = true) usuario: String,
        @RequestHeader("idBussines", required = true) idBussines: Long,
        @RequestHeader("proceso", required = true) proceso: String,
    ): ResponseEntity<PlantillaResponse<RES>>{
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build()
    }



    @GetMapping("/all")
    fun all(
        @RequestParam("id", required = false) id : I?,
        @RequestHeader("ip", required = true) ip: String,
        @RequestHeader("dominio", required = true) dominio: String,
        @RequestHeader("usuario", required = true) usuario: String,
        @RequestHeader("idBussines", required = false) idBussines: Long,
        @RequestHeader("proceso", required = true) proceso: String,
    ): ResponseEntity<PlantillaResponse<RES>>{
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build()
    }



    @PutMapping("/update")
    fun update(
        @RequestBody entidad: RQ,
        @RequestHeader("ip") ip: String,
        @RequestHeader("dominio") dominio: String,
        @RequestHeader("usuario") usuario: String,
        @RequestHeader("idBussines") idBussines: Long,
        @RequestHeader("proceso", required = true) proceso: String,
    ): ResponseEntity<PlantillaResponse<RES>>{
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build()
    }



}
