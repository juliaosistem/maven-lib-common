package com.common.lib.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración para las utilidades comunes
 * @author Daniel juliao
 * @version 1
 */
@Configuration
public class CommonUtilsConfig {
    
    /**
     * Bean para las respuestas genéricas
     * @return Instancia de Responses
     */
    @Bean
    public Responses<Object> responses() {
        return new Responses<>();
    }
} 