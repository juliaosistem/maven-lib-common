package com.common.lib.utils;

import org.springframework.http.HttpStatus;

/**
 * Clase para manejar los tipos de respuesta
 * @author Daniel juliao
 * @version 1
 */
public class ResponseType {
    private final int code;
    private final String message;
    private final boolean rta;
    private final HttpStatus httpStatus;

    public ResponseType(int code, String message, boolean rta, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.rta = rta;
        this.httpStatus = httpStatus;
    }

    public static ResponseType fromCode(int code, String message) {
        ResponseType[] responses = {
            new ResponseType(ResponseTypeEnum.CREADO.getCode(), message + "Creado", true, HttpStatus.CREATED),
            new ResponseType(ResponseTypeEnum.ACTUALIZADO.getCode(), message + "Actualizado", true, HttpStatus.OK),
            new ResponseType(ResponseTypeEnum.GET.getCode(), message + "Se Obtuvieron Datos Correctamente", true, HttpStatus.OK),
            new ResponseType(ResponseTypeEnum.DELETE.getCode(), message + "Eliminado Correctamente", true, HttpStatus.OK),
            new ResponseType(ResponseTypeEnum.NOT_FOUND.getCode(), message + "No se encontraron datos", false, HttpStatus.NOT_FOUND),
            new ResponseType(ResponseTypeEnum.FALLO.getCode(), message + "Fallo", false, HttpStatus.INTERNAL_SERVER_ERROR),
            new ResponseType(ResponseTypeEnum.BAD_REQUEST.getCode(), message + "BAD REQUEST", false, HttpStatus.BAD_REQUEST)
        };

        for (ResponseType response : responses) {
            if (response.getCode() == code) {
                return response;
            }
        }

        return new ResponseType(0, "Desconocido", false, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseType fromCode(int code) {
        return fromCode(code, "");
    }

    // Getters
    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public boolean isRta() {
        return rta;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
} 