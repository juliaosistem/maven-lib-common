package com.common.lib.infraestructure.services.primary;

import com.common.lib.utils.PlantillaResponse;

/**
 * Interfaz para servicios primarios CRUD.
 * Alineada con Nest: ID como String.
 */
public interface CrudPrimaryService<RES, RQ, E> {

    PlantillaResponse<RES> all();

    PlantillaResponse<RES> byId(String id);

    PlantillaResponse<RES> byIdBusiness(Long idBusiness);

    PlantillaResponse<RES> add(RQ request);

    PlantillaResponse<RES> update(RQ request);

    PlantillaResponse<RES> delete(String id);
}