package com.common.lib.infraestructure.services.secundary;

import com.common.lib.api.response.PlantillaResponse;
import org.springframework.stereotype.Service;

/**
 *
 * @apiNote
 * Requiere de  E   que es la entidad que responden los metodos y la R que es la request de los metodos y todos los metodos retonar
PlantillaResponse<E>> objeto de respuesta estándar
 * @author : Daniel Juliao
 * @param <E>   Class entidad
 * @param <R>   Class respuesta
 * @param  <I>   Class ID
 * @implNote  interface  encargada de definir   todos los metodos que se usan para los cruds en las clases adapter
 * @version 1
 */
@Service
public interface CrudSecundaryService<R,E,I> {

    default PlantillaResponse<E> all() {
        return new PlantillaResponse<>();
    }

    default PlantillaResponse<E> byIdBussines(Long idBusiness) {
        return new PlantillaResponse<>();
    }

    default PlantillaResponse<E> byId(I id) {
        return new PlantillaResponse<>();
    }

    default PlantillaResponse<E> add(R e) {
        return new PlantillaResponse<>();
    }

    default PlantillaResponse<E> update(R e) {
        return new PlantillaResponse<>();
    }

    default PlantillaResponse<E> delete(I e) {
        return new PlantillaResponse<>();
    }
}