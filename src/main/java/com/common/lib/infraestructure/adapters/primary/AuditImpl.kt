package com.common.lib.infraestructure.adapters.primary

import com.common.lib.api.dtos.request.AuditRequest
import com.common.lib.api.response.AuditResponse
import com.common.lib.utils.PlantillaResponse
import com.common.lib.infraestructure.adapters.secundary.AuditAdapter
import com.common.lib.infraestructure.services.primary.CrudPrimaryService
import org.springframework.stereotype.Service
import java.util.*

/**
 * Class servicio encargada de realizar acciones crud para auditoria.
 * Requiere de E que es la entidad que responden los metodos y la R que es la request de los metodos y todos los metodos .
 *
 * @author Daniel Juliao
 * @param <E> Class entidad
 * @param <R> Class request
 * @param <I> tipo de dato del id de la entidad
 * @property PlantillaResponse objeto de respuesta estándar requiere el parametro E
 * @return PlantillaResponse<E>
 * @version 2
 */
@Service
class AuditImpl(
    private val auditService: AuditAdapter,
) : CrudPrimaryService<AuditResponse, AuditRequest, com.common.lib.infraestructure.entitis.Audit, UUID> {

    override fun all(): PlantillaResponse<AuditResponse> {
        return auditService.all()
    }

    override fun byId(id: UUID): PlantillaResponse<AuditResponse> {
        return auditService.byId(id)
    }

    override fun add(request: AuditRequest): PlantillaResponse<AuditResponse> {
        return auditService.add(request)
    }

    override fun delete(id: UUID): PlantillaResponse<AuditResponse> {
        val res: PlantillaResponse<AuditResponse> = auditService.byId(id)
        return if (res.rta) auditService.delete(id)
        else res
    }

    override fun update(request: AuditRequest): PlantillaResponse<AuditResponse> {
        return auditService.update(request)
    }


}