package com.common.lib.api.mappers;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *  Recibe en el parametro E la entidad y  Res  = Dto   clase abstracta para manejar mapeos
 *  daniel juliao
 * @param <E> entidad
 * @param <RES> dto

 * @author Daniel Juliao
 * @version 2
 */
@Component
public abstract class PlantillaMappers <E,RES> {

    public abstract RES mapToRes(E source);
    public abstract E mapToEntity(RES source);


    /**
     * Mapea listas de entidades a listas de DTOs de respuesta.
     * @param sourceList Lista de entidades
     *
     */
    public  List<RES> mapListToRes(List<E> sourceList) {
        if (sourceList == null) return List.of();
        return sourceList.stream()
                .map(this::mapToRes)
                .collect(Collectors.toList());
    }

    /**
     *  Mapea listas de DTOs de respuesta a listas de entidades.
     *
     * @param sourceList Lista de entidades
     *
     */
    public List<E> mapListToEntity(List<RES> sourceList) {
        if (sourceList == null ) return List.of();
        return sourceList.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
    }


    /**
     * Convierte un mapa que se debe ordenar de entidades de menor nivel a mayor nivel
     * para saber en qué orden se deben insertar los datos en la base de datos.
     *
     * La clave del mapa es el nombre de la entidad y el valor es una lista de instancias
     * de dicha entidad. Se permite usar Object para soportar múltiples tipos de entidades
     * dependientes.
     *
     * @param entidad Entidad principal a partir de la cual se generará el mapa de dependencias.
     * @return Mapa de entidades ordenadas: clave = nombre de la entidad, valor = lista de objetos.
     */
    public abstract Map<String, List<Object>> getOrdenEntidades(E entidad);


}
