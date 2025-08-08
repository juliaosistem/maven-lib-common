package com.common.lib.infraestructure.adapters.secundary

import com.common.lib.api.mappers.GenericMapper
import com.common.lib.utils.PlantillaResponse
import com.common.lib.infraestructure.repository.DefaultRepository
import com.common.lib.infraestructure.services.secundary.CrudSecundaryService
import com.common.lib.utils.TimeUtils
import com.common.lib.utils.UserResponses
import com.common.lib.utils.enums.ResponseType
import com.common.lib.utils.errors.AbtractError
import org.springframework.stereotype.Service
import org.springframework.data.domain.PageRequest

/**
 * Implementación abstracta de un adaptador para operaciones CRUD en el repositorio.
 *
 * Esta clase proporciona una implementación genérica de los métodos CRUD (crear, leer, actualizar y eliminar)
 * para interactuar con repositorios en una arquitectura basada en TypeORM y Spring Data.
 * Sigue los principios SOLID y arquitectura hexagonal.
 *
 * @param RES El tipo de la respuesta esperada de la operación.
 * @param RQ El tipo de los datos enviados en la solicitud para operaciones de creación o actualización.
 * @param E El tipo de la entidad que será manejada por el repositorio.
 * @param I El tipo del identificador único de la entidad.
 */
@Service
abstract class DefaultAdapter<RES, RQ, E : Any, I>(
    private val mapper: GenericMapper<RES, RQ, E>,
    private val abstractError: AbtractError,
    private val userResponses: UserResponses<RES>,
    private val resClass: Class<RES>,
    private val entityClass: Class<E>,
    private val defaultRepository: DefaultRepository<E, I>
) : CrudSecundaryService<RES, RQ, E, I> {

    /**
     * Recupera todos los registros disponibles en el repositorio.
     *
     * @return Respuesta con una lista de elementos de tipo `RES` (respuesta).
     */
    override fun all(): PlantillaResponse<RES> {
        return TimeUtils.measureExecutionTime {
            try {
                val response = mapper.mapListToRes(defaultRepository.findAllWithPagination().content.toList(), resClass)
                validareResponse(response)
            } catch (e: Exception) {
                abstractError.logError(e)
                userResponses.buildResponse(ResponseType.FALLO.code, null)
            }
        }
    }

    private fun validareResponse(response : List<RES>) :PlantillaResponse<RES> {
        return if (response.isEmpty()) {
            abstractError.logInfo("res.all() :  ${ResponseType.GET.message} ")
            userResponses.buildResponse(ResponseType.NO_ENCONTRADO.code, response.firstOrNull())
        } else {
            abstractError.logInfo("DefaultAdapter.all() :  ${ResponseType.GET.message} - ${response[0]}")
            userResponses.buildResponse(ResponseType.GET.code, response.firstOrNull(), response)
        }
    }

    /**
     * Recupera un registro específico por su identificador único.
     *
     * @param id El identificador único del recurso.
     * @return Respuesta con el recurso encontrado o un mensaje de error si no se encuentra.
     */
    override fun byId(id: I): PlantillaResponse<RES> {
        return TimeUtils.measureExecutionTime {
            try {
                val response = defaultRepository.findByIdSafe(id!!)
                if (response.isPresent) {
                    abstractError.logInfo("DefaultAdapter.byId() :  ${ResponseType.GET.message}")
                    userResponses.buildResponse(ResponseType.GET.code, mapper.mapToRes(response.get(), resClass))
                } else {
                    abstractError.logInfo("DefaultAdapter.byId() :  ${ResponseType.NO_ENCONTRADO.message}")
                    userResponses.buildResponse(ResponseType.NO_ENCONTRADO.code, null)
                }
            } catch (e: Exception) {
                abstractError.logError(e)
                userResponses.buildResponse(ResponseType.FALLO.code, null)
            }
        }
    }

    /**
     * Elimina un registro por su identificador único.
     *
     * @param id El identificador único del recurso a eliminar.
     * @return Respuesta indicando el resultado de la operación.
     */
    override fun delete(id: I): PlantillaResponse<RES> {
        return TimeUtils.measureExecutionTime {
            try {
                defaultRepository.deleteByIdSafe(id!!)
                userResponses.buildResponse(ResponseType.DELETED.code, null)
            } catch (e: Exception) {
                abstractError.logError(e)
                userResponses.buildResponse(ResponseType.FALLO.code, null)
            }
        }
    }

    /**
     * Agrega un nuevo registro al repositorio.
     *
     * @param request Los datos del recurso a crear.
     * @return Respuesta indicando el resultado de la operación.
     */
    override fun add(request: RQ): PlantillaResponse<RES> {
        return TimeUtils.measureExecutionTime {
            try {
                val response = mapper.mapToRes(defaultRepository.save(mapper.mapToEntity(request, entityClass)), resClass)
                abstractError.logInfo("DefaultAdapter.add() :  ${ResponseType.CREATED.message}")
                userResponses.buildResponse(ResponseType.CREATED.code, response)
            } catch (e: Exception) {
                abstractError.logError(e)
                userResponses.buildResponse(ResponseType.FALLO.code, null)
            }
        }
    }

    /**
     * Actualiza un registro existente en el repositorio.
     *
     * @param request Los datos del recurso a actualizar.
     * @return Respuesta indicando el resultado de la operación.
     */
    override fun update(request: RQ): PlantillaResponse<RES> {
        return TimeUtils.measureExecutionTime {
            try {
                val response = mapper.mapToRes(defaultRepository.save(mapper.mapToEntity(request, entityClass)), resClass)
                abstractError.logInfo("DefaultAdapter.update() :  ${ResponseType.UPDATED.message}")
                userResponses.buildResponse(ResponseType.UPDATED.code, response)
            } catch (e: Exception) {
                abstractError.logError(e)
                userResponses.buildResponse(ResponseType.FALLO.code, null)
            }
        }
    }
}
