package com.common.lib.utils.enums;




public enum MensajesRespuesta {
    CREADO("Creado(a) correctamente"),
    NO_ENCONTRADO("No se encontraron datos"),
    GET("Se obtuvieron datos correctamente"),
    ACTUALIZADO("Actualizado correctamente"),
    ELIMINADO("Eliminado correctamente"),
    EMAIL_NO_ENCONTRADO("Email o contraseña inválida"),
    FALLO("Algo salió mal"),

    USER_ISFOUND("El usuario ya se encuentra registrado"),
    USER_LOGEADO("Datos Correctos"),
    FALLO_CREATE_PHONE("Fallo al crear telefono"),
    FALLO_CREATE_DATOS_USER("Fallo al crear datos de usuario"),
    DELETED("Eliminado(a) correctamente");

    private final String mensaje;

    MensajesRespuesta(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }
}
