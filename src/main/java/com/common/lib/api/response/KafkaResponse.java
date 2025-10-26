package com.common.lib.api.response;

public class KafkaResponse<RES> extends ServiceResponse<RES> {
    public KafkaResponse(boolean success, String message, String error, RES data) {
        super(success, message, error, data);
    }
}


