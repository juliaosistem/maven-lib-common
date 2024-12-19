package com.common.lib.api.response




class KafkaRedisResponse <T>{
    lateinit var kafka: KafkaResponse<T>
   lateinit var redis: RedisResponse<T>
}

