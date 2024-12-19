package com.common.lib.infraestructure.adapters.third


import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import  com.common.lib.api.dtos.request.KafkaMessage
import  com.common.lib.api.response.KafkaRedisResponse
import com.common.lib.api.response.*
import com.common.lib.api.response.ServiceResponse
import com.common.lib.utils.enums.ResponseType
import reactor.core.scheduler.Schedulers
import org.springframework.http.HttpStatus
import reactor.core.publisher.Flux
import reactor.util.retry.Retry
import java.time.Duration


@Component
class DefaultAdapterKafkaAndRedis<RES>(
    private val redisFeignClient: RedisFeignClient<RES>,
    private val kafkaFeignClient: McsKafkaFeignClient<RES>,
)  {

    /**
     * Reintenta solo Kafka utilizando el endpoint específico.
     *
     * @param kafkaMessage El mensaje a enviar a Kafka.
     * @return Mono que emite la respuesta de Kafka.
     */
    fun retryKafka(kafkaMessage: KafkaMessage): Mono<KafkaResponse<RES>> {
        return kafkaFeignClient.sendToKafka(kafkaMessage)

    }

    /**
     * Reintenta solo Redis utilizando el endpoint específico.
     *
     * @param redisMessage El mensaje a enviar a Redis.
     * @return Mono que emite la respuesta de Redis.
     */
    fun retryRedis(redisMessage: KafkaMessage): Mono<RedisResponse<RES>> {
        return redisFeignClient.sendToRedis(redisMessage)
    }







    /**
     * Obtiene datos de cache en Redis utilizando un endpoint específico.
     *
     * @param key El key o nombre del canal en Redis.
     * @return  Mono<RedisResponse<RES>> que emite la respuesta obtenida desde Redis.
     */
    fun getRedis(key: String): Mono<RedisResponse<RES>> {
        return redisFeignClient.get(key)
    }

    /**
     * Obtiene datos de Redis filtrados por un campo específico.
     *
     * @param key El key o nombre del canal en Redis.
     * @param fieldName El nombre del campo por el cual filtrar.
     * @param fieldValue El valor del campo para realizar la búsqueda.
     * @return Mono que emite la respuesta obtenida desde Redis.
     */
    fun getRedisByField(key: String, fieldName: String, fieldValue: Any): Mono<RedisResponse<RES>> {
        return redisFeignClient.getByField(key, fieldName, fieldValue);
    }

    /**
     * Envía un mensaje a Kafka y luego a Redis, y responde con el que responda primero.
     *
     * Este método realiza un intento de envío de mensaje primero a Kafka y luego a Redis,
     * y devuelve la respuesta de uno de los dos sistemas dependiendo de cuál responda primero.
     *
     * @param body El mensaje a enviar, encapsulado en un objeto de tipo KafkaMessage.
     * @return Mono que emite la respuesta de Kafka o Redis, dependiendo del que responda primero.
     */
    fun sendMessage(body: KafkaMessage): Mono<PlantillaResponse<RES>> {
        return getFastestResponse(body)
            .onErrorResume {
                handleRetryOnError(body)
                    .map { response -> mapServiceResponseToPlantillaResponse(response) }
            }
            .doFinally {
                println("El flujo ha finalizado.")
            }
    }


    /**
     * Obtiene la respuesta más rápida entre Kafka y Redis.
     *
     * Este método compara las respuestas de Kafka y Redis y devuelve la que reciba primero la respuesta.
     *
     * @param body El mensaje que se enviará a Kafka y Redis.
     * @return Mono que emite la respuesta de Kafka o Redis, según cuál llegue primero.
     */
    private fun getFastestResponse(body: KafkaMessage): Mono<PlantillaResponse<RES>> {
        val kafkaResponse = retryKafka(body).map { mapKafkaResponse(it) }
        val redisResponse = retryRedis(body).map { mapRedisResponse(it) }

        return Mono.firstWithSignal<PlantillaResponse<RES>>(kafkaResponse, redisResponse)
            .flatMap { response ->
                Mono.justOrEmpty(response)
            }
            .publishOn(Schedulers.boundedElastic())
            .doFinally {
                Flux.merge(kafkaResponse, redisResponse)
                    .subscribe()
            }

    }



    /**
     * Mapea la respuesta de Kafka a un formato estándar de PlantillaResponse.
     *
     * @param response La respuesta de Kafka que se mapeará.
     * @return Un objeto `Partial` que encapsula la respuesta formateada de Kafka.
     */

    private fun mapKafkaResponse(response: KafkaResponse<RES>): PlantillaResponse<RES> {
        return PlantillaResponse.builder<RES>()
            .rta(response.success)
            .httpStatus(if (response.success) HttpStatus.OK else HttpStatus.INTERNAL_SERVER_ERROR)
            .data(response.data)
            .build()
    }


    /**
     * Mapea la respuesta de Redis a un formato estándar de PlantillaResponse.
     *
     * @param response La respuesta de Redis que se mapeará.
     * @return Un objeto `Partial` que encapsula la respuesta formateada de Redis.
     */
    private fun mapRedisResponse(response: RedisResponse<RES>): PlantillaResponse<RES> {
        return PlantillaResponse.builder<RES>()
            .rta(response.success)
            .httpStatus(if (response.success) HttpStatus.OK else HttpStatus.INTERNAL_SERVER_ERROR)
            .data(response.data)
            .build()
    }


    /**
     * Envia a redis y a kafka mensajes y espera a que  los dos servicios respondan .
     *
     * @param body lo que se envia
     * @return Mono<KafkaRedisResponse<RES>>
     */

    private fun sendRedisYkafka(body: KafkaMessage):Mono<KafkaRedisResponse<RES>>{
      return  Mono.zip(
            retryKafka(body),
            retryRedis(body)
        ).flatMap { tuple ->
            val kafkaResponse = tuple.t1
            val redisResponse = tuple.t2
            val updatedData = KafkaRedisResponse<RES>().apply {
                this.kafka = kafkaResponse
                this.redis = redisResponse
            }
            Mono.just(updatedData)
        }
   }

    private  fun validarRetry(body: KafkaMessage,data: KafkaRedisResponse<RES>, waitTime: Long, maxRetries :Int): Mono<KafkaRedisResponse<RES>> {
        if (!data.kafka.success && data.redis.success) {
            return retryKafka(body).delayElement(Duration.ofMillis(waitTime)).retryWhen(Retry.max(maxRetries.toLong()))
                .map { kafkaResponse ->
                    KafkaRedisResponse<RES>().apply {
                        this.kafka = kafkaResponse
                        this.redis = data.redis
                    }
                }
        }
        if (data.kafka.success && !data.redis.success) {
            return retryRedis(body).delayElement(Duration.ofMillis(waitTime)).retryWhen(Retry.max(maxRetries.toLong()))
                .map { redisResponse ->
                    KafkaRedisResponse<RES>().apply {
                        this.kafka = data.kafka
                        this.redis = redisResponse
                    }
                }
        }
        return  Mono.just(data);
    }


    /**
     * Maneja los reintentos cuando ocurre un error durante los procesos de Kafka o Redis.
     *
     * @param body El mensaje que se está enviando.
     * @param data datos para validar reintentos
     * @return Un Mono con la respuesta del servicio.
     */

    private fun retry(
        body: KafkaMessage,
        data: KafkaRedisResponse<RES>,
        retries: Int = 0
    ): Mono<KafkaRedisResponse<RES>> {
        val maxRetries = 8
        val waitTime = 120000L * retries
        return if (!data.kafka.success && !data.redis.success){ sendRedisYkafka(body) }
        else { validarRetry(body, data,waitTime,maxRetries) }
    }

    /**
     * MapeaServiceResponse  a  Plnatilla response n.
     *
     * @param serviceResponse El mensaje que se está enviando.
     */

    private fun mapServiceResponseToPlantillaResponse(serviceResponse: ServiceResponse<RES>): PlantillaResponse<RES> {
        return PlantillaResponse.builder<RES>()
            .rta(serviceResponse.success)
            .httpStatus(if (serviceResponse.success) HttpStatus.OK else HttpStatus.INTERNAL_SERVER_ERROR)
            .data(serviceResponse.data)
            .build()
    }


    /**
     * Envia Reintentos cuando paso un error usando el metodo Retry.
     *
     * @param body El mensaje que se está enviando.
     * @return Un Mono con la respuesta del servicio.
     */
    private fun handleRetryOnError(body: KafkaMessage): Mono<ServiceResponse<RES>> {
        return retry(body, KafkaRedisResponse())
            .map { kafkaRedisResponse ->
                if (kafkaRedisResponse.kafka.success && kafkaRedisResponse.redis.success) {
                    ServiceResponse(success = kafkaRedisResponse.kafka.success, message = kafkaRedisResponse.kafka.message, error = null, data = kafkaRedisResponse.kafka.data)
                } else {
                    when {
                        kafkaRedisResponse.kafka.success -> ServiceResponse(success = kafkaRedisResponse.kafka.success, message = kafkaRedisResponse.kafka.message,
                            error = null, data = kafkaRedisResponse.kafka.data)
                        kafkaRedisResponse.redis.success -> ServiceResponse(
                            success = kafkaRedisResponse.redis.success, message = kafkaRedisResponse.redis.message,
                            error = null, data = kafkaRedisResponse.redis.data)
                        else -> ServiceResponse(success = false, message = ResponseType.FALLO.message, error = null, data = null)
                    }
                }
            }
    }

}
