package com.common.lib.infraestructure.services.primary;

import com.common.lib.utils.PlantillaResponse;

/**
 * Interfaz para servicios primarios que manejan operaciones CRUD básicas.
 * Define los métodos esenciales para operaciones de lectura, escritura, actualización y eliminación.
 * Sigue los principios SOLID y arquitectura hexagonal.
 * 
 * @author Daniel Juliao
 * @param <RES> Clase de respuesta
 * @param <RQ> Clase de request
 * @param <E> Clase entidad
 * @param <I> Tipo del ID de la entidad
 * @version 3
 */
public interface CrudPrimaryService<RES, RQ, E, I> {

    /**
     * Obtiene todas las entidades.
     * @return Respuesta con todas las entidades
     */
    PlantillaResponse<RES> all();

    /**
     * Obtiene una entidad por su ID.
     * @param id ID de la entidad
     * @return Respuesta con la entidad encontrada
     */
    PlantillaResponse<RES> byId(I id);

    /**
     * Obtiene entidades por idBusiness.
     * @param idBusiness identificador del negocio
     * @return Respuesta con entidades relacionadas al negocio
     */
    PlantillaResponse<RES> byIdBusiness(Long idBusiness);

    /**
     * Crea una nueva entidad.
     * @param request Datos de la entidad a crear
     * @return Respuesta con la entidad creada
     */
    PlantillaResponse<RES> add(RQ request);

    /**
     * Actualiza una entidad existente.
     * @param request Datos de la entidad a actualizar
     * @return Respuesta con la entidad actualizada
     */
    PlantillaResponse<RES> update(RQ request);

    /**
     * Elimina una entidad por su ID.
     * @param id ID de la entidad a eliminar
     * @return Respuesta de confirmación
     */
    PlantillaResponse<RES> delete(I id);
}