package com.common.lib.api.controller


import com.common.lib.api.response.PlantillaResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

/**
 * Recibe en el parámetro E la entidad del body. Es encargada de construir los controladores.
 * @Author: Daniel Juliao
 * @param E entidad por defecto del controlador
 * @param R  Class request
 * @property PlantillaResponse
 * @return  ResponseEntity<PlantillaResponse<E>> objeto de respuesta estándar
 * @version 1
 */

interface CrudController<E,R> {



    @PostMapping("/add")
    fun add(
            @RequestBody(required = true) entidad: R,
            @RequestHeader("ip", required = true)ip: String,
            @RequestHeader("dominio" , required = true) dominio: String,
            @RequestHeader("usuario" , required = true) usuario: String,
            @RequestHeader("idBussines", required = true) idBussines: Long,
            @RequestHeader("proceso", required = true) proceso: String,
    ): ResponseEntity<PlantillaResponse<E>>{
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build()
    }



    @GetMapping("/all")
    fun all(
           @RequestParam("id", required = false) id : UUID?,
            @RequestHeader("ip", required = true) ip: String,
            @RequestHeader("dominio", required = true) dominio: String,
            @RequestHeader("usuario", required = true) usuario: String,
            @RequestHeader("idBussines", required = true) idBussines: Long,
            @RequestHeader("proceso", required = true) proceso: String,
    ): ResponseEntity<PlantillaResponse<E>>{
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build()
    }



    @PutMapping("/update")
    fun update(
            @RequestBody entidad: R,
            @RequestHeader("ip") ip: String,
            @RequestHeader("dominio") dominio: String,
            @RequestHeader("usuario") usuario: String,
            @RequestHeader("idBussines") idBussines: Long,
            @RequestHeader("proceso", required = true) proceso: String,
    ): ResponseEntity<PlantillaResponse<E>>{
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build()
    }



}
