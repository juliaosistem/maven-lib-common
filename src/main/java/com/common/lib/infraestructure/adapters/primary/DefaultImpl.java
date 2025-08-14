package com.common.lib.infraestructure.adapters.primary;

import com.common.lib.infraestructure.services.primary.CrudPrimaryService;
import com.common.lib.infraestructure.services.secundary.CrudSecundaryService;
import com.common.lib.utils.PlantillaResponse;

public class DefaultImpl<RES, RQ, E> implements CrudPrimaryService<RES, RQ, E> {

    private final CrudSecundaryService<RES, RQ, E> secondary;

    public DefaultImpl(CrudSecundaryService<RES, RQ, E> secondary) {
        this.secondary = secondary;
    }

    @Override
    public PlantillaResponse<RES> all() {
        return secondary.all();
    }

    @Override
    public PlantillaResponse<RES> byId(String id) {
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
    public PlantillaResponse<RES> delete(String id) {
        return secondary.delete(id);
    }

    public PlantillaResponse<RES> byIdBusiness(Long idBusiness) {
        return secondary.byIdBusiness(idBusiness);
    }
}


