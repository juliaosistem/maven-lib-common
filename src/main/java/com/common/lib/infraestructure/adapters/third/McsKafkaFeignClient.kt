package com.common.lib.infraestructure.adapters.third

import com.common.lib.api.dtos.request.KafkaMessage
import com.common.lib.api.response.KafkaResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import reactor.core.publisher.Mono

@FeignClient(name = "kafkaClient", url = "\${kafka.base-url}")
interface McsKafkaFeignClient<RES> {

    /**
     * Envía un mensaje a Kafka y luego a Redis.
     * 
     * @param topic El nombre del tópico en Kafka.
     * @param message El mensaje a enviar.
     * @return La respuesta que contiene los resultados de Kafka y Redis.
     */
    @PostMapping("/kafka/send")
    fun sendMessage(
        @RequestBody body: KafkaMessage
    ): Map<String, Any>

    /**
     * Envía un mensaje solo a Kafka.
     * 
     * @param topic El nombre del tópico en Kafka.
     * @param message El mensaje a enviar.
     * @return La respuesta de Kafka.
     */
    @PostMapping("/kafka/send-kafka")
    fun sendToKafka(
        @RequestBody body: KafkaMessage
    ): Mono<KafkaResponse<RES>>
}
