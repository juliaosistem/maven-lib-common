package com.common.lib.api.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GenericMapper<RES, RQ, E> {

    private final ModelMapper modelMapper = new ModelMapper();

    public GenericMapper() { }

    /**
     * Equivalente a plainToClass: mapea entidad a DTO de respuesta.
     */
    public RES mapToRes(E source, Class<RES> resClass) {
        if (source == null) return null;
        return modelMapper.map(source, resClass);
    }

    /**
     * Equivalente a plainToClass: mapea request a entidad.
     */
    public E mapToEntity(RQ source, Class<E> entityClass) {
        if (source == null) return null;
        return modelMapper.map(source, entityClass);
    }

    /**
     * Mapea listas de entidades a listas de DTOs de respuesta.
     */
    public List<RES> mapListToRes(List<E> sourceList, Class<RES> resClass) {
        if (sourceList == null) return List.of();
        return sourceList.stream()
                .map(e -> mapToRes(e, resClass))
                .collect(Collectors.toList());
    }
}
