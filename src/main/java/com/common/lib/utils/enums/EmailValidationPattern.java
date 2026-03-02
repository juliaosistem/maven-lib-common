package com.common.lib.utils.enums;

public enum EmailValidationPattern {
    VALID("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"),
    EMAIL_VALIDATION_FAIL("Correo electrónico tiene un formato inválido"),
    EMAIL_NOT_FOUD("El correo electrónico o contraseña son invalidos"),
    NO_ENCONTRADO("No se encontraron datos");
    private final String pattern;

    EmailValidationPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }
}
