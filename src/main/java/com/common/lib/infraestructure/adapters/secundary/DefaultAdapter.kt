package com.common.lib.infraestructure.adapters.secundary

import com.common.lib.api.mappers.GenericMapper
import com.common.lib.api.response.PlantillaResponse
import com.common.lib.infraestructure.repository.DefaultRepository
import com.common.lib.infraestructure.services.secundary.CrudSecundaryService
import com.common.lib.utils.UserResponses
import com.common.lib.utils.enums.ResponseType
import com.common.lib.utils.errors.AbtractError
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Service
class DefaultAdapter<RES,RQ,E,I>(
    private val mapper: GenericMapper<RES,RQ,E>,
    private val abstractError: AbtractError,
    private val userResponses: UserResponses<RES>,
    private val resClass: RES,
    private val entityClass :E,
    private val defaultRepository: DefaultRepository<E,I>,

): CrudSecundaryService<RQ, RES, I> {

    override fun all(): PlantillaResponse<RES> {
        return  try {
            val response = mapper.mapListToRes(defaultRepository.findAll(),resClass)
            if (response.isEmpty()){
                abstractError.logInfo("res.all() :  ${ResponseType.GET.message} ")
                userResponses.buildResponse(ResponseType.NO_ENCONTRADO.code,response.first())
            }else {
                abstractError.logInfo("AuditAdapter.all() :  ${ResponseType.GET.message} - de auditoria")
                userResponses.buildResponse(ResponseType.GET.code, response.first(), response)
            }
        } catch (e: Exception) {
            abstractError.logError(e)
            userResponses.buildResponse(ResponseType.FALLO.code, null )
        }
    }

    override fun byId(id: I): PlantillaResponse<RES> {
        return try {
            val response = defaultRepository.findById(id!!)
            if (response.isPresent) {
                abstractError.logInfo("default.byId() :  ${ResponseType.GET.message}")
                userResponses.buildResponse(ResponseType.GET.code, mapper.mapToRes(response.get(),resClass))
            } else {
                abstractError.logInfo("default.byId() :  ${ResponseType.NO_ENCONTRADO.message}")
                userResponses.buildResponse(ResponseType.NO_ENCONTRADO.code, null)
            }
        } catch (e: Exception) {
            abstractError.logError(e)
            userResponses.buildResponse(ResponseType.FALLO.code, null)
        }
    }

    override fun delete(e: I): PlantillaResponse<RES> {
        return  try {
            defaultRepository.deleteById(e!!)
            userResponses.buildResponse(ResponseType.DELETED.code,null)
        } catch (e: Exception) {
            abstractError.logError(e)
            userResponses.buildResponse(ResponseType.FALLO.code, null)
        }
    }

    override fun add(e: RQ): PlantillaResponse<RES> {
        return  try {
            val  response = mapper.mapToRes(defaultRepository.save(mapper.mapToEntity(e,entityClass)),resClass)
            abstractError.logInfo("default.add() :   ${ResponseType.CREATED.message}")
            userResponses.buildResponse(ResponseType.CREATED.code,  response)
        } catch (e: Exception) {
            abstractError.logError(e)
            userResponses.buildResponse(ResponseType.FALLO.code, null)
        }
    }

}

