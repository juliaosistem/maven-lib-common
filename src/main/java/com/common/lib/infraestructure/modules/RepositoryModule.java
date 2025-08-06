package com.common.lib.infraestructure.modules;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Módulo de repositorio que proporciona configuraciones comunes para JPA.
 * Este módulo habilita los repositorios JPA y proporciona configuraciones
 * compartidas para el acceso a datos.
 * 
 * @author Daniel juliao
 * @version 1
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.common.lib.infraestructure.repository")
@EntityScan(basePackages = "com.common.lib.infraestructure.entitis")
public class RepositoryModule {
    
    /**
     * Configuración base para repositorios JPA.
     * Este módulo se puede importar en otros microservicios para obtener
     * funcionalidades de repositorio compartidas.
     */
    
    // Aquí se pueden agregar configuraciones específicas para repositorios
    // que necesiten ser compartidas entre diferentes microservicios
    
} 