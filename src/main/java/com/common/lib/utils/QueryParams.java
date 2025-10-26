package com.common.lib.utils;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Clase para manejar los parámetros de consulta
 * @author Daniel juliao
 * @version 1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryParams {
    private String id;
    private String ip;
    private String dominio;
    private String usuario;
    private Integer idbusiness;
    private String proceso;
    private String topic;
    private String token;
} 