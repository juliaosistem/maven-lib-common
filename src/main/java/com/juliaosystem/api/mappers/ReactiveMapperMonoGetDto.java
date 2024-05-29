package com.juliaosystem.api.mappers;


import reactor.core.publisher.Mono;

/**
 * @description Recibe en el parametro T la entidad y en D Dto
 * @Autor daniel juliao
 * @param <T>
 * @param <D>
 * @version 1
 * @return retorna un mono Dto
 */
public interface ReactiveMapperMonoGetDto<T,D>{
    Mono<D> getMonoEntityToDto(T t);

}
