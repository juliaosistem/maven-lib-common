package com.common.lib.infraestructure.services.primary

import com.common.lib.api.dtos.request.AuditRequest
import com.common.lib.api.response.PlantillaResponse
import com.common.lib.infraestructure.adapters.primary.DefaultImpl
import lombok.NoArgsConstructor
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Component
class DefualtService<RQ,RES,I>(
    private val defualImpl : DefaultImpl<RES ,RQ,I>
) {

    fun  add(entidad: RQ, id: I?,audit: AuditRequest?):PlantillaResponse<RES> {
         return defualImpl.add(entidad,id!!)
    }

    fun  all(id: I?, idBussines: Long, audit: AuditRequest):PlantillaResponse<RES>{
        return defualImpl.all(id!!,idBussines)
    }

}
