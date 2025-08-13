package com.common.lib.infraestructure.adapters.primary;

import com.common.lib.infraestructure.services.primary.CrudPrimaryService;
import com.common.lib.infraestructure.services.secundary.CrudSecundaryService;
import com.common.lib.utils.PlantillaResponse;

/**
 * Servicio primario por defecto que delega en el servicio secundario.
 */
public class DefaultImpl<RES, RQ, E, I> implements CrudPrimaryService<RES, RQ, E, I> {

    private final CrudSecundaryService<RES, RQ, E, I> secondary;

    public DefaultImpl(CrudSecundaryService<RES, RQ, E, I> secondary) {
        this.secondary = secondary;
    }

    @Override
    public PlantillaResponse<RES> all() {
        return secondary.all();
    }

    @Override
    public PlantillaResponse<RES> byId(I id) {
        return secondary.byId(id);
    }

    @Override
    public PlantillaResponse<RES> add(RQ request) {
        return secondary.add(request);
    }

    @Override
    public PlantillaResponse<RES> update(RQ request) {
        return secondary.update(request);
    }

    @Override
    public PlantillaResponse<RES> delete(I id) {
        return secondary.delete(id);
    }

    public PlantillaResponse<RES> byIdBusiness(Long idBusiness) {
        return secondary.byIdBusiness(idBusiness);
    }
}


