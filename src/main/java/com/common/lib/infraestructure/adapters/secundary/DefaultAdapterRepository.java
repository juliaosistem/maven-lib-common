package com.common.lib.infraestructure.adapters.secundary;

import com.common.lib.api.mappers.GenericMapper;
import com.common.lib.api.mappers.MapperConRequest;
import com.common.lib.infraestructure.entitis.BaseEntities;
import com.common.lib.infraestructure.repository.DefaultRepository;
import com.common.lib.infraestructure.services.secundary.CrudSecundaryService;
import com.common.lib.utils.GenericSpecificationBuilder;
import com.common.lib.utils.PlantillaResponse;
import com.common.lib.utils.UserResponses;
import com.common.lib.utils.enums.ResponseType;
import com.common.lib.utils.errors.AbtractError;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

public class DefaultAdapterRepository<RES, RQ, E> implements CrudSecundaryService<RES, RQ> {

    protected final GenericMapper<RES, RQ, E> mapper;
    protected final AbtractError abstractError;
    protected final UserResponses<RES> userResponses;
    protected final Class<RES> resClass;
    protected final Class<E> entityClass;
    protected final DefaultRepository<E, Object> defaultRepository;
    protected final MapperConRequest<E, RES, RQ> mapperConRequest;
    protected final TransactionTemplate readTransactionTemplate;

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
        this(mapper, abstractError, userResponses, resClass, entityClass, defaultRepository, null, null);
    }

    public DefaultAdapterRepository(GenericMapper<RES, RQ, E> mapper,
                          AbtractError abstractError,
                          UserResponses<RES> userResponses,
                          Class<RES> resClass,
                          Class<E> entityClass,
                          DefaultRepository<E, Object> defaultRepository,
                          PlatformTransactionManager transactionManager) {
        this(mapper, abstractError, userResponses, resClass, entityClass, defaultRepository, null, transactionManager);
    }

    public DefaultAdapterRepository(GenericMapper<RES, RQ, E> mapper,
                          AbtractError abstractError,
                          UserResponses<RES> userResponses,
                          Class<RES> resClass,
                          Class<E> entityClass,
                          DefaultRepository<E, Object> defaultRepository,
                          MapperConRequest<E, RES, RQ> mapperConRequest,
                          PlatformTransactionManager transactionManager) {
        this.mapper = mapper;
        this.abstractError = abstractError;
        this.userResponses = userResponses;
        this.resClass = resClass;
        this.entityClass = entityClass;
        this.defaultRepository = defaultRepository;
        this.mapperConRequest = mapperConRequest;
        this.readTransactionTemplate = transactionManager == null ? null : new TransactionTemplate(transactionManager);
        if (this.readTransactionTemplate != null) {
            this.readTransactionTemplate.setReadOnly(true);
        }
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
        this(mapper, abstractError, userResponses, resClass, entityClass, defaultRepository, mapperConRequest, null);
    }


    public PlantillaResponse<RES> all() {
        return all(Collections.emptyMap());
    }

    @Override
    public PlantillaResponse<RES> all(Map<String, String> filters) {
        return executeRead(() -> readAll(filters));
    }

    private PlantillaResponse<RES> readAll(Map<String, String> filters) {
        long startTime = System.currentTimeMillis();
        this.abstractError.logInfo("DefaultAdapter.all() filters=" + filters);
        try {
            Specification<E> filterSpec = GenericSpecificationBuilder.fromFilters(filters);
            Pageable pageable = buildPageable(filters);
            Page<E> page = defaultRepository.findAll(filterSpec, pageable);
            List<E> content = page.getContent();
            List<RES> list = (mapperConRequest != null)
            ? mapperConRequest.mapListToRes(content)
            : mapper.mapListToRes(content, resClass);
            if (list == null || list.isEmpty()) {
                abstractError.logInfo("DefaultAdapter.all(): NOT_FOUND");
                return userResponses.buildResponse(ResponseType.NO_ENCONTRADO.getCode(), null);
            }
            traceQueryResult("DefaultAdapter.all()", list, filters, null);
            return userResponses.buildResponse(ResponseType.GET.getCode(), null, list);
        } catch (Exception e) {
            abstractError.logError(e);
            return userResponses.buildResponse(ResponseType.FALLO.getCode(), null);
        }
        finally {
            this.abstractError.logExecutionTime("DefaultAdapter.all()", startTime);
        }
    }

    @Override
    public PlantillaResponse<RES> byId(Object id) {
        return executeRead(() -> readById(id));
    }

    private PlantillaResponse<RES> readById(Object id) {
        long startTime = System.currentTimeMillis();
        this.abstractError.logInfo("DefaultAdapter.byId() id =" + id);
        try {
            Object searchId = id;
            if (id instanceof String && isValidUUID((String) id)) {
                 searchId = java.util.UUID.fromString((String) id);
            }

            Optional<E> opt = defaultRepository.findByIdSafe(searchId);
            if (opt.isPresent()) {
                this.abstractError.traceResponse("DefaultAdapter.byId()= " ,opt.get());
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
            this.abstractError.logExecutionTime("DefaultAdapter.byId()", startTime);
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
            this.abstractError.logExecutionTime("DefaultAdapter.add()", startTime);
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
            this.abstractError.logExecutionTime("DefaultAdapter.update()", startTime);
        }

    }

    @Override
    public PlantillaResponse<RES> delete(Object id) {
        long startTime = System.currentTimeMillis();
        this.abstractError.logInfo("DefaultAdapter.delete() id =" + id);
        try {
            Object deleteId = normalizeDeleteId(id);
            boolean existed = defaultRepository.deleteByIdSafe(deleteId);
            return existed
                ? userResponses.buildResponse(ResponseType.DELETED.getCode(), null)
                : userResponses.buildResponse(ResponseType.NO_ENCONTRADO.getCode(), null);
        } catch (IllegalArgumentException e) {
            abstractError.logInfo("DefaultAdapter.delete() - ID invalido o incompatible: " + e.getMessage());
            return userResponses.buildResponse(ResponseType.BAD_REQUEST.getCode(), null);
        } catch (Exception e) {
            if (e.getCause() instanceof IllegalArgumentException || (e.getMessage() != null && e.getMessage().contains("incompatible type"))) {
                abstractError.logInfo("DefaultAdapter.delete() - ID invalido o incompatible (wrapped): " + e.getMessage());
                return userResponses.buildResponse(ResponseType.BAD_REQUEST.getCode(), null);
            }
            abstractError.logError(e);
            return userResponses.buildResponse(ResponseType.FALLO.getCode(), null);
        }
        finally {
            this.abstractError.logExecutionTime("DefaultAdapter.delete()", startTime);
        }
    }

    private Object normalizeDeleteId(Object id) {
        if (!(id instanceof String rawId)) {
            return id;
        }
        String trimmedId = rawId.trim();
        if (!isValidUUID(trimmedId)) {
            return id;
        }
        return UUID.fromString(trimmedId);
    }

    public PlantillaResponse<RES> byIdBusiness(Integer idBusiness) {
        return byIdBusiness(idBusiness, Collections.emptyMap());
    }

    @Override
    public PlantillaResponse<RES> byIdBusiness(Integer idBusiness, Map<String, String> filters) {
        return executeRead(() -> readByIdBusiness(idBusiness, filters));
    }

    private PlantillaResponse<RES> readByIdBusiness(Integer idBusiness, Map<String, String> filters) {
        long startTime = System.currentTimeMillis();
        this.abstractError.logInfo("DefaultAdapter.byIdBusiness() idBusiness =" + idBusiness + ", filters=" + filters);
        try {
            Specification<E> tenantSpec = (root, query, cb) -> cb.equal(root.get("idBusiness"), idBusiness);
            Specification<E> filterSpec = GenericSpecificationBuilder.fromFilters(filters);
            Pageable pageable = buildPageable(filters);
            List<E> list = defaultRepository.findAll(tenantSpec.and(filterSpec), pageable).getContent();
        List<RES> mapped = (mapperConRequest != null)
            ? mapperConRequest.mapListToRes(list)
            : mapper.mapListToRes(list, resClass);
            if (mapped == null || mapped.isEmpty()) {
                return userResponses.buildResponse(ResponseType.NO_ENCONTRADO.getCode(), null);
            }
            traceQueryResult("DefaultAdapter.byIdBusiness()", mapped, filters, idBusiness);
            return userResponses.buildResponse(ResponseType.GET.getCode(), null, mapped);
        } catch (Exception e) {
            abstractError.logError(e);
            return userResponses.buildResponse(ResponseType.FALLO.getCode(), null);
        }
        finally {
            this.abstractError.logExecutionTime("DefaultAdapter.byIdBusiness()", startTime);
        }
    }

    private PlantillaResponse<RES> executeRead(Supplier<PlantillaResponse<RES>> action) {
        if (readTransactionTemplate == null) {
            return action.get();
        }
        return readTransactionTemplate.execute(status -> action.get());
    }

    private Pageable buildPageable(Map<String, String> filters) {
        int page = parsePositiveInt(filters, "page", 0);
        int size = parsePositiveInt(filters, "size", 100);
        Sort sort = buildSort(filters == null ? null : filters.get("sort"));
        return PageRequest.of(page, size, sort);
    }

    private int parsePositiveInt(Map<String, String> filters, String key, int defaultValue) {
        if (filters == null || !filters.containsKey(key)) {
            return defaultValue;
        }
        try {
            int value = Integer.parseInt(filters.get(key));
            return value >= 0 ? value : defaultValue;
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    private Sort buildSort(String sortRaw) {
        if (sortRaw == null || sortRaw.isBlank()) {
            return Sort.unsorted();
        }
        String[] parts = sortRaw.split(",");
        if (parts.length == 0 || parts[0].isBlank()) {
            return Sort.unsorted();
        }
        Sort.Direction direction = parts.length > 1 && "desc".equalsIgnoreCase(parts[1].trim())
                ? Sort.Direction.DESC : Sort.Direction.ASC;
        return Sort.by(direction, parts[0].trim());
    }

    /**
     * Registra metadatos y datos de salida para auditoría de consultas CRUD.
     *
     * @param context nombre del contexto de consulta.
     * @param list datos resultantes de la consulta.
     * @param filters filtros aplicados en la búsqueda.
     * @param idBusiness identificador del tenant cuando aplica.
     */
    private void traceQueryResult(String context, List<RES> list, Map<String, String> filters, Integer idBusiness) {
        Map<String, Object> meta = new HashMap<>();
        meta.put("idBusiness", idBusiness);
        meta.put("filters", filters == null ? Collections.emptyMap() : filters);
        meta.put("total", list.size());
        abstractError.traceResponse(context + ".meta", meta);
        abstractError.traceResponse(context + ".dataList", list);
    }
}

