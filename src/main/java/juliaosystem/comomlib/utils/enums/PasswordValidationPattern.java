package juliaosystem.comomlib.utils.enums;

public enum PasswordValidationPattern {
    VALID("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"),
    PASSWORD_VALIDATION_FAIL("La contraseña debe tener al menos 8 caracteres, una combinación de letras mayúsculas y minúsculas, al menos un dígito y al menos un carácter especial (@#$%^&+=)");

    private final String pattern;

    PasswordValidationPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }
}