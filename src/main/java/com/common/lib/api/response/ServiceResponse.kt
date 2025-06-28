package com.common.lib.api.response

open class ServiceResponse<T>(
    val success: Boolean,
    val message: String,
    val error: String?,
    val data: T?
)



