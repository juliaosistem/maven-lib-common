package com.common.lib.api.controller;

import com.common.lib.api.mappers.GenericMapper;

import com.common.lib.api.mappers.MapperConRequest;
import com.common.lib.infraestructure.services.primary.CrudPrimaryService;
import com.common.lib.utils.PlantillaResponse;
import com.common.lib.utils.RequestHeaders;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
public class DefaultCrudController<RES, RQ, E, I> {

    protected final CrudPrimaryService<RES, RQ,  I> primaryService;
    /**
     * Mapper genérico opcional para usar en controladores concretos que requieran transformar
     * entre request/entidad/respuesta manualmente. No es obligatorio para el flujo base.
     */
    protected final MapperConRequest<E,RES,RQ> mapper;

    public DefaultCrudController(CrudPrimaryService<RES, RQ, I> primaryService) {
        this.primaryService = primaryService;
        this.mapper = null;
    }

    /**
     * Constructor alterno que permite inyectar un {@link GenericMapper} cuando el controlador
     * concreto lo necesita para lógica adicional de mapeo (similar a la capacidad del
     * DefaultCrudController en TypeScript de configurar colaboradores opcionales).
     * Se mantiene el constructor original para compatibilidad.
     */
    public DefaultCrudController(CrudPrimaryService<RES, RQ, I> primaryService,
                                 MapperConRequest<E,RES,RQ> mapper) {
        this.primaryService = primaryService;
        this.mapper = mapper;
    }

    @PostMapping("/add")
    public Mono<PlantillaResponse<RES>> add(
            @RequestBody RQ request,
            @RequestHeader HttpHeaders headers
    ) {
        RequestHeaders<I> rh = RequestHeaders.from(headers);
        return primaryService.add(request, rh.getId(), rh.getTopic());
    }

    @GetMapping("/all")
    public Mono<PlantillaResponse<RES>> all(
            @RequestHeader HttpHeaders headers,
            @RequestParam(required = false) Map<String, String> filters
    ) {
        RequestHeaders<I> rh = RequestHeaders.from(headers);
        if (rh.getIdbusiness() == null) {
            return Mono.just(new PlantillaResponse<>(false, "No llegó parámetro idBussines", HttpStatus.BAD_REQUEST, null, null));
        }
        return primaryService.all(rh.getTopic(), rh.getId(), rh.getIdbusiness(), filters);
    }

    @PutMapping("/update")
    public Mono<PlantillaResponse<RES>> update(
            @RequestBody RQ request,
            @RequestHeader HttpHeaders headers
    ) {
        RequestHeaders<I> rh = RequestHeaders.from(headers);
        if (rh.getId() == null  || rh.getTopic() == null || rh.getTopic().isBlank()) {
            return Mono.just(new PlantillaResponse<>(false, "No llegó parámetro id o topic en los headers", HttpStatus.BAD_REQUEST, null, null));
        }
        return primaryService.update(request, (I) rh.getId(), rh.getTopic());
    }

    @DeleteMapping("/delete")
    public Mono<PlantillaResponse<RES>> delete(
            @RequestHeader HttpHeaders headers
    ) {
        RequestHeaders<I> rh = RequestHeaders.from(headers);
        if (rh.getId() == null ) {
            return Mono.just(new PlantillaResponse<>(false, "No llegó parámetro id", HttpStatus.BAD_REQUEST, null, null));
        }

        return primaryService.delete(rh.getId(), rh.getTopic());
    }
}
   
       