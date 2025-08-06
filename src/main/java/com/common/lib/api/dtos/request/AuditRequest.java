package com.common.lib.api.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO de request para auditoría
 * 
 * @author Daniel juliao
 * @version 1
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuditRequest {
    private UUID id;
    private String ip;
    private String dominio;
    private String usuario;
    private Long idBusiness;
    private String proceso;
    private String logs;
}
