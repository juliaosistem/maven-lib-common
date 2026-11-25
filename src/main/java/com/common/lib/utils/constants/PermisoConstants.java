package com.common.lib.utils.constants;

/**
 * Constantes de permisos del sistema para uso en validaciones y asignaciones.
 * Reemplazan el uso de PermisoEnum para permitir permisos dinámicos desde BD.
 *
 * @author Twins Code
 * @version 1.0
 */
public final class PermisoConstants {

    private PermisoConstants() {
        throw new AssertionError("No se permite instanciar PermisoConstants");
    }

    // Permiso comodín
    public static final String TODOS = "TODOS";

    // Permisos de usuarios
    public static final String VER_USUARIOS = "VER_USUARIOS";
    public static final String REGISTRAR_USUARIOS = "REGISTRAR_USUARIOS";
    public static final String ACTUALIZAR_USUARIOS = "ACTUALIZAR_USUARIOS";
    public static final String ELIMINAR_USUARIOS = "ELIMINAR_USUARIOS";
    public static final String GESTIONAR_PROPIO_USUARIO = "GESTIONAR_PROPIO_USUARIO";

    // Permisos de empleados
    public static final String VER_EMPLEADOS = "VER_EMPLEADOS";

    // Permisos de roles
    public static final String VER_ROLES = "VER_ROLES";
    public static final String CREAR_ROLES = "CREAR_ROLES";
    public static final String ACTUALIZAR_ROLES = "ACTUALIZAR_ROLES";
    public static final String ELIMINAR_ROLES = "ELIMINAR_ROLES";

    // Permisos de trabajos
    public static final String CREAR_TRABAJOS = "CREAR_TRABAJOS";
    public static final String ACTUALIZAR_TRABAJOS = "ACTUALIZAR_TRABAJOS";
    public static final String ELIMINAR_TRABAJOS = "ELIMINAR_TRABAJOS";
    public static final String VER_TRABAJOS_POR_EMPLEADO = "VER_TRABAJOS_POR_EMPLEADO";
    public static final String VER_TODOS_LOS_TRABAJOS = "VER_TODOS_LOS_TRABAJOS";

    // Permisos de productos
    public static final String VER_PRODUCTOS = "VER_PRODUCTOS";
    public static final String CREAR_PRODUCTOS = "CREAR_PRODUCTOS";
    public static final String ACTUALIZAR_PRODUCTOS = "ACTUALIZAR_PRODUCTOS";
    public static final String ELIMINAR_PRODUCTOS = "ELIMINAR_PRODUCTOS";

    // Permisos de pedidos
    public static final String VER_PEDIDOS = "VER_PEDIDOS";
    public static final String VER_PEDIDOS_GESTIONADOS_POR_EMPLEADO = "VER_PEDIDOS_GESTIONADOS_POR_EMPLEADO";
    public static final String CREAR_PEDIDOS = "CREAR_PEDIDOS";
    public static final String ACTUALIZAR_PEDIDOS = "ACTUALIZAR_PEDIDOS";
    public static final String ACTUALIZAR_PEDIDOS_GESTIONADOS_POR_EMPLEADO = "ACTUALIZAR_PEDIDOS_GESTIONADOS_POR_EMPLEADO";
    public static final String ELIMINAR_PEDIDOS = "ELIMINAR_PEDIDOS";

    // Permisos de integraciones
    public static final String ADMINISTRAR_WHATSAPP = "ADMINISTRAR_WHATSAPP";
    public static final String MODULO_DE_WHATSAPP = "MODULO_DE_WHATSAPP";

    // Permisos de fabricación
    public static final String VER_ORDENES_FABRICACION = "VER_ORDENES_FABRICACION";
    public static final String CREAR_ORDENES_FABRICACION = "CREAR_ORDENES_FABRICACION";
    public static final String ACTUALIZAR_ORDENES_FABRICACION = "ACTUALIZAR_ORDENES_FABRICACION";
    public static final String EJECUTAR_CORTE = "EJECUTAR_CORTE";
    public static final String EJECUTAR_COSTURA = "EJECUTAR_COSTURA";
    public static final String VER_INVENTARIO = "VER_INVENTARIO";
    public static final String GESTIONAR_INVENTARIO = "GESTIONAR_INVENTARIO";

    // Arreglos para validaciones
    public static final String[] ADMIN_PERMISSIONS = {
        VER_USUARIOS, REGISTRAR_USUARIOS, ACTUALIZAR_USUARIOS, ELIMINAR_USUARIOS,
        VER_ROLES, CREAR_ROLES, ACTUALIZAR_ROLES, ELIMINAR_ROLES,
        VER_PRODUCTOS, CREAR_PRODUCTOS, ACTUALIZAR_PRODUCTOS, ELIMINAR_PRODUCTOS,
        VER_PEDIDOS, CREAR_PEDIDOS, ACTUALIZAR_PEDIDOS, ELIMINAR_PEDIDOS,
        TODOS
    };

    public static final String[] USUARIO_DEFAULT_PERMISSIONS = {
        GESTIONAR_PROPIO_USUARIO, VER_PRODUCTOS, CREAR_PEDIDOS, VER_PEDIDOS
    };

    public static final String[] PRODUCTION_PERMISSIONS = {
        VER_ORDENES_FABRICACION, EJECUTAR_CORTE, EJECUTAR_COSTURA,
        VER_INVENTARIO, GESTIONAR_INVENTARIO
    };

    /**
     * Valida si un permiso es válido y conocido por el sistema.
     *
     * @param permisoStr nombre del permiso a validar
     * @return true si el permiso existe
     */
    public static boolean isValidPermiso(String permisoStr) {
        if (permisoStr == null || permisoStr.isBlank()) {
            return false;
        }
        try {
            return permisoStr.equalsIgnoreCase(TODOS) ||
                   permisoStr.equalsIgnoreCase(VER_USUARIOS) ||
                   permisoStr.equalsIgnoreCase(REGISTRAR_USUARIOS) ||
                   permisoStr.equalsIgnoreCase(ACTUALIZAR_USUARIOS) ||
                   permisoStr.equalsIgnoreCase(ELIMINAR_USUARIOS) ||
                   permisoStr.equalsIgnoreCase(GESTIONAR_PROPIO_USUARIO) ||
                   permisoStr.equalsIgnoreCase(VER_EMPLEADOS) ||
                   permisoStr.equalsIgnoreCase(VER_ROLES) ||
                   permisoStr.equalsIgnoreCase(CREAR_ROLES) ||
                   permisoStr.equalsIgnoreCase(ACTUALIZAR_ROLES) ||
                   permisoStr.equalsIgnoreCase(ELIMINAR_ROLES) ||
                   permisoStr.equalsIgnoreCase(CREAR_TRABAJOS) ||
                   permisoStr.equalsIgnoreCase(ACTUALIZAR_TRABAJOS) ||
                   permisoStr.equalsIgnoreCase(ELIMINAR_TRABAJOS) ||
                   permisoStr.equalsIgnoreCase(VER_TRABAJOS_POR_EMPLEADO) ||
                   permisoStr.equalsIgnoreCase(VER_TODOS_LOS_TRABAJOS) ||
                   permisoStr.equalsIgnoreCase(VER_PRODUCTOS) ||
                   permisoStr.equalsIgnoreCase(CREAR_PRODUCTOS) ||
                   permisoStr.equalsIgnoreCase(ACTUALIZAR_PRODUCTOS) ||
                   permisoStr.equalsIgnoreCase(ELIMINAR_PRODUCTOS) ||
                   permisoStr.equalsIgnoreCase(VER_PEDIDOS) ||
                   permisoStr.equalsIgnoreCase(VER_PEDIDOS_GESTIONADOS_POR_EMPLEADO) ||
                   permisoStr.equalsIgnoreCase(CREAR_PEDIDOS) ||
                   permisoStr.equalsIgnoreCase(ACTUALIZAR_PEDIDOS) ||
                   permisoStr.equalsIgnoreCase(ACTUALIZAR_PEDIDOS_GESTIONADOS_POR_EMPLEADO) ||
                   permisoStr.equalsIgnoreCase(ELIMINAR_PEDIDOS) ||
                   permisoStr.equalsIgnoreCase(ADMINISTRAR_WHATSAPP) ||
                   permisoStr.equalsIgnoreCase(MODULO_DE_WHATSAPP) ||
                   permisoStr.equalsIgnoreCase(VER_ORDENES_FABRICACION) ||
                   permisoStr.equalsIgnoreCase(CREAR_ORDENES_FABRICACION) ||
                   permisoStr.equalsIgnoreCase(ACTUALIZAR_ORDENES_FABRICACION) ||
                   permisoStr.equalsIgnoreCase(EJECUTAR_CORTE) ||
                   permisoStr.equalsIgnoreCase(EJECUTAR_COSTURA) ||
                   permisoStr.equalsIgnoreCase(VER_INVENTARIO) ||
                   permisoStr.equalsIgnoreCase(GESTIONAR_INVENTARIO);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Normaliza el nombre del permiso al formato canónico del sistema.
     *
     * @param permisoStr nombre del permiso a normalizar
     * @return permiso en mayúsculas o null si no es válido
     */
    public static String normalizePermiso(String permisoStr) {
        if (!isValidPermiso(permisoStr)) {
            return null;
        }
        return permisoStr.trim().toUpperCase();
    }
}

