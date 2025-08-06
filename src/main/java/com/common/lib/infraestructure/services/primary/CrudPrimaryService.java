package com.common.lib.infraestructure.services.primary;

import com.common.lib.utils.PlantillaResponse;

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
public interface CrudPrimaryService<RES, RQ, E, I> {

    PlantillaResponse<RES> all();

    PlantillaResponse<RES> byId(I id);

    PlantillaResponse<RES> add(RQ e);

    PlantillaResponse<RES> delete(I id);

    PlantillaResponse<RES> update(RQ e);

    PlantillaResponse<RES> byIdBusiness(Long idBusiness);

    default PlantillaResponse<RES> all(I id, Long idBusiness) {
        return null;
    }
}