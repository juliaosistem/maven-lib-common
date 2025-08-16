package com.common.lib.utils;

import org.springframework.http.HttpHeaders;

/**
 * Wrapper tipado para headers comunes de CRUD.
 */
public class RequestHeaders<I>{
    private I id;
    private String ip;
    private String dominio;
    private String usuario;
    private Integer idbusiness;
    private String proceso;
    private String topic;
    private String token;

    public static RequestHeaders from(HttpHeaders headers) {
        RequestHeaders rh = new RequestHeaders();
        rh.id = headers.getFirst("id");
        rh.ip = headers.getFirst("ip");
        rh.dominio = headers.getFirst("dominio");
        rh.usuario = headers.getFirst("usuario");
        String idb = headers.getFirst("idbusiness");
        if (idb == null) {
            idb = headers.getFirst("idBusiness");
        }
        rh.idbusiness = idb != null ? Integer.parseInt(idb) : null;
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

   

    public I getId() { return id; }
    public String getIp() { return ip; }
    public String getDominio() { return dominio; }
    public String getUsuario() { return usuario; }
    public Integer getIdbusiness() { return idbusiness; }
    public String getProceso() { return proceso; }
    public String getTopic() { return topic; }
    public String getToken() { return token; }
}


