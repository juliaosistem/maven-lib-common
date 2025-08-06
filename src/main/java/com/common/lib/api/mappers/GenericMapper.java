package com.common.lib.api.mappers;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GenericMapper<RES, RQ, E> {

    public GenericMapper() {
    }

    /**
     * Mapea de un objeto de tipo `E` a un objeto de tipo `RES`.
     * Esta es una implementación básica que debe ser sobrescrita según las necesidades específicas.
     *
     * @param source El objeto fuente de tipo `E`.
     * @param resClass La clase de tipo `RES`.
     * @return Un objeto mapeado de tipo `RES`.
     */
    public RES mapToRes(E source, Class<RES> resClass) {
        // Implementación básica - debe ser sobrescrita según las necesidades específicas
        try {
            return resClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Error al mapear objeto", e);
        }
    }

    /**
     * Mapea de un objeto de tipo `RQ` a un objeto de tipo `E`.
     * Esta es una implementación básica que debe ser sobrescrita según las necesidades específicas.
     *
     * @param source El objeto fuente de tipo `RQ`.
     * @param entityClass La clase de tipo `E`.
     * @return Un objeto mapeado de tipo `E`.
     */
    public E mapToEntity(RQ source, Class<E> entityClass) {
        // Implementación básica - debe ser sobrescrita según las necesidades específicas
        try {
            return entityClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Error al mapear objeto", e);
        }
    }

    /**
     * Mapea una lista de objetos de tipo `E` a una lista de objetos de tipo `RES`.
     *
     * @param sourceList La lista de objetos fuente de tipo `E`.
     * @param resClass La clase de tipo `RES`.
     * @return Una lista de objetos mapeados de tipo `RES`.
     */
    public List<RES> mapListToRes(List<E> sourceList, Class<RES> resClass) {
        List<RES> list = new ArrayList<>();
        for (E source : sourceList) {
            RES res = mapToRes(source, resClass);
            list.add(res);
        }
        return list;
    }
}
