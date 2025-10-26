package com.common.lib.utils;

/**
 * Enum para los tipos de respuesta
 * @author Daniel juliao
 * @version 1
 */
public enum ResponseTypeEnum {
    CREADO(1),
    ACTUALIZADO(2),
    FALLO(3),
    GET(4),
    NOT_FOUND(5),
    DELETE(6),
    BAD_REQUEST(7);

    private final int code;

    ResponseTypeEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
} 