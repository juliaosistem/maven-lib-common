package com.common.lib.infraestructure.modules;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * Módulo de utilidades que proporciona funcionalidades comunes para la aplicación Spring Boot.
 * Este módulo incluye configuraciones comunes y beans compartidos entre microservicios.
 * 
 * @author Daniel juliao
 * @version 1
 */
@Configuration
@ComponentScan(
    basePackages = "com.common.lib",
    excludeFilters = {
        @ComponentScan.Filter(
            type = FilterType.REGEX,
            pattern = ".*DefaultAdapterKafkaAndRedis.*"
        ),
        @ComponentScan.Filter(
            type = FilterType.REGEX,
            pattern = ".*McsKafkaFeignClient.*"
        ),
        @ComponentScan.Filter(
            type = FilterType.REGEX,
            pattern = ".*RedisFeignClient.*"
        )
    }
)
public class UtilsModule {
    
    /**
     * Configuración base para utilidades comunes.
     * Este módulo se puede importar en otros microservicios para obtener
     * funcionalidades compartidas como DTOs, mappers, y utilidades.
     * 
     * NOTA: Los adaptadores de Kafka y Redis han sido excluidos temporalmente
     * para permitir pruebas de CRUD sin dependencias externas.
     */
    
    // Aquí se pueden agregar beans comunes que necesiten ser compartidos
    // entre diferentes microservicios que usen esta librería común
    
} 