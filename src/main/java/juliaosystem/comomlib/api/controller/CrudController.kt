package juliaosystem.comomlib.api.controller


import juliaosystem.comomlib.api.response.PlantillaResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

/**
 * Recibe en el parámetro E la entidad del body. Es encargada de construir los controladores.
 * @Author: Daniel Juliao
 * @param E entidad por defecto del controlador
 * @property PlantillaResponse
 * @return  ResponseEntity<PlantillaResponse<E>> objeto de respuesta estándar
 * @version 1
 */

interface CrudController<E> {


    @GetMapping("/findById/{id}/{idBussines}")
    fun findById(
        @PathVariable("id", required = true) id : UUID,
        @PathVariable("idBussines", required = true) idBussines: Long,
        @RequestHeader("ip", required = true) ip: String,
        @RequestHeader("dominio", required = true) dominio: String,
        @RequestHeader("usuario", required = true) usuario: String,
        @RequestHeader("logError", required = false) logError: Boolean,
        @RequestHeader("logComplete", required = false) logComplete: Boolean,
        @RequestHeader("fecha", required = true) fecha: String
    ): ResponseEntity<PlantillaResponse<E>>

    @PostMapping("/add")
    fun add(
            @RequestBody(required = true) entidad: E,
            @RequestHeader("ip", required = true)ip: String,
            @RequestHeader("dominio" , required = true) dominio: String,
            @RequestHeader("usuario" , required = true) usuario: String,
            @RequestHeader("idBussines", required = true) idBussines: Long,
            @RequestHeader("logError", required = false ) logError: Boolean,
            @RequestHeader("logComplete", required = false) logComplete: Boolean,
            @RequestHeader("fecha" , required = true ) fecha: String
    ): ResponseEntity<PlantillaResponse<E>>

    @GetMapping("/all/{idBussines}")
    fun findByIdBussines(
            @PathVariable(required = true) idBussines: Long,
            @RequestHeader("ip", required = true) ip: String,
            @RequestHeader("dominio", required = true) dominio: String,
            @RequestHeader("usuario", required = true) usuario: String,
            @RequestHeader("idBussines", required = true) idBussinesHeader: Long,
            @RequestHeader("logError", required = false) logError: Boolean,
            @RequestHeader("logComplete", required = false) logComplete: Boolean,
            @RequestHeader("fecha", required = true) fecha: String
    ): ResponseEntity<PlantillaResponse<E>>



    @PutMapping("/update/{id}")
    fun update(
            @PathVariable id: Long,
            @RequestBody entidad: E,
            @RequestHeader("ip") ip: String,
            @RequestHeader("dominio") dominio: String,
            @RequestHeader("usuario") usuario: String,
            @RequestHeader("idBussines") idBussines: Long,
            @RequestHeader("logError") logError: Boolean,
            @RequestHeader("logComplete") logComplete: Boolean,
            @RequestHeader("fecha") fecha: String
    ): ResponseEntity<PlantillaResponse<E>>


}
