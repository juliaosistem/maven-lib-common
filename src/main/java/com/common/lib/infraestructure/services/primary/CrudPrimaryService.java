package com.common.lib.infraestructure.services.primary;

import com.common.lib.utils.PlantillaResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Interface se usa para implementar en adaptadores de logica de negocio para cruds.
 * @param <RES> Objeto Respuesta .
 * @param <RQ> Objeto Request .
 */
@Component
public interface CrudPrimaryService<RES, RQ> {

    /**
     * Obtiene todas las entidades, con filtros opcionales.
     * @param headers Identificador del negocio (opcional)
     *
     * @param filters Mapa de filtros personalizados (opcional)
     */
    Mono<PlantillaResponse<RES>> all(Object id ,HttpHeaders headers, Map<String, String> filters);

    /**
     * Agrega una nueva entidad.
     * @param request Objeto de solicitud de creación
     * @param headers Identificador del negocio ,topico kafka y redis
     *
     */
    Mono<PlantillaResponse<RES>> add(RQ request, HttpHeaders headers);

    /**
     * Actualiza una entidad existente.
     * @param headers de aqui se optiee el id y el id de negocio
     * @param request  entidad a actualizar
     *
     */
    Mono<PlantillaResponse<RES>> update(Object id,RQ request, HttpHeaders headers);

    /**
     * Elimina una entidad por su ID.
     * @param id Identificador de la entidad
     * @param headers Identificador topico kafka y redis id, idBusiness
     */
    Mono<PlantillaResponse<RES>> delete(Object id, HttpHeaders headers);
}