package com.common.lib.utils.constants;

/**
 * Constantes de roles del sistema para uso en validaciones y asignaciones.
 * Reemplazan el uso de RolEnum para permitir roles dinámicos desde BD.
 *
 * @author Twins Code
 * @version 1.0
 */
public final class RoleConstants {

    private RoleConstants() {
        throw new AssertionError("No se permite instanciar RoleConstants");
    }

    // Roles del sistema
    public static final String SUPER_ADMIN = "SUPER_ADMIN";
    public static final String ADMINISTRADOR = "ADMINISTRADOR";
    public static final String ADMINISTRADOR_SHORT = "ADMIN";
    public static final String USUARIO = "USUARIO";
    public static final String COSTURERO = "COSTURERO";
    public static final String COORDINADOR_BODEGA = "COORDINADOR_BODEGA";
    public static final String VENDEDOR = "VENDEDOR";

    // Roles especializados de fabricación
    public static final String OPERARIO_CORTE = "OPERARIO_CORTE";
    public static final String OPERARIO_COSTURA = "OPERARIO_COSTURA";
    public static final String SUPERVISOR_PRODUCCION = "SUPERVISOR_PRODUCCION";
    public static final String INSPECTOR_CALIDAD = "INSPECTOR_CALIDAD";

    // Arreglos para validaciones rápidas
    public static final String[] ADMIN_ROLES = {
        SUPER_ADMIN, ADMINISTRADOR, ADMINISTRADOR_SHORT
    };

    public static final String[] PRODUCTION_ROLES = {
        COSTURERO, OPERARIO_CORTE, OPERARIO_COSTURA, SUPERVISOR_PRODUCCION
    };

    public static final String[] ALL_ROLES = {
        SUPER_ADMIN, ADMINISTRADOR, USUARIO, COSTURERO,
        COORDINADOR_BODEGA, VENDEDOR, OPERARIO_CORTE, OPERARIO_COSTURA,
        SUPERVISOR_PRODUCCION, INSPECTOR_CALIDAD
    };

    /**
     * Valida si un rol es válido según el catálogo del sistema.
     *
     * @param roleStr nombre del rol a validar
     * @return true si el rol existe en los roles conocidos
     */
    public static boolean isValidRole(String roleStr) {
        if (roleStr == null || roleStr.isBlank()) {
            return false;
        }
        for (String role : ALL_ROLES) {
            if (role.equalsIgnoreCase(roleStr)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Normaliza el nombre del rol al formato canónico del sistema.
     *
     * @param roleStr nombre del rol a normalizar
     * @return rol en mayúsculas o null si no es válido
     */
    public static String normalizeRole(String roleStr) {
        if (!isValidRole(roleStr)) {
            return null;
        }
        return roleStr.trim().toUpperCase();
    }
}

