package com.common.lib.api.response;

public class KafkaRedisResponse<T> {
    private KafkaResponse<T> kafka;
    private RedisResponse<T> redis;

    public KafkaRedisResponse() {
    }

    public KafkaResponse<T> getKafka() {
        return kafka;
    }

    public void setKafka(KafkaResponse<T> kafka) {
        this.kafka = kafka;
    }

    public RedisResponse<T> getRedis() {
        return redis;
    }

    public void setRedis(RedisResponse<T> redis) {
        this.redis = redis;
    }
}


