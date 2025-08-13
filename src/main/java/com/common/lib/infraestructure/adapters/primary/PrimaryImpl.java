package com.common.lib.infraestructure.adapters.primary;

import com.common.lib.infraestructure.services.primary.CrudPrimaryService;
import com.common.lib.infraestructure.services.secundary.CrudSecundaryService;
import com.common.lib.utils.PlantillaResponse;

/**
 * Clase base primario genérica que delega en el secundario.
 * Úsala directamente desde los microservicios para evitar crear primarios por recurso.
 */
public class PrimaryImpl<RES, RQ, E, I> implements CrudPrimaryService<RES, RQ, E, I> {

    private final CrudSecundaryService<RES, RQ, E, I> secondary;

    public PrimaryImpl(CrudSecundaryService<RES, RQ, E, I> secondary) {
        this.secondary = secondary;
    }

    @Override
    public PlantillaResponse<RES> all() { return secondary.all(); }

    @Override
    public PlantillaResponse<RES> byId(I id) { return secondary.byId(id); }

    @Override
    public PlantillaResponse<RES> add(RQ request) { return secondary.add(request); }

    @Override
    public PlantillaResponse<RES> update(RQ request) { return secondary.update(request); }

    @Override
    public PlantillaResponse<RES> delete(I id) { return secondary.delete(id); }

    public PlantillaResponse<RES> byIdBusiness(Long idBusiness) { return secondary.byIdBusiness(idBusiness); }
}


