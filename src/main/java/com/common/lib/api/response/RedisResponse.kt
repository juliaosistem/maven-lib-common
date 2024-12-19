package com.common.lib.api.response

import lombok.Builder
import lombok.Data

@Data
@Builder
class RedisResponse<T>(
    success: Boolean,
    message: String,
    error: String?,
    data: T?
) : ServiceResponse<T>(success, message, error, data)
