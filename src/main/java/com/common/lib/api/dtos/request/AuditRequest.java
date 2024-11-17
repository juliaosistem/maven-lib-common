package com.common.lib.api.dtos.request;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AuditRequest {

    private UUID id;
    private String ip;
    private String dominio;
    private String usuario;
    private Long idBussines;
    private String proceso;
    private LocalDateTime timestamp;
    private String logs;

}
