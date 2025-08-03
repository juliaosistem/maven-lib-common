package com.common.lib.infraestructure.adapters.primary

import com.common.lib.api.response.AuditResponse
import com.common.lib.api.response.PlantillaResponse
import com.common.lib.infraestructure.adapters.secundary.AuditAdapter


import org.springframework.stereotype.Service
import java.util.*

/**
 *  Class servicio encargada de realizar acciones crud para auditoria.
 * Requiere de  E   que es la entidad que responden los metodos y la R que es la request de los metodos y todos los metodos .
 *
 * @author  Daniel Juliao
 * @param <E>   Class entidad
 * @param <R>  Class request
 * @param <I>  tipo de dato del id de la entidad
 * @property PlantillaResponse  objeto de respuesta estándar requiere el parametro E
 * @return PlantillaResponse<E>
 * @version 1
 */

@Service
class AuditImpl(
    private  val auditService: AuditAdapter,
)  {

    fun delete(id: UUID): PlantillaResponse<AuditResponse> {
        val res:PlantillaResponse<AuditResponse> = auditService.byId(id)
        return if (res.rta) auditService.delete(id)
        else res
    }

//   fun update(e: AuditRequest): PlantillaResponse<AuditResponse> {
//        val res = auditService.byId(e.id)
//        return if (res.isRta) auditService.update(e)
//                 else res
//    }


    /**
     * Metodo encargado de consultar las auditorias se puede mandar parametros o no pues no son requeridos pero si agrega filtros
     * @author  Daniel Juliao
     * @param id  id de auditoria  sirve para filtrar pero no es requerido
     * @param idBusiness  id de negocio  sirve para filtrar  no es requerido
     * @property PlantillaResponse  objeto de respuesta estándar requiere el parametro E
     * @return PlantillaResponse<E>
     * @version 1
     */

    fun all(id: UUID?, idBusiness: Long?): PlantillaResponse<AuditResponse> {
        if (id != null ) return auditService.byId(id)
        return if (idBusiness != null) auditService.byIdBussines(idBusiness)
        else auditService.all()
    }
}