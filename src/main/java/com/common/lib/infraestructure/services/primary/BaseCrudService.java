package com.common.lib.infraestructure.services.primary;

import com.common.lib.infraestructure.repository.DefaultRepository;
import com.common.lib.utils.PlantillaResponse;
import com.common.lib.utils.ResponseType;
import com.common.lib.utils.ResponseTypeEnum;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

/**
 * Clase base para servicios CRUD que implementa operaciones comunes.
 * Sigue los principios SOLID y arquitectura hexagonal.
 * 
 * @author Daniel Juliao
 * @param <RES> Clase de respuesta
 * @param <RQ> Clase de request
 * @param <E> Clase entidad
 * @param <I> Tipo del ID de la entidad
 * @version 3
 */
public abstract class BaseCrudService<RES, RQ, E, I> implements CrudPrimaryService<RES, RQ, E, I> {

    protected final DefaultRepository<E, I> repository;

    protected BaseCrudService(DefaultRepository<E, I> repository) {
        this.repository = repository;
    }

    /**
     * Obtiene todas las entidades.
     */
    @Override
    public PlantillaResponse<RES> all() {
        try {
            Page<E> entities = repository.findAllWithPagination();
            List<RES> responses = mapToResponseList(entities.getContent());
            
            @SuppressWarnings("unchecked")
            RES[] arr = (RES[]) responses.toArray((Object[]) new Object[responses.size()]);
            return PlantillaResponse.<RES>builder()
                .rta(true)
                .message("Entidades obtenidas exitosamente")
                .httpStatus(ResponseType.fromCode(ResponseTypeEnum.GET.getCode()).getHttpStatus())
                .dataList(arr)
                .build();
        } catch (Exception e) {
            return createErrorResponse("Error al obtener entidades: " + e.getMessage());
        }
    }

    /**
     * Obtiene una entidad por su ID.
     */
    @Override
    public PlantillaResponse<RES> byId(I id) {
        try {
            Optional<E> entityOpt = repository.findByIdSafe(id);
            if (entityOpt.isPresent()) {
                RES response = mapToResponse(entityOpt.get());
                return PlantillaResponse.<RES>builder()
                    .rta(true)
                    .message("Entidad obtenida exitosamente")
                    .httpStatus(ResponseType.fromCode(ResponseTypeEnum.GET.getCode()).getHttpStatus())
                    .data(response)
                    .build();
            } else {
                return createNotFoundResponse("Entidad no encontrada con ID: " + id);
            }
        } catch (Exception e) {
            return createErrorResponse("Error al obtener entidad: " + e.getMessage());
        }
    }

    /**
     * Obtiene entidades por idBusiness si el repositorio/entidad lo soporta.
     */
    @Override
    public PlantillaResponse<RES> byIdBusiness(Long idBusiness) {
        try {
            // Filtro por Specification si el repositorio soporta JPA Specifications
            List<E> entities;
            try {
                entities = repository.findAll((root, query, cb) -> cb.equal(root.get("idBusiness"), idBusiness));
            } catch (Exception ex) {
                return createBadRequestResponse("La entidad no soporta filtro por idBusiness");
            }
            List<RES> responses = mapToResponseList(entities);
            @SuppressWarnings("unchecked")
            RES[] arr = (RES[]) responses.toArray((Object[]) new Object[responses.size()]);
            return PlantillaResponse.<RES>builder()
                .rta(!responses.isEmpty())
                .message(responses.isEmpty() ? "No se encontraron datos" : "Entidades obtenidas por idBusiness")
                .httpStatus(ResponseType.fromCode(
                    responses.isEmpty() ? ResponseTypeEnum.NOT_FOUND.getCode() : ResponseTypeEnum.GET.getCode()
                ).getHttpStatus())
                .dataList(arr)
                .build();
        } catch (Exception e) {
            return createErrorResponse("Error al consultar por idBusiness: " + e.getMessage());
        }
    }

    /**
     * Crea una nueva entidad.
     */
    @Override
    public PlantillaResponse<RES> add(RQ request) {
        try {
            E entity = mapToEntity(request);
            E savedEntity = repository.save(entity);
            RES response = mapToResponse(savedEntity);
            
            return PlantillaResponse.<RES>builder()
                .rta(true)
                .message("Entidad creada exitosamente")
                .httpStatus(ResponseType.fromCode(ResponseTypeEnum.CREADO.getCode()).getHttpStatus())
                .data(response)
                .build();
        } catch (Exception e) {
            return createErrorResponse("Error al crear entidad: " + e.getMessage());
        }
    }

    /**
     * Actualiza una entidad existente.
     */
    @Override
    public PlantillaResponse<RES> update(RQ request) {
        try {
            I id = getIdFromRequest(request);
            if (id == null) {
                return createBadRequestResponse("ID es requerido para actualizar");
            }
            
            if (!repository.existsByIdSafe(id)) {
                return createNotFoundResponse("Entidad no encontrada con ID: " + id);
            }
            
            E entity = mapToEntity(request);
            E updatedEntity = repository.save(entity);
            RES response = mapToResponse(updatedEntity);
            
            return PlantillaResponse.<RES>builder()
                .rta(true)
                .message("Entidad actualizada exitosamente")
                .httpStatus(ResponseType.fromCode(ResponseTypeEnum.ACTUALIZADO.getCode()).getHttpStatus())
                .data(response)
                .build();
        } catch (Exception e) {
            return createErrorResponse("Error al actualizar entidad: " + e.getMessage());
        }
    }

    /**
     * Elimina una entidad por su ID.
     */
    @Override
    public PlantillaResponse<RES> delete(I id) {
        try {
            if (!repository.existsByIdSafe(id)) {
                return createNotFoundResponse("Entidad no encontrada con ID: " + id);
            }
            
            repository.deleteByIdSafe(id);
            
            return PlantillaResponse.<RES>builder()
                .rta(true)
                .message("Entidad eliminada exitosamente")
                .httpStatus(ResponseType.fromCode(ResponseTypeEnum.DELETE.getCode()).getHttpStatus())
                .build();
        } catch (Exception e) {
            return createErrorResponse("Error al eliminar entidad: " + e.getMessage());
        }
    }

    /**
     * Crea una respuesta de error.
     */
    protected PlantillaResponse<RES> createErrorResponse(String message) {
        return PlantillaResponse.<RES>builder()
            .rta(false)
            .message(message)
            .httpStatus(ResponseType.fromCode(ResponseTypeEnum.FALLO.getCode()).getHttpStatus())
            .build();
    }

    /**
     * Crea una respuesta de entidad no encontrada.
     */
    protected PlantillaResponse<RES> createNotFoundResponse(String message) {
        return PlantillaResponse.<RES>builder()
            .rta(false)
            .message(message)
            .httpStatus(ResponseType.fromCode(ResponseTypeEnum.NOT_FOUND.getCode()).getHttpStatus())
            .build();
    }

    /**
     * Crea una respuesta de bad request.
     */
    protected PlantillaResponse<RES> createBadRequestResponse(String message) {
        return PlantillaResponse.<RES>builder()
            .rta(false)
            .message(message)
            .httpStatus(ResponseType.fromCode(ResponseTypeEnum.BAD_REQUEST.getCode()).getHttpStatus())
            .build();
    }

    // Métodos abstractos que deben ser implementados por las clases hijas
    protected abstract RES mapToResponse(E entity);
    protected abstract List<RES> mapToResponseList(List<E> entities);
    protected abstract E mapToEntity(RQ request);
    protected abstract I getIdFromRequest(RQ request);
}
