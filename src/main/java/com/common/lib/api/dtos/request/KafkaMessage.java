package com.common.lib.api.dtos.request;

public class KafkaMessage {
    private String topic;
    private Object message;

    public KafkaMessage() {
    }

    public KafkaMessage(String topic, Object message) {
        this.topic = topic;
        this.message = message;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }
}


