package com.common.lib.api.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Clase de respuesta para auditoría
 * 
 * @author Daniel juliao
 * @version 1
 */
@Setter
@Getter
public class AuditResponse {
    private UUID id;
    private String proceso;

    public AuditResponse() {
    }

    public AuditResponse(UUID id, String proceso) {
        this.id = id;
        this.proceso = proceso;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UUID id;
        private String proceso;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder proceso(String proceso) {
            this.proceso = proceso;
            return this;
        }

        public AuditResponse build() {
            return new AuditResponse(id, proceso);
        }
    }
} 