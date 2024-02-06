package juliaosystem.comomlib.utils;

import
juliaosystem.comomlib.api.response.PlantillaResponse;
import juliaosystem.comomlib.utils.enums.ResponseType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description Recibe en el parametro E la entidad  es escargada de Costruir las respuestas Revisar Enum ResponseType
 * @Autor daniel juliao
 * @param <E> entidad
 * @version 1
 */
public class UserResponses<E> {
    public PlantillaResponse<E> buildResponse(int tipoRespuesta, E e) {
        return buildResponse(tipoRespuesta, e, null);
    }

    /**
     * Construye una respuesta estándar con una única entidad.
     *
     * @param tipoRespuesta Código que representa el tipo de respuesta deseada.
     * @param e             La entidad que se incluirá en la respuesta.
     * @return Una instancia de PlantillaResponse que representa la respuesta construida.
     * @throws IllegalArgumentException Si el tipo de respuesta proporcionado no es válido.
     */
    public PlantillaResponse<E> buildResponse(int tipoRespuesta, E e , List<E> listE) {
        ResponseType responseType = ResponseType.fromCode(tipoRespuesta);
        if (responseType != null) {
            return PlantillaResponse.<E>builder()
                    .message(responseType.getMessage())
                    .data(e)
                    .dataList(listE)
                    .rta(responseType.isRta())
                    .httpStatus(responseType.getHttpStatus())
                    .build();
        }

        throw new IllegalArgumentException("Tipo de respuesta no válido: " + tipoRespuesta);
    }
}
