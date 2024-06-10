package com.common.lib.infraestructure.services.primary;

import com.common.lib.api.response.PlantillaResponse;

import java.util.UUID;

/**
 *
 * @apiNote
 * Requiere de  E   que es la entidad que responden los metodos y la R que es la request de los metodos y todos los metodos retonar
PlantillaResponse<E>> objeto de respuesta estándar
 * @author : Daniel Juliao
 * @param <E>   Class entidad
 * @param <R> Class request
 * @implNote  interface  encargada de definir   todos los metodos que se usan para los cruds en las clases adapter
 * @version 1
 */
public interface CrudPrimaryService<R,E> {

    default PlantillaResponse<E> all(UUID id, Long idBusiness) {
        return new PlantillaResponse<>();
    }

    default PlantillaResponse<E> add(R e) {
        return new PlantillaResponse<>();
    }

    default PlantillaResponse<E> update(R e) {
        return new PlantillaResponse<>();
    }

    default PlantillaResponse<E> delete(R e) {
        return new PlantillaResponse<>();
    }
}
