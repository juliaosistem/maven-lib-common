package com.common.lib.api.controller;

import com.common.lib.infraestructure.services.primary.CrudPrimaryService;
import com.common.lib.utils.PlantillaResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Controlador CRUD base plug-and-play.
 */
public class DefaultCrudController<RES, RQ, E, I> implements CrudController<RES, RQ> {

    protected final CrudPrimaryService<RES, RQ, E, I> primaryService;

    public DefaultCrudController(CrudPrimaryService<RES, RQ, E, I> primaryService) {
        this.primaryService = primaryService;
    }

    @Override
    public ResponseEntity<PlantillaResponse<RES>> add(RQ request, String id, String ip, String dominio, String usuario, Long idbusiness, String proceso, String topic, String token) {
        if (id == null || id.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new PlantillaResponse<>(false, "No llegó parámetro id en los headers", HttpStatus.BAD_REQUEST, null, null));
        }
        PlantillaResponse<RES> res = primaryService.add(request);
        return ResponseEntity.status(res.getHttpStatus() == null ? HttpStatus.OK : res.getHttpStatus()).body(res);
    }

    @Override
    public ResponseEntity<PlantillaResponse<RES>> all(String id, String ip, String dominio, String usuario, Long idbusiness, String proceso, String topic, String token, Map<String, String> filters) {
        PlantillaResponse<RES> res;
        if (id != null && !id.isBlank()) {
            res = primaryService.byId(castId(id));
        } else if (idbusiness != null) {
            res = primaryService.byIdBusiness(idbusiness);
        } else {
            res = primaryService.all();
        }
        return ResponseEntity.status(res.getHttpStatus() == null ? HttpStatus.OK : res.getHttpStatus()).body(res);
    }

    @Override
    public Mono<PlantillaResponse<RES>> update(RQ request, String id, String ip, String dominio, String usuario, Long idbusiness, String proceso, String topic, String token) {
        if (id == null || id.isBlank()) {
            return Mono.just(new PlantillaResponse<>(false, "No llegó parámetro id en los headers", HttpStatus.BAD_REQUEST, null, null));
        }
        return Mono.just(primaryService.update(request));
    }

    @Override
    public ResponseEntity<PlantillaResponse<RES>> delete(String id, String ip, String dominio, String usuario, Long idbusiness, String proceso, String topic, String token) {
        PlantillaResponse<RES> res = primaryService.delete(castId(id));
        return ResponseEntity.status(res.getHttpStatus() == null ? HttpStatus.OK : res.getHttpStatus()).body(res);
    }

    @SuppressWarnings("unchecked")
    protected I castId(String id) {
        try {
            Long num = Long.parseLong(id);
            return (I) num;
        } catch (Exception ex) {
            return (I) id;
        }
    }
}


