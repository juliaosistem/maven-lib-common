package com.common.lib.api.response

import lombok.Builder
import lombok.Data

@Data
@Builder
class RedisResponse<RES>(
    success: Boolean,
    message: String,
    error: String?,
    data: RES
) : ServiceResponse<RES>(success, message, error, data)
