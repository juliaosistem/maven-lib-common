package com.common.lib.infraestructure.adapters.third

import com.common.lib.api.dtos.request.KafkaMessage
import com.common.lib.api.response.RedisResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@FeignClient(name = "redisClient", url = "\${redis.base-url}")
interface RedisFeignClient <RES> {

    /**
     * Obtiene datos de Redis filtrados por un campo específico.
     *
     * @param key El prefijo o nombre del canal en Redis.
     * @param fieldName El nombre del campo por el cual filtrar.
     * @param fieldValue El valor del campo para realizar la búsqueda.
     * @return Resultado de la búsqueda.
     */
    @GetMapping("/byFilter")
    fun getByField(
        @RequestParam("key") key: String,
        @RequestParam("fieldName") fieldName: String,
        @RequestParam("fieldValue") fieldValue: Any
    ): Mono<RedisResponse<RES>>

    /**
     * Obtiene datos de Redis a partir de un key específico.
     *
     * @param key El prefijo o nombre del canal en Redis.
     * @return El valor asociado al key.
     */
    @GetMapping("/get")
    fun get(
        @RequestParam("key") key: String
    ): Mono<RedisResponse<RES>>

    /**
     * Envia un mensaje a Kafka y luego lo guarda en Redis.
     *
     * @param body El mensaje a enviar.
     * @return Indica si el mensaje fue guardado correctamente en Redis.
     */
    @PostMapping("/send")
    fun sendToRedis(
        @RequestBody body: KafkaMessage
    ): Mono<RedisResponse<RES>>
}
