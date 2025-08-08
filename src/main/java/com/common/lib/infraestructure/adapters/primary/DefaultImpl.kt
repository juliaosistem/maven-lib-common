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
        override fun byId(id: I): PlantillaResponse<RES> = PlantillaResponse()
        override fun add(request: RQ): PlantillaResponse<RES> = PlantillaResponse()
        override fun update(request: RQ): PlantillaResponse<RES> = PlantillaResponse()
        override fun delete(id: I): PlantillaResponse<RES> = PlantillaResponse()
    }

    override fun all(): PlantillaResponse<RES> {
        return crudSecondaryService.all()
    }

    override fun byId(id: I): PlantillaResponse<RES> {
        return crudSecondaryService.byId(id)
    }

    override fun add(request: RQ): PlantillaResponse<RES> {
        return crudSecondaryService.add(request)
    }

    override fun delete(id: I): PlantillaResponse<RES> {
        return crudSecondaryService.delete(id)
    }

    override fun update(request: RQ): PlantillaResponse<RES> {
        return crudSecondaryService.update(request)
    }

}