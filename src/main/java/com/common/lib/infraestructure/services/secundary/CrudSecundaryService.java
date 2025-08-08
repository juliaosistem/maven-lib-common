package com.common.lib.infraestructure.services.secundary;

import com.common.lib.utils.PlantillaResponse;

/**
 * Interfaz para servicios secundarios que manejan operaciones CRUD adicionales.
 * Define métodos para operaciones específicas de negocio.
 * Sigue los principios SOLID y arquitectura hexagonal.
 * 
 * @author Daniel Juliao
 * @param <RES> Clase de respuesta
 * @param <RQ> Clase de request
 * @param <E> Clase entidad
 * @param <I> Tipo del ID de la entidad
 * @version 3
 */
public interface CrudSecundaryService<RES, RQ, E, I> {

    /**
     * Obtiene todas las entidades.
     * @return Respuesta con todas las entidades
     */
    default PlantillaResponse<RES> all() {
        return new PlantillaResponse<>();
    }

    /**
     * Obtiene una entidad por su ID.
     * @param id ID de la entidad
     * @return Respuesta con la entidad encontrada
     */
    default PlantillaResponse<RES> byId(I id) {
        return new PlantillaResponse<>();
    }

    /**
     * Crea una nueva entidad.
     * @param request Datos de la entidad a crear
     * @return Respuesta con la entidad creada
     */
    default PlantillaResponse<RES> add(RQ request) {
        return new PlantillaResponse<>();
    }

    /**
     * Actualiza una entidad existente.
     * @param request Datos de la entidad a actualizar
     * @return Respuesta con la entidad actualizada
     */
    default PlantillaResponse<RES> update(RQ request) {
        return new PlantillaResponse<>();
    }

    /**
     * Elimina una entidad por su ID.
     * @param id ID de la entidad a eliminar
     * @return Respuesta de confirmación
     */
    default PlantillaResponse<RES> delete(I id) {
        return new PlantillaResponse<>();
    }
}