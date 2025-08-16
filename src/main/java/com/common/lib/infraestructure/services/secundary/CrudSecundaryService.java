package com.common.lib.infraestructure.services.secundary;

import com.common.lib.utils.PlantillaResponse;

/**
 * Interfaz secundaria CRUD genérica
 * @param <RES> Tipo de respuesta
 * @param <RQ> Tipo de request
 * @param <E> Tipo de entidad
 * @param <I> Tipo de ID
 */
public interface CrudSecundaryService<RES, RQ, E, I> {

    default PlantillaResponse<RES> all() { 
        return new PlantillaResponse<>(); 
    }

    default PlantillaResponse<RES> byId(I id) { 
        return new PlantillaResponse<>(); 
    }

    default PlantillaResponse<RES> byIdBusiness(Integer idBusiness) { 
        return new PlantillaResponse<>(); 
    }

    default PlantillaResponse<RES> add(RQ request) { 
        return new PlantillaResponse<>(); 
    }

    default PlantillaResponse<RES> update(RQ request) { 
        return new PlantillaResponse<>(); 
    }

    default PlantillaResponse<RES> delete(I id) { 
        return new PlantillaResponse<>(); 
    }
}