package com.common.lib.infraestructure.services.secundary;

import com.common.lib.utils.PlantillaResponse;

/**
 * Interfaz secundaria CRUD alineada con Nest: ID como String.
 */
public interface CrudSecundaryService<RES, RQ, E> {

    default PlantillaResponse<RES> all() { return new PlantillaResponse<>(); }

    default PlantillaResponse<RES> byId(String id) { return new PlantillaResponse<>(); }

    default PlantillaResponse<RES> byIdBusiness(Long idBusiness) { return new PlantillaResponse<>(); }

    default PlantillaResponse<RES> add(RQ request) { return new PlantillaResponse<>(); }

    default PlantillaResponse<RES> update(RQ request) { return new PlantillaResponse<>(); }

    default PlantillaResponse<RES> delete(String id) { return new PlantillaResponse<>(); }
}