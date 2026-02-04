package com.common.lib.api.controller;

import com.common.lib.api.mappers.GenericMapper;

import com.common.lib.api.mappers.MapperConRequest;
import com.common.lib.infraestructure.services.primary.CrudPrimaryService;
import com.common.lib.utils.PlantillaResponse;
import com.common.lib.utils.RequestHeaders;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Controlador CRUD base reactivo para consultar cualquier entidad
 * @param <RES>  clase respuesta
 * @param <RQ>  clase request
 * @param <E>   clase entidad
 * @author  Daniel Juliao
 */
public class DefaultCrudController<RES, RQ, E> implements CrudController<RES, RQ> {

    protected final CrudPrimaryService<RES, RQ> primaryService;
    /**
     * Mapper genérico opcional para usar en controladores concretos que requieran transformar
     * entre request/entidad/respuesta manualmente. No es obligatorio para el flujo base.
     */
    protected final MapperConRequest<E,RES,RQ> mapper;

    public DefaultCrudController(CrudPrimaryService<RES, RQ> primaryService) {
        this.primaryService = primaryService;
        this.mapper = null;
    }

    /**
     * Constructor alterno que permite inyectar un {@link GenericMapper} cuando el controlador
     * concreto lo necesita para lógica adicional de mapeo (similar a la capacidad del
     * DefaultCrudController en TypeScript de configurar colaboradores opcionales).
     * Se mantiene el constructor original para compatibilidad.
     */
    public DefaultCrudController(CrudPrimaryService<RES, RQ> primaryService,
                                 MapperConRequest<E,RES,RQ> mapper) {
        this.primaryService = primaryService;
        this.mapper = mapper;
    }

    @PostMapping("/add")
    public Mono<ResponseEntity<PlantillaResponse<RES>>> add(
            @RequestBody RQ request,
            @RequestHeader HttpHeaders headers
    ) {
        var rh = RequestHeaders.from(headers);
        if (rh.getIdBusiness() == null) {
            PlantillaResponse<RES> body = new PlantillaResponse<>(false, "No llegó parámetro idBussines", HttpStatus.BAD_REQUEST.value(), null, null);
            return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body));
        }

        return primaryService.add(request, headers)
                .map(resp -> {
                    int statusCode = resp.getHttpStatus();
                    HttpStatus status = (statusCode > 0) ? HttpStatus.valueOf(statusCode) : HttpStatus.OK;
                    return ResponseEntity.status(status).body(resp);
                })
                .onErrorResume(ex -> {
                    Throwable cause = ex;
                    while (cause != null) {
                        if (cause instanceof feign.RetryableException) {
                            PlantillaResponse<RES> body = new PlantillaResponse<RES>(false, "El endpoint business no está disponible en este momento", HttpStatus.SERVICE_UNAVAILABLE.value(), null, null);
                            return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(body));
                        }
                        cause = cause.getCause();
                    }
                    PlantillaResponse<RES> body = new PlantillaResponse<RES>(false, "Error interno", HttpStatus.INTERNAL_SERVER_ERROR.value(), null, null);
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body));
                });
    }

    @GetMapping("/all")
    public Mono<ResponseEntity<PlantillaResponse<RES>>> all(
            @RequestHeader HttpHeaders headers,
            @RequestParam(required = false) Map<String, String> filters,
            @RequestParam(required= false) Object id
    ) {

        return primaryService.all(id, headers, filters)
                .map(resp -> {
                    int statusCode = resp.getHttpStatus();
                    HttpStatus status = (statusCode > 0) ? HttpStatus.valueOf(statusCode) : HttpStatus.OK;
                    return ResponseEntity.status(status).body(resp);
                })
                .onErrorResume(ex -> {
                    PlantillaResponse<RES> body = new PlantillaResponse<>(false, "Error interno", HttpStatus.INTERNAL_SERVER_ERROR.value(), null, null);
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body));
                });
    }

    @PutMapping("/update")
    public Mono<ResponseEntity<PlantillaResponse<RES>>> update(
            @RequestBody RQ request,
            @RequestHeader HttpHeaders headers,
            @RequestParam Object id

    ) {
        if (id == null) {
            PlantillaResponse<RES> body = new PlantillaResponse<>(false, "No llegó parámetro id o topic en los headers", HttpStatus.BAD_REQUEST.value(), null, null);
            return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body));
        }
        return primaryService.update(id, request, headers)
                .map(resp -> {
                    int statusCode = resp.getHttpStatus();
                    HttpStatus status = (statusCode > 0) ? HttpStatus.valueOf(statusCode) : HttpStatus.OK;
                    return ResponseEntity.status(status).body(resp);
                })
                .onErrorResume(ex -> {
                    PlantillaResponse<RES> body = new PlantillaResponse<>(false, "Error interno", HttpStatus.INTERNAL_SERVER_ERROR.value(), null, null);
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body));
                });
    }

    @DeleteMapping("/delete")
    public Mono<ResponseEntity<PlantillaResponse<RES>>> delete(
            @RequestHeader HttpHeaders headers,
            @RequestParam(required = false) Object id
    ) {

        if (id == null ) {
            PlantillaResponse<RES> body = new PlantillaResponse<>(false, "No llegó parámetro id", HttpStatus.BAD_REQUEST.value(), null, null);
            return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body));
        }

        return primaryService.delete(id, headers)
                .map(resp -> {
                    int statusCode = resp.getHttpStatus();
                    HttpStatus status = (statusCode > 0) ? HttpStatus.valueOf(statusCode) : HttpStatus.OK;
                    return ResponseEntity.status(status).body(resp);
                })
                .onErrorResume(ex -> {
                    PlantillaResponse<RES> body = new PlantillaResponse<>(false, "Error interno", HttpStatus.INTERNAL_SERVER_ERROR.value(), null, null);
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body));
                });
    }
}

