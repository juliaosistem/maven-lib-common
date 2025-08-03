package com.common.lib.infraestructure.adapters.third

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import com.common.lib.api.dtos.request.KafkaMessage
import com.common.lib.api.response.KafkaRedisResponse
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
) {

    fun retryKafka(kafkaMessage: KafkaMessage): Mono<KafkaResponse<RES>> {
        return kafkaFeignClient.sendToKafka(kafkaMessage)
    }

    fun retryRedis(redisMessage: KafkaMessage): Mono<RedisResponse<RES>> {
        return redisFeignClient.sendToRedis(redisMessage)
    }

    fun getRedis(key: String): Mono<RedisResponse<RES>> {
        return redisFeignClient.get(key)
    }

    fun getRedisByField(key: String, fieldName: String, fieldValue: Any): Mono<RedisResponse<RES>> {
        return redisFeignClient.getByField(key, fieldName, fieldValue)
    }

//    fun sendMessage(body: KafkaMessage): Mono<PlantillaResponse<RES>> {
//        return getFastestResponse(body)
//            .onErrorResume {
//                handleRetryOnError(body)
//                    .map { response -> mapServiceResponseToPlantillaResponse(response) }
//            }
//            .doFinally {
//                println("El flujo ha finalizado.")
//            }
//    }

    private fun getFastestResponse(body: KafkaMessage): Mono<PlantillaResponse<RES>> {
        val kafkaResponse = retryKafka(body).map { mapKafkaResponse(it) }
        val redisResponse = retryRedis(body).map { mapRedisResponse(it) }

        return Mono.firstWithSignal(kafkaResponse, redisResponse)
            .flatMap { response -> Mono.justOrEmpty(response) }
            .publishOn(Schedulers.boundedElastic())
            .doFinally {
                Flux.merge(kafkaResponse, redisResponse).subscribe()
            }
    }

    private fun mapKafkaResponse(response: KafkaResponse<RES>): PlantillaResponse<RES> {
        return PlantillaResponse.builder<RES>()
            .rta(response.success)
            .httpStatus(if (response.success) HttpStatus.OK else HttpStatus.INTERNAL_SERVER_ERROR)
            .data(response.data)
            .build()
    }

    private fun mapRedisResponse(response: RedisResponse<RES>): PlantillaResponse<RES> {
        return PlantillaResponse.builder<RES>()
            .rta(response.success)
            .httpStatus(if (response.success) HttpStatus.OK else HttpStatus.INTERNAL_SERVER_ERROR)
            .data(response.data )
            .build()
    }

    private fun sendRedisYkafka(body: KafkaMessage): Mono<KafkaRedisResponse<RES>> {
        return Mono.zip(
            retryKafka(body),
            retryRedis(body)
        ).flatMap { tuple ->
            Mono.just(
                KafkaRedisResponse<RES>().apply {
                    this.kafka = tuple.t1
                    this.redis = tuple.t2
                }
            )
        }
    }

    private fun validarRetry(
        body: KafkaMessage,
        data: KafkaRedisResponse<RES>,
        waitTime: Long,
        maxRetries: Int
    ): Mono<KafkaRedisResponse<RES>> {
        return when {
            !data.kafka.success && data.redis.success -> retryKafka(body)
                .delayElement(Duration.ofMillis(waitTime))
                .retryWhen(Retry.max(maxRetries.toLong()))
                .map { kafkaResponse ->
                    KafkaRedisResponse<RES>().apply {
                        this.kafka = kafkaResponse
                        this.redis = data.redis
                    }
                }

            data.kafka.success && !data.redis.success -> retryRedis(body)
                .delayElement(Duration.ofMillis(waitTime))
                .retryWhen(Retry.max(maxRetries.toLong()))
                .map { redisResponse ->
                    KafkaRedisResponse<RES>().apply {
                        this.kafka = data.kafka
                        this.redis = redisResponse
                    }
                }

            else -> Mono.just(data)
        }
    }

    private fun retry(
        body: KafkaMessage,
        data: KafkaRedisResponse<RES> = KafkaRedisResponse(),
        retries: Int = 0
    ): Mono<KafkaRedisResponse<RES>> {
        val maxRetries = 8
        val waitTime = 120000L * retries
        return if (!data.kafka.success && !data.redis.success) {
            sendRedisYkafka(body)
        } else {
            validarRetry(body, data, waitTime, maxRetries)
        }
    }

    private fun mapServiceResponseToPlantillaResponse(
        serviceResponse: ServiceResponse<RES>
    ): PlantillaResponse<RES> {
        return PlantillaResponse.builder<RES>()
            .rta(serviceResponse.success)
            .httpStatus(if (serviceResponse.success) HttpStatus.OK else HttpStatus.INTERNAL_SERVER_ERROR)
            .data(serviceResponse.data as RES)
            .build()
    }

//    private fun handleRetryOnError(body: KafkaMessage): Mono<ServiceResponse<RES>> {
//        return retry(body)
//            .map { kafkaRedisResponse ->
//                when {
//                    kafkaRedisResponse.kafka.success && kafkaRedisResponse.redis.success ->
//                        ServiceResponse(
//                            success = kafkaRedisResponse.kafka.success,
//                            message = kafkaRedisResponse.kafka.message,
//                            error = null,
//                            data = kafkaRedisResponse.kafka.data
//                        )
//
//                    kafkaRedisResponse.kafka.success ->
//                        ServiceResponse(
//                            success = kafkaRedisResponse.kafka.success,
//                            message = kafkaRedisResponse.kafka.message,
//                            error = null,
//                            data = kafkaRedisResponse.kafka.data
//                        )
//
//                    kafkaRedisResponse.redis.success ->
//                        ServiceResponse(
//                            success = kafkaRedisResponse.redis.success,
//                            message = kafkaRedisResponse.redis.message,
//                            error = null,
//                            data = kafkaRedisResponse.redis.data
//                        )
//
//                    else ->
//                        ServiceResponse(
//                            success = false,
//                            message = ResponseType.FALLO.message,
//                            error = null,
//                            data = null
//                        )
//                }
//            }
//    }
}