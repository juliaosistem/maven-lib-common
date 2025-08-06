package com.common.lib.infraestructure.adapters.primary

import com.common.lib.utils.PlantillaResponse
import com.common.lib.infraestructure.services.primary.CrudPrimaryService
import com.common.lib.infraestructure.services.secundary.CrudSecundaryService
import org.springframework.stereotype.Service

@Service
open class DefaultImpl<RES, RQ, E, I> : CrudPrimaryService<RES, RQ, E, I> {

    // Implementación por defecto de CrudSecundaryService
    private val crudSecondaryService = object : CrudSecundaryService<RES, RQ, E, I> {
        override fun all(): PlantillaResponse<RES> = PlantillaResponse()
        override fun byIdBusiness(idBusiness: Long): PlantillaResponse<RES> = PlantillaResponse()
        override fun byId(id: I): PlantillaResponse<RES> = PlantillaResponse()
        override fun add(e: RQ): PlantillaResponse<RES> = PlantillaResponse()
        override fun update(e: RQ): PlantillaResponse<RES> = PlantillaResponse()
        override fun delete(e: I): PlantillaResponse<RES> = PlantillaResponse()
    }

    override fun all(): PlantillaResponse<RES> {
        return crudSecondaryService.all()
    }

    override fun byId(id: I): PlantillaResponse<RES> {
        return crudSecondaryService.byId(id)
    }

    override fun add(e: RQ): PlantillaResponse<RES> {
        return crudSecondaryService.add(e)
    }

    override fun delete(id: I): PlantillaResponse<RES> {
        return crudSecondaryService.delete(id)
    }

    override fun update(e: RQ): PlantillaResponse<RES> {
        return crudSecondaryService.update(e)
    }

    override fun byIdBusiness(idBusiness: Long): PlantillaResponse<RES> {
        return crudSecondaryService.byIdBusiness(idBusiness)
    }

    override fun all(id: I?, idBusiness: Long?): PlantillaResponse<RES> {
        return when {
            id != null -> crudSecondaryService.byId(id)
            idBusiness != null -> crudSecondaryService.byIdBusiness(idBusiness)
            else -> crudSecondaryService.all()
        }
    }
}