package com.juliaosystem.adapters.primary;

import com.juliaosystem.api.response.PlantillaResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface CrudServiceAdapter<T> {
    Optional<PlantillaResponse<T>> all() ;
    Optional<PlantillaResponse<T>> findById(int id);
    Optional<PlantillaResponse<T>> add(T t);
    Optional<PlantillaResponse<T>> update(T t);
    Boolean delete(T t);
}
