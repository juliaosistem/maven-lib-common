package com.common.lib.api.dtos.request

class KafkaMessage(
    val topic: String,
    val message: Any?
)
