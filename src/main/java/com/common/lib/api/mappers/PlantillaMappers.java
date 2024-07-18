package com.common.lib.api.mappers;

import org.springframework.stereotype.Component;

/**
 *  Recibe en el parametro T la entidad y en D Dto
 *  daniel juliao
 * @param <T> entidad
 * @param <D> dto
 * @version 1
 */
@Component
public interface PlantillaMappers <T,D>extends PlantillaMapperGetEntity<T, D>, PlantillaMapperGetDTO<T,D> {
}
