package com.common.lib.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;

/**
 * Wrapper tipado para headers comunes de CRUD.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestHeaders{

    private String ip;
    private String dominio;
    private String usuario;
    private Integer idBusiness;
    private String proceso;
    private String topic;
    private String token;

    public static RequestHeaders from(HttpHeaders headers) {
        RequestHeaders rh = new RequestHeaders();
        rh.ip = headers.getFirst("ip");
        rh.dominio = headers.getFirst("dominio");
        rh.usuario = headers.getFirst("usuario");
        String idb = headers.getFirst("idBusiness");
        if (idb == null) {
            idb = headers.getFirst("idBusiness");
        }
        rh.idBusiness = idb != null ? Integer.parseInt(idb) : null;
        rh.proceso = headers.getFirst("proceso");
        rh.topic = headers.getFirst("topic");
        String auth = headers.getFirst("Authorization");
        if (auth != null && auth.toLowerCase().startsWith("bearer ")) {
            rh.token = auth.substring(7);
        } else {
            rh.token = headers.getFirst("token");
        }
        return rh;
    }

}


