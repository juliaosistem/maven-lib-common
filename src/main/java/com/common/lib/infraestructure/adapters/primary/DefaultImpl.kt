package com.common.lib.infraestructure.adapters.primary

import com.common.lib.api.response.PlantillaResponse
import com.common.lib.infraestructure.services.primary.CrudPrimaryService
import com.common.lib.infraestructure.services.secundary.CrudSecundaryService
import org.springframework.stereotype.Service

@Service
open class DefaultImpl<RES, RQ, E,I>(
      private  val crudSecondaryService : CrudSecundaryService<RES, RQ, E,I>
): CrudPrimaryService<RES, RQ, E,I> {

    override fun all(id: I?, idBusiness: Long?): PlantillaResponse<RES> {
        if (id != null ) return crudSecondaryService.byId(id)
        return if (idBusiness != null) crudSecondaryService.byIdBussines(idBusiness)
        else crudSecondaryService.all()
    }

    override fun delete(id:I): PlantillaResponse<RES> {
        val res:PlantillaResponse<RES> = crudSecondaryService.byId(id)
        return if (res.isRta) crudSecondaryService.delete(id)
        else res
    }

    override fun add(e: RQ , id: I): PlantillaResponse<RES> {
        val  res = crudSecondaryService.byId(id)
        return if (res.isRta) res
        else {
            crudSecondaryService.add(e)
        }
    }

    override fun update(e: RQ): PlantillaResponse<RES> {
        return crudSecondaryService.update(e)
    }
}