package com.common.lib.utils.enums;


import org.springframework.http.HttpStatus;

public enum ResponseType {

    CREATED(1, MensajesRespuesta.CREADO.getMensaje(), true, HttpStatus.CREATED.value()),
    UPDATED(2, MensajesRespuesta.ACTUALIZADO.getMensaje(), true, HttpStatus.OK.value()),
    FALLO(3, MensajesRespuesta.FALLO.getMensaje(), false, HttpStatus.INTERNAL_SERVER_ERROR.value()),
    EMAIL_NO_ENCONTRADO(4 ,MensajesRespuesta.EMAIL_NO_ENCONTRADO.getMensaje(),false,HttpStatus.OK.value()),
    USER_ISFOUND(5, MensajesRespuesta.USER_ISFOUND.getMensaje(), false, HttpStatus.OK.value()),
    USER_LOGEADO(6, MensajesRespuesta.USER_LOGEADO.getMensaje(), false, HttpStatus.OK.value()),
    EMAIL_VALIDATION_FAIL(7, EmailValidationPattern.EMAIL_VALIDATION_FAIL.getPattern(), false, HttpStatus.BAD_REQUEST.value()),
    EMAIL_NOT_FOUD(8,  EmailValidationPattern.EMAIL_NOT_FOUD.getPattern(), false, HttpStatus.OK.value()),

    PASSWORD_VALIDATION_FAIL(9, PasswordValidationPattern.PASSWORD_VALIDATION_FAIL.getPattern(), false, HttpStatus.BAD_REQUEST.value()),
    GET(10,MensajesRespuesta.GET.getMensaje(),true,HttpStatus.OK.value()),
    NO_ENCONTRADO(11, MensajesRespuesta.NO_ENCONTRADO.getMensaje(), false, HttpStatus.NOT_FOUND.value()),
    FALLO_CREATE_PHONE(12, MensajesRespuesta.FALLO_CREATE_PHONE.getMensaje(), false, HttpStatus.BAD_REQUEST.value()),
    FALLO_CREATE_DATOS_USER(13,MensajesRespuesta.FALLO_CREATE_DATOS_USER.getMensaje() ,false ,HttpStatus.BAD_REQUEST.value()),
    DELETED(14,MensajesRespuesta.DELETED.getMensaje(),true, HttpStatus.OK.value()),
    ID_BUSSINES_NO_ENCONTRADO(15, MensajesRespuesta.ID_BUSSINES_NO_ENCONTRADO.getMensaje(), false, HttpStatus.OK.value()),
    FEING_BUSSINES_FALLO(16,MensajesRespuesta.FEING_BUSSINES_FALLO.getMensaje() ,false ,HttpStatus.SERVICE_UNAVAILABLE.value()),
    BAD_REQUEST(17, MensajesRespuesta.SOLICITUD_INCORRECTA.getMensaje(), false, HttpStatus.BAD_REQUEST.value());



    private final int code;

    private final String message;
    private final boolean isRta;
    private final int httpStatus;

    ResponseType(int code, String message, boolean isRta, int httpStatus) {
        this.code = code;
        this.message = message;
        this.isRta = isRta;
        this.httpStatus = httpStatus;
    }

    public int getCode() {
        return code;
    }


    public String getMessage() {
        return message;
    }

    public boolean isRta() {
        return isRta;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public  static  ResponseType fromMessage(String message){
        for (ResponseType responseType : ResponseType.values()) {
            if (responseType.message.equalsIgnoreCase(message)) {
                return responseType;
            }
        }
        return ResponseType.FALLO;
    }
    public static ResponseType fromCode(int code) {
        for (ResponseType responseType : ResponseType.values()) {
            if (responseType.code == code) {
                return responseType;
            }
        }
        return null;
    }
}
