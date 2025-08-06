package com.common.lib.infraestructure.services.primary

import com.common.lib.api.dtos.request.AuditRequest
import com.common.lib.utils.PlantillaResponse

interface DefaultService<RES, RQ, I> {
    fun all(): PlantillaResponse<RES>?
    fun byId(id: I): PlantillaResponse<RES>?
    fun add(e: RQ): PlantillaResponse<RES>?
    fun delete(id: I): PlantillaResponse<RES>?
    fun update(e: RQ): PlantillaResponse<RES>?
    fun byIdBusiness(idBusiness: Long): PlantillaResponse<RES>?
    fun all(id: I?, idBusiness: Long?, audit: AuditRequest): PlantillaResponse<RES>? {
        return null
    }
} 