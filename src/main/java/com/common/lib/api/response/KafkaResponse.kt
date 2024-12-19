package com.common.lib.api.response

import lombok.Builder
import lombok.Data

@Data
@Builder
 class KafkaResponse<T>(
    success: Boolean,
    message: String,
    error: String?,
    data: T?
) : ServiceResponse<T>(success, message, error, data)