package com.common.lib.infraestructure.services.primary;

import com.common.lib.api.response.PlantillaResponse;
import org.springframework.stereotype.Service;


/**
 *
 * @apiNote
 * Requiere de  E   que es la entidad que responden los metodos y la R que es la request de los metodos y todos los metodos retonar
PlantillaResponse<RES>> objeto de respuesta estándar
 * @author : Daniel Juliao
 * @param <E>   Class entidad
 * @param <RQ> Class request
 * @param <I>  id de la entidad
 * @param <RES> Class response
 * @implNote  interface  encargada de definir   todos los metodos que se usan para los cruds en las clases adapter
 * @version 1
 */
@Service
public interface CrudPrimaryService<RES, RQ, E, I> {

    default PlantillaResponse<RES> all(I id, Long idBussines) {
        return new PlantillaResponse<>();
    }

    default PlantillaResponse<RES> add(RQ e,I id) {
        return new PlantillaResponse<>();
    }

    default PlantillaResponse<RES> update(RQ e) {
        return new PlantillaResponse<>();
    }

    default PlantillaResponse<RES> delete(I e) {
        return new PlantillaResponse<>();
    }
}