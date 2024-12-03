package com.common.lib.infraestructure.services.secundary;

import com.common.lib.api.response.PlantillaResponse;
import org.springframework.stereotype.Service;

/**
 *
 * @apiNote
 * Requiere de RES que es la entidad que responden los metodos y la RQ que es la request de los metodos y todos los metodos retonar
PlantillaResponse<RES>> objeto de respuesta estándar
 * @author : Daniel Juliao
 * @param <E>   Class entidad
 * @param <RES>>   Class Response
 * @param <RQ>   Class Request
 * @param  <I>   Class ID
 * @implNote  interface encargada de definir todos los metodos que se usan para los cruds en las clases adapter
 * @version 1
 */
@Service
public interface CrudSecundaryService<RES, RQ, E, I> {

    default PlantillaResponse<RES> all() {
        return new PlantillaResponse<>();
    }

    default PlantillaResponse<RES> byIdBussines(Long idBusiness) {
        return new PlantillaResponse<>();
    }

    default PlantillaResponse<RES> byId(I id) {
        return new PlantillaResponse<>();
    }

    default PlantillaResponse<RES> add(RQ e) {
        return new PlantillaResponse<>();
    }

    default PlantillaResponse<RES> update(RQ e) {
        return new PlantillaResponse<>();
    }

    default PlantillaResponse<RES> delete(I e) {
        return new PlantillaResponse<>();
    }
}