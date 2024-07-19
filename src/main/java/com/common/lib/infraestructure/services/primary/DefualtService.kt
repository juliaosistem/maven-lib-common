package com.common.lib.infraestructure.services.primary

import com.common.lib.api.dtos.request.AuditRequest
import com.common.lib.api.response.PlantillaResponse
import org.springframework.stereotype.Component

@Component
class DefualtService<RQ,RES,E,I>(
) {

    fun  add(entidad: RQ, id: I?,audit: AuditRequest?): PlantillaResponse<RES>? {
        return null
    }

    fun  all(id: I?, idBussines: Long?, audit: AuditRequest): PlantillaResponse<RES>? {
        return null
    }

}