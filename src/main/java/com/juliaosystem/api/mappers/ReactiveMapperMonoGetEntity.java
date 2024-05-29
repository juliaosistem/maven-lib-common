package com.juliaosystem.api.mappers;

import reactor.core.publisher.Mono;

/**
 * @Author daniel juliao
 * @Description retorna un Mono entidad que es igual al parametro T y recibe un parametro D que es el DTO
 * @param <T>
 * @param <D>
 */
public interface ReactiveMapperMonoGetEntity<D,T> {
 Mono<T> getMonoDtoToEntity(D d);
}
