package com.common.lib.api.mappers;

/**
 * Clase genérica para mapear entidades, DTOs y Requests.
 *
 * Extiende la funcionalidad de `PlantillaMapper` para incluir la capacidad de mapear
 * un Request (`R`) a un DTO (`D`) o directamente a una Entidad (`E`).
 *
 * @template E - Tipo de la entidad.
 * @template RES - Tipo del DTO respuesta.
 * @template R - Tipo del Request.
 */

public abstract class MapperConRequest <E, RES, R> extends PlantillaMappers<E,RES>{

    /**
     * Convierte un Request a un DTO.

     * Este método debe ser implementado por las clases que extiendan `MapperConRequest`.
     *
     * @param request - El Request que será transformado en un DTO.
     * @returns El DTO correspondiente.
     */
    public abstract RES deRequestADTO(R request);
    /**
     * Convierte un Request a una Entidad.
     *
     * Este método debe ser implementado por las clases que extiendan `MapperConRequest`.
     *
     * @param request - El Request que será transformado en una Entidad.
     * @returns La Entidad correspondiente.
     */
   public abstract E deRequestAEntidad( R request) ;

}
