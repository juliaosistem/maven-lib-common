package com.common.lib.api.response

open class ServiceResponse<RES>(
    val success: Boolean,
    val message: String,
    val error: String?,
    val data: RES
)



