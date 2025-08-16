package com.common.lib.infraestructure.adapters.primary;

import com.common.lib.infraestructure.services.primary.CrudPrimaryService;
import com.common.lib.infraestructure.services.secundary.CrudSecundaryService;
import com.common.lib.utils.PlantillaResponse;
import reactor.core.publisher.Mono;
import java.util.Map;

public class DefaultImpl<RES, RQ, E, I> implements CrudPrimaryService<RES,RQ,E, I> {

    private final CrudSecundaryService<RES, RQ, E,I> secondary;

    public DefaultImpl(CrudSecundaryService<RES, RQ, E,I> secondary) {
        this.secondary = secondary;
    }

    @Override
    public Mono<PlantillaResponse<RES>> all(String topic, I id, Integer idBusiness, Map<String, String> filters) {
        if (id != null) {
            return Mono.fromCallable(() -> secondary.byId(id));
        } else if (idBusiness != null) {
            return Mono.fromCallable(() -> secondary.byIdBusiness(idBusiness));
        } else {
            return Mono.fromCallable(secondary::all);
        }
    }




    @Override
    public Mono<PlantillaResponse<RES>> add(RQ request, I id, String topic) {
        return Mono.fromCallable(() -> secondary.add(request));
    }

    @Override
    public Mono<PlantillaResponse<RES>> update(RQ request, I id, String topic) {
        return Mono.fromCallable(() -> secondary.update(request));
    }

    @Override
    public Mono<PlantillaResponse<RES>> delete(I id, String topic) {
        return Mono.fromCallable(() -> secondary.delete(id));
    }
}


