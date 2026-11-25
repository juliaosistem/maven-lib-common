package com.common.lib.infraestructure.services.secundary;

import com.common.lib.utils.PlantillaResponse;
import org.springframework.stereotype.Service;
import java.util.Map;

/**
 * Interfaz secundaria CRUD genérica
 * @param <RES> Tipo de respuesta
 * @param <RQ> Tipo de request
 */
@Service
public interface CrudSecundaryService<RES, RQ> {

    default PlantillaResponse<RES> all() { 
        return new PlantillaResponse<>(); 
    }

    default PlantillaResponse<RES> all(Map<String, String> filters) {
        return all();
    }

    default PlantillaResponse<RES> byId(Object id) {
        return new PlantillaResponse<>(); 
    }

    default PlantillaResponse<RES> byIdBusiness(Integer idBusiness) { 
        return new PlantillaResponse<>(); 
    }

    default PlantillaResponse<RES> byIdBusiness(Integer idBusiness, Map<String, String> filters) {
        return byIdBusiness(idBusiness);
    }

    default PlantillaResponse<RES> add(RQ request ,Integer idBusiness) {
        return new PlantillaResponse<>(); 
    }

    default PlantillaResponse<RES> update(RQ request) { 
        return new PlantillaResponse<>(); 
    }

    default PlantillaResponse<RES> delete(Object id) {
        return new PlantillaResponse<>(); 
    }
}