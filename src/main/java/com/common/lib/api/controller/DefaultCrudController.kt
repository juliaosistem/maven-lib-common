package com.common.lib.api.controller

import com.common.lib.api.dtos.request.AuditRequest
import com.common.lib.api.response.PlantillaResponse
import com.common.lib.infraestructure.services.primary.DefualtService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class DefaultCrudController<RES, RQ, E,I>(
    private val defualtService: DefualtService<RQ,RES,E,I>,
) : CrudController<RES, RQ, E,I> {


}