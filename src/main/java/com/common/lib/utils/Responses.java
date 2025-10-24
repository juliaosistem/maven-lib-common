package com.common.lib.utils;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Clase para manejar las respuestas genéricas
 * @param <E> Tipo de datos de la respuesta
 * @author Daniel juliao
 * @version 1
 */
@Component
public class Responses<E> {
    
    /**
     * Constructor por defecto
     */
    public Responses() {
    }

    /**
     * Construye una respuesta genérica
     * @param tipoRespuesta Tipo de respuesta
     * @param datos Datos de la respuesta
     * @param dataList Lista de datos de la respuesta
     * @return PlantillaResponse con la respuesta construida
     */
    public PlantillaResponse<E> buildResponse(ResponseType tipoRespuesta, E datos, List<E> dataList) {
        return new PlantillaResponse<>(
            tipoRespuesta.isRta(),
            tipoRespuesta.getMessage(),
            tipoRespuesta.getHttpStatus(),
            datos,
            dataList
        );
    }

    /**
     * Construye una respuesta genérica con datos únicos
     * @param tipoRespuesta Tipo de respuesta
     * @param datos Datos de la respuesta
     * @return PlantillaResponse con la respuesta construida
     */
    public PlantillaResponse<E> buildResponse(ResponseType tipoRespuesta, E datos) {
        return buildResponse(tipoRespuesta, datos, null);
    }

    /**
     * Construye una respuesta genérica con lista de datos
     * @param tipoRespuesta Tipo de respuesta
     * @param dataList Lista de datos de la respuesta
     * @return PlantillaResponse con la respuesta construida
     */
    public PlantillaResponse<E> buildResponse(ResponseType tipoRespuesta, List<E> dataList) {
        return buildResponse(tipoRespuesta, null, dataList);
    }

    /**
     * Construye una respuesta reactiva genérica
     * @param tipoRespuesta Tipo de respuesta
     * @param datos Datos de la respuesta
     * @param dataList Lista de datos de la respuesta
     * @return Mono con PlantillaResponse
     */
    public Mono<PlantillaResponse<E>> buildResponseMono(ResponseType tipoRespuesta, E datos, List<E> dataList) {
        return Mono.just(buildResponse(tipoRespuesta, datos, dataList));
    }

    /**
     * Construye una respuesta reactiva genérica con datos únicos
     * @param tipoRespuesta Tipo de respuesta
     * @param datos Datos de la respuesta
     * @return Mono con PlantillaResponse
     */
    public Mono<PlantillaResponse<E>> buildResponseMono(ResponseType tipoRespuesta, E datos) {
        return buildResponseMono(tipoRespuesta, datos, null);
    }

    /**
     * Construye una respuesta reactiva genérica con lista de datos
     * @param tipoRespuesta Tipo de respuesta
     * @param dataList Lista de datos de la respuesta
     * @return Mono con PlantillaResponse
     */
    public Mono<PlantillaResponse<E>> buildResponseMono(ResponseType tipoRespuesta, List<E> dataList) {
        return buildResponseMono(tipoRespuesta, null, dataList);
    }
} 