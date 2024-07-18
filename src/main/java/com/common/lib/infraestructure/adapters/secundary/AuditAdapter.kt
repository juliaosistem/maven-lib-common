package com.common.lib.infraestructure.adapters.secundary

import com.common.lib.api.dtos.request.AuditRequest
import com.common.lib.api.mappers.AuditMapper
import com.common.lib.api.response.AuditResponse
import com.common.lib.api.response.PlantillaResponse
import com.common.lib.infraestructure.repository.AuditRepository
import com.common.lib.infraestructure.services.secundary.CrudSecundaryService
import com.common.lib.utils.UserResponses
import com.common.lib.utils.enums.ResponseType
import com.common.lib.utils.errors.AbtractError
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuditAdapter (
    private val auditRepository: AuditRepository,
    private val abtractError: AbtractError,
    private val userResponses: UserResponses<AuditResponse>,
    private val mapper: AuditMapper
) {

    fun all(): PlantillaResponse<AuditResponse> {
      return  try {
            val response = mapper.mapList(auditRepository.findAll())
          if (response.isEmpty()){
              abtractError.logInfo("AuditAdapter.all() :  ${ResponseType.GET.message} - de auditoria")
              userResponses.buildResponse(ResponseType.NO_ENCONTRADO.code,AuditResponse())
          }else {
              abtractError.logInfo("AuditAdapter.all() :  ${ResponseType.GET.message} - de auditoria")
              userResponses.buildResponse(ResponseType.GET.code, response.first(), response)
          }

        } catch (e: Exception) {
            abtractError.logError(e)
            userResponses.buildResponse(ResponseType.FALLO.code, AuditResponse())
        }
    }

   fun byId(id: UUID): PlantillaResponse<AuditResponse> {
        return try {
            val response = auditRepository.findById(id)
            if (response.isPresent) {
                abtractError.logInfo("AuditAdapter.byId() :  ${ResponseType.GET.message} - de auditoria")
                userResponses.buildResponse(ResponseType.GET.code, mapper.map(response.get()))
            } else {
                abtractError.logInfo("AuditAdapter.byId() :  ${ResponseType.NO_ENCONTRADO.message} - de auditoria")
                userResponses.buildResponse(ResponseType.NO_ENCONTRADO.code, AuditResponse())
            }
        } catch (e: Exception) {
            abtractError.logError(e)
            userResponses.buildResponse(ResponseType.FALLO.code, AuditResponse())
        }
    }

    fun delete(id: UUID ): PlantillaResponse<AuditResponse> {
      return  try {
               auditRepository.deleteById(id)
               userResponses.buildResponse(ResponseType.DELETED.code,AuditResponse.builder().id(id).build())
         } catch (e: Exception) {
            abtractError.logError(e)
             userResponses.buildResponse(ResponseType.FALLO.code, AuditResponse())
        }
    }

   fun byIdBussines(idBusiness: Long): PlantillaResponse<AuditResponse> {
        return try {
            val response = auditRepository.findByIdBussines(idBusiness)
            if (response.isNotEmpty()) {
                val res = mapper.mapList(response)
                abtractError.logInfo("AuditAdapter.byIdBussines() :  ${ResponseType.GET.message} - de auditoria")
                userResponses.buildResponse(ResponseType.GET.code,res.first(), res)
            } else {
                abtractError.logInfo("AuditAdapter.byIdBussines() :  ${ResponseType.NO_ENCONTRADO.message} - de auditoria")
                userResponses.buildResponse(ResponseType.NO_ENCONTRADO.code, AuditResponse())
            }
        } catch (e: Exception) {
            abtractError.logError(e)
            userResponses.buildResponse(ResponseType.FALLO.code, AuditResponse())
        }
    }

    fun add(e: AuditRequest?): PlantillaResponse<AuditResponse> {
       return  try {
             val  response = mapper.map(auditRepository.save(mapper.map(e)))
            abtractError.logInfo("AuditAdapter.add() :  La auditoria fue  ${ResponseType.CREATED.message}")
             userResponses.buildResponse(ResponseType.CREATED.code,  response)
        } catch (e: Exception) {
            abtractError.logError(e)
            userResponses.buildResponse(ResponseType.FALLO.code, AuditResponse())
        }
    }


}





