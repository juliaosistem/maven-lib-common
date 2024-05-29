package com.juliaosystem.adapters.secundary;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface CrudReactiveServiceInterface<T> {
    Flux<T> all() ;
    Mono<T> findById(int id);
    Mono<T> add(T t);
    Mono<T> update(T t);
    Boolean delete(T t);
}
