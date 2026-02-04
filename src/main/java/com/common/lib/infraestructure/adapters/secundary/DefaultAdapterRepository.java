package com.common.lib.infraestructure.adapters.secundary;

import com.common.lib.api.mappers.GenericMapper;
import com.common.lib.api.mappers.MapperConRequest;
import com.common.lib.infraestructure.entitis.BaseEntities;
import com.common.lib.infraestructure.repository.DefaultRepository;
import com.common.lib.infraestructure.services.secundary.CrudSecundaryService;
import com.common.lib.utils.PlantillaResponse;
import com.common.lib.utils.UserResponses;
import com.common.lib.utils.enums.ResponseType;
import com.common.lib.utils.errors.AbtractError;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public class DefaultAdapterRepository<RES, RQ, E> implements CrudSecundaryService<RES, RQ> {

    protected final GenericMapper<RES, RQ, E> mapper;
    protected final AbtractError abstractError;
    protected final UserResponses<RES> userResponses;
    protected final Class<RES> resClass;
    protected final Class<E> entityClass;
    protected final DefaultRepository<E, Object> defaultRepository;
    protected final MapperConRequest<E, RES, RQ> mapperConRequest;

    /** Constructor principal sin MapperConRequest especializado usa el mapper generico
     * @param mapper Mapper genérico
     * @param abstractError Manejo de errores
     * @param userResponses Respuestas de usuario
     * @param resClass Clase de respuesta
   * @param entityClass Clase de entidad
   * @param defaultRepository Repositorio por defecto
  */
    public DefaultAdapterRepository(GenericMapper<RES, RQ, E> mapper,
                          AbtractError abstractError,
                          UserResponses<RES> userResponses,
                          Class<RES> resClass,
                          Class<E> entityClass,
                          DefaultRepository<E, Object> defaultRepository) {
        this.mapper = mapper;
        this.abstractError = abstractError;
        this.userResponses = userResponses;
        this.resClass = resClass;
        this.entityClass = entityClass;
        this.defaultRepository = defaultRepository;
        this.mapperConRequest = null; // por defecto no hay mapper especializado
    }


    /**
     * @param mapperConRequest Mapper especializado para transformar entre entidad y request/response
     * @param abstractError Manejo de errores
     * @param userResponses Respuestas de usuario
     * @param resClass Clase de respuesta
     * @param entityClass Clase de entidad
     * @param defaultRepository Repositorio por defecto
     * Constructor alterno que permite inyectar un MapperConRequest especializado
     */
    public DefaultAdapterRepository(GenericMapper<RES, RQ, E> mapper,
                          AbtractError abstractError,
                          UserResponses<RES> userResponses,
                          Class<RES> resClass,
                          Class<E> entityClass,
                          DefaultRepository<E, Object> defaultRepository,
                          MapperConRequest<E, RES, RQ> mapperConRequest) {
        this.mapper = mapper;
        this.abstractError = abstractError;
        this.userResponses = userResponses;
        this.resClass = resClass;
        this.entityClass = entityClass;
        this.defaultRepository = defaultRepository;
        this.mapperConRequest = mapperConRequest;
    }


    @Override
    public PlantillaResponse<RES> all() {
        long startTime = System.currentTimeMillis();
        this.abstractError.logInfo("DefaultAdapter.all()");
        try {
            List<E> content = defaultRepository.findAllWithPagination().getContent();
            List<RES> list = (mapperConRequest != null)
            ? mapperConRequest.mapListToRes(content)
            : mapper.mapListToRes(content, resClass);
            if (list == null || list.isEmpty()) {
                abstractError.logInfo("DefaultAdapter.all(): NOT_FOUND");
                return userResponses.buildResponse(ResponseType.NO_ENCONTRADO.getCode(), null);
            }
            abstractError.logInfo("DefaultAdapter.all(): GET  list = " + list);
            return userResponses.buildResponse(ResponseType.GET.getCode(), null, list);
        } catch (Exception e) {
            abstractError.logError(e);
            return userResponses.buildResponse(ResponseType.FALLO.getCode(), null);
        }
        finally {
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            this.abstractError.logInfo("DefaultAdapter.all() - Tiempo de ejecución: " + duration + " ms");
        }
    }

    @Override
    public PlantillaResponse<RES> byId(Object id) {
        long startTime = System.currentTimeMillis();
        this.abstractError.logInfo("DefaultAdapter.byId() id =" + id);
        try {
            Object searchId = id;
            if (id instanceof String && isValidUUID((String) id)) {
                 searchId = java.util.UUID.fromString((String) id);
            }

            Optional<E> opt = defaultRepository.findByIdSafe(searchId);
            if (opt.isPresent()) {
                RES res = (mapperConRequest != null)
                    ? mapperConRequest.mapToRes(opt.get())
                    : mapper.mapToRes(opt.get(), resClass);
                return userResponses.buildResponse(ResponseType.GET.getCode(), res);
            }
            return userResponses.buildResponse(ResponseType.NO_ENCONTRADO.getCode(), null);
        } catch (IllegalArgumentException e) {
            abstractError.logInfo("DefaultAdapter.byId() - ID invalido o incompatible: " + e.getMessage());
            return userResponses.buildResponse(ResponseType.BAD_REQUEST.getCode(), null);
        } catch (Exception e) {
            if (e.getCause() instanceof IllegalArgumentException || (e.getMessage() != null && e.getMessage().contains("Supplied id had wrong type"))) {
                abstractError.logInfo("DefaultAdapter.byId() - ID invalido o incompatible (wrapped): " + e.getMessage());
                return userResponses.buildResponse(ResponseType.BAD_REQUEST.getCode(), null);
            }
            abstractError.logError(e);
            return userResponses.buildResponse(ResponseType.FALLO.getCode(), null);
        }
        finally {
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            this.abstractError.logInfo("DefaultAdapter.byId() - Tiempo de ejecución: " + duration + " ms");
        }
    }

    private boolean isValidUUID(String str) {
        try {
            java.util.UUID.fromString(str);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * @param request Request a transformar a entidad
     * @param idBussines id de bussines a asignar a la entidad

     * Se encarga de transformar el request a entidad ,asignarle el id de bussines si es necesario , guardarlo en base de datos
     *  condiciones verifica si viene un mapper en el  constructor si llega lo usa para transformar el request a entidad si no usa el defualt
     */

    @Override
    public PlantillaResponse<RES> add(RQ request ,Integer idBussines) {
        long startTime = System.currentTimeMillis();
 this.abstractError.logInfo("DefaultAdapter.add() request =" + request);
        try {
        E entity = (mapperConRequest != null)
            ? mapperConRequest.deRequestAEntidad(request)
            : mapper.mapToEntity(request, entityClass);
           llenarEntidadConIdbussines(idBussines, entity);
            E saved = defaultRepository.save(entity);
        RES res = (mapperConRequest != null)
            ? mapperConRequest.mapToRes(saved)
            : mapper.mapToRes(saved, resClass);
            return userResponses.buildResponse(ResponseType.CREATED.getCode(), res);
        } catch (Exception e) {
            abstractError.logError(e);
            return userResponses.buildResponse(ResponseType.FALLO.getCode(), null);
        }
        finally {
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            this.abstractError.logInfo("DefaultAdapter.all() - Tiempo de ejecución: " + duration + " ms");
        }
    }

    /**
     * @param entity entidad a llenar
     * @param idBussines id de bussines a asignar a la entidad
     * le agrega el id de bussines a la entidad si es necesario
     */
    private void llenarEntidadConIdbussines(Integer idBussines, E entity){
        if (idBussines != null) {
            try {
                if (entity instanceof BaseEntities) {
                    ((BaseEntities) entity).setIdBusiness(idBussines);
                } else {
                    try {
                        var field = entity.getClass().getDeclaredField("idBusiness");
                        field.setAccessible(true);
                        field.set(entity, idBussines);
                    } catch (NoSuchFieldException | IllegalAccessException ex) {
                        abstractError.logInfo("No se estableció idBusiness via reflection: " + ex.getMessage());
                    }
                }
            } catch (Exception ex) {
                abstractError.logInfo("Error asignando idBusiness a la entidad: " + ex.getMessage());
            }
        }
    }

    @Override
    public PlantillaResponse<RES> update(RQ request) {
        long startTime = System.currentTimeMillis();
        this.abstractError.logInfo("DefaultAdapter.update() request =" + request);
        try {
        E entity = (mapperConRequest != null)
            ? mapperConRequest.deRequestAEntidad(request)
            : mapper.mapToEntity(request, entityClass);
            E saved = defaultRepository.save(entity);
        RES res = (mapperConRequest != null)
            ? mapperConRequest.mapToRes(saved)
            : mapper.mapToRes(saved, resClass);
            return userResponses.buildResponse(ResponseType.UPDATED.getCode(), res);
        } catch (Exception e) {
            abstractError.logError(e);
            return userResponses.buildResponse(ResponseType.FALLO.getCode(), null);
        }
        finally {
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            this.abstractError.logInfo("DefaultAdapter.all() - Tiempo de ejecución: " + duration + " ms");
        }

    }

    @Override
    public PlantillaResponse<RES> delete(Object id) {
        long startTime = System.currentTimeMillis();
        this.abstractError.logInfo("DefaultAdapter.delete() id =" + id);
        try {
            boolean existed = defaultRepository.deleteByIdSafe(id);
            return existed
                ? userResponses.buildResponse(ResponseType.DELETED.getCode(), null)
                : userResponses.buildResponse(ResponseType.NO_ENCONTRADO.getCode(), null);
        } catch (Exception e) {
            abstractError.logError(e);
            return userResponses.buildResponse(ResponseType.FALLO.getCode(), null);
        }
        finally {
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            this.abstractError.logInfo("DefaultAdapter.all() - Tiempo de ejecución: " + duration + " ms");
        }
    }

    @Override
    public PlantillaResponse<RES> byIdBusiness(Integer idBusiness) {
        long startTime = System.currentTimeMillis();
        this.abstractError.logInfo("DefaultAdapter.byIdBusiness() idBusiness =" + idBusiness);
        try {
            Specification<E> spec = (root, query, cb) -> cb.equal(root.get("idBusiness"), idBusiness);
            List<E> list = defaultRepository.findAll(spec);
        List<RES> mapped = (mapperConRequest != null)
            ? mapperConRequest.mapListToRes(list)
            : mapper.mapListToRes(list, resClass);
            if (mapped == null || mapped.isEmpty()) {
                return userResponses.buildResponse(ResponseType.NO_ENCONTRADO.getCode(), null);
            }
            return userResponses.buildResponse(ResponseType.GET.getCode(), null, mapped);
        } catch (Exception e) {
            abstractError.logError(e);
            return userResponses.buildResponse(ResponseType.FALLO.getCode(), null);
        }
        finally {
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            this.abstractError.logInfo("DefaultAdapter.all() - Tiempo de ejecución: " + duration + " ms");
        }
    }

}


