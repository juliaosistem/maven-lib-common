package com.common.lib.utils;

import com.common.lib.utils.enums.ResponseType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 *  Recibe en el parametro E la entidad  es escargada de Costruir las respuestas Revisar Enum ResponseType
 *  importante int tipoRespuesta se puede ver o definir en ResponseType
 * @author  daniel juliao
 * @param <RES> entidad
 *  {@link ResponseType}
 * @version 1
 */
@Component
public class UserResponses<RES> {
    public PlantillaResponse<RES> buildResponse(int tipoRespuesta, RES e) {
        return buildResponse(tipoRespuesta, e, null);
    }

    /**
     * Construye una respuesta estándar con una única entidad.
     *
     * @param tipoRespuesta Código que representa el tipo de respuesta deseada.
     * @param e             La entidad que se incluirá en la respuesta.
     * @return Una instancia de PlantillaResponse que representa la respuesta construida.
     * {@link ResponseType}
     *  {@link PlantillaResponse}
     * @throws IllegalArgumentException Si el tipo de respuesta proporcionado no es válido.
     */
    public PlantillaResponse<RES> buildResponse(int tipoRespuesta, RES e, List<RES> listE) {
        ResponseType responseType = ResponseType.fromCode(tipoRespuesta);
        if (responseType != null) {
            @SuppressWarnings("unchecked")
            RES[] dataArray = listE != null ? listE.toArray((RES[]) new Object[0]) : null;
            return PlantillaResponse.<RES>builder()
                    .message(responseType.getMessage())
                    .data(e)
                    .dataList(dataArray)
                    .rta(responseType.isRta())
                    .httpStatus(responseType.getHttpStatus())
                    .build();
        }

        throw new IllegalArgumentException("Tipo de respuesta no válido: " + tipoRespuesta);
    }

    /**
     * Crea un Observable que emite la respuesta construida.
     *
     * @param tipoRespuesta Código que representa el tipo de respuesta deseada.
     * @param e             La entidad que se incluirá en la respuesta.
     * @param listE         Lista de entidades adicionales.
     * @return Observable que emite la respuesta de tipo PlantillaResponse.
     */
    public Mono<PlantillaResponse<RES>> buildResponseObserver(int tipoRespuesta, RES e, List<RES> listE) {
        var response = buildResponse(tipoRespuesta, e, listE);
        return Mono.just(response);
    }
}
