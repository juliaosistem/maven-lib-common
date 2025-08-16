package com.common.lib.infraestructure.adapters.secundary;

import com.common.lib.api.mappers.GenericMapper;
import com.common.lib.infraestructure.repository.DefaultRepository;
import com.common.lib.infraestructure.services.secundary.CrudSecundaryService;
import com.common.lib.utils.PlantillaResponse;
import com.common.lib.utils.ResponseTypeEnum;
import com.common.lib.utils.UserResponses;
import com.common.lib.utils.errors.AbtractError;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public class DefaultAdapter<RES, RQ, E, I> implements CrudSecundaryService<RES, RQ, E, I> {

    protected final GenericMapper<RES, RQ, E> mapper;
    protected final AbtractError abstractError;
    protected final UserResponses<RES> userResponses;
    protected final Class<RES> resClass;
    protected final Class<E> entityClass;
    protected final DefaultRepository<E, Object> defaultRepository;

    public DefaultAdapter(GenericMapper<RES, RQ, E> mapper,
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
    }

    @Override
    public PlantillaResponse<RES> all() {
        try {
            List<E> content = defaultRepository.findAllWithPagination().getContent();
            List<RES> list = mapper.mapListToRes(content, resClass);
            if (list == null || list.isEmpty()) {
                abstractError.logInfo("DefaultAdapter.all(): NOT_FOUND");
                return userResponses.buildResponse(ResponseTypeEnum.NOT_FOUND.getCode(), null);
            }
            abstractError.logInfo("DefaultAdapter.all(): GET");
            return userResponses.buildResponse(ResponseTypeEnum.GET.getCode(), null, list);
        } catch (Exception e) {
            abstractError.logError(e);
            return userResponses.buildResponse(ResponseTypeEnum.FALLO.getCode(), null);
        }
    }

    @Override
    public PlantillaResponse<RES> byId(I id) {
        try {

            Optional<E> opt = defaultRepository.findByIdSafe(id);
            if (opt.isPresent()) {
                RES res = mapper.mapToRes(opt.get(), resClass);
                return userResponses.buildResponse(ResponseTypeEnum.GET.getCode(), res);
            }
            return userResponses.buildResponse(ResponseTypeEnum.NOT_FOUND.getCode(), null);
        } catch (Exception e) {
            abstractError.logError(e);
            return userResponses.buildResponse(ResponseTypeEnum.FALLO.getCode(), null);
        }
    }

    @Override
    public PlantillaResponse<RES> add(RQ request) {
        try {
            E entity = mapper.mapToEntity(request, entityClass);
            E saved = defaultRepository.save(entity);
            RES res = mapper.mapToRes(saved, resClass);
            return userResponses.buildResponse(ResponseTypeEnum.CREADO.getCode(), res);
        } catch (Exception e) {
            abstractError.logError(e);
            return userResponses.buildResponse(ResponseTypeEnum.FALLO.getCode(), null);
        }
    }

    @Override
    public PlantillaResponse<RES> update(RQ request) {
        try {
            E entity = mapper.mapToEntity(request, entityClass);
            E saved = defaultRepository.save(entity);
            RES res = mapper.mapToRes(saved, resClass);
            return userResponses.buildResponse(ResponseTypeEnum.ACTUALIZADO.getCode(), res);
        } catch (Exception e) {
            abstractError.logError(e);
            return userResponses.buildResponse(ResponseTypeEnum.FALLO.getCode(), null);
        }
    }

    @Override
    public PlantillaResponse<RES> delete(I id) {
        try {
            boolean existed = defaultRepository.deleteByIdSafe(id);
            return existed
                ? userResponses.buildResponse(ResponseTypeEnum.DELETE.getCode(), null)
                : userResponses.buildResponse(ResponseTypeEnum.NOT_FOUND.getCode(), null);
        } catch (Exception e) {
            abstractError.logError(e);
            return userResponses.buildResponse(ResponseTypeEnum.FALLO.getCode(), null);
        }
    }

    @Override
    public PlantillaResponse<RES> byIdBusiness(Integer idBusiness) {
        try {
            Specification<E> spec = (root, query, cb) -> cb.equal(root.get("idBusiness"), idBusiness);
            List<E> list = defaultRepository.findAll(spec);
            List<RES> mapped = mapper.mapListToRes(list, resClass);
            if (mapped == null || mapped.isEmpty()) {
                return userResponses.buildResponse(ResponseTypeEnum.NOT_FOUND.getCode(), null);
            }
            return userResponses.buildResponse(ResponseTypeEnum.GET.getCode(), null, mapped);
        } catch (Exception e) {
            abstractError.logError(e);
            return userResponses.buildResponse(ResponseTypeEnum.FALLO.getCode(), null);
        }
    }

}


