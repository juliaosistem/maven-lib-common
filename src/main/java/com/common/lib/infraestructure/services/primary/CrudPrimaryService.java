package com.common.lib.infraestructure.services.primary;

import com.common.lib.utils.PlantillaResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Interface se usa para implementar en adaptadores de logica de negocio para cruds.
 * @param <RES> Objeto Respuesta .
 * @param <RQ> Objeto Request .
 * @param <E> Entidad de la base de datos.
 * @param <I> Tipo de id de la entidad puede ser String o Integer.
 */
public interface CrudPrimaryService<RES, RQ, E, I> {

    /**
     * Obtiene todas las entidades, con filtros opcionales.
     * @param topic Identificador topico kafka y redis
     * @param id Identificador de la entidad (opcional)
     * @param idBusiness Identificador del negocio (opcional)
     * @param filters Mapa de filtros personalizados (opcional)
     */
    Mono<PlantillaResponse<RES>> all(String topic, I id, Integer idBusiness, Map<String, String> filters);

    /**
     * Agrega una nueva entidad.
     * @param request Objeto de solicitud de creación
     * @param id Identificador de la entidad
     * @param topic Identificador topico kafka y redis
     */
    Mono<PlantillaResponse<RES>> add(RQ request, I id, String topic);

    /**
     * Actualiza una entidad existente.
     * @param request Objeto de solicitud de actualización
     * @param id Identificador de la entidad
     * @param topic Identificador topico kafka y redis
     */
    Mono<PlantillaResponse<RES>> update(RQ request, I id, String topic);

    /**
     * Elimina una entidad por su ID.
     * @param id Identificador de la entidad
     * @param topic Identificador topico kafka y redis
     */
    Mono<PlantillaResponse<RES>> delete(I id, String topic);
}