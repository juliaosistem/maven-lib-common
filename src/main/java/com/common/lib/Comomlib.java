package com.common.lib;

import com.common.lib.infraestructure.modules.RepositoryModule;
import com.common.lib.infraestructure.modules.UtilsModule;

/**
 * Clase principal de la librería común.
 * Esta clase actúa como punto de entrada para la librería común
 * y proporciona acceso a los módulos compartidos.
 * 
 * @author Daniel juliao
 * @version 1
 */
public class Comomlib {
    
    /**
     * Obtiene la clase del módulo de utilidades.
     * @return Clase del módulo de utilidades
     */
    public static Class<UtilsModule> getUtilsModule() {
        return UtilsModule.class;
    }
    
    /**
     * Obtiene la clase del módulo de repositorio.
     * @return Clase del módulo de repositorio
     */
    public static Class<RepositoryModule> getRepositoryModule() {
        return RepositoryModule.class;
    }
}