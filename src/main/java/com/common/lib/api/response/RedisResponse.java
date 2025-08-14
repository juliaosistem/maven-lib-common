package com.common.lib.api.response;

public class RedisResponse<RES> extends ServiceResponse<RES> {
    public RedisResponse(boolean success, String message, String error, RES data) {
        super(success, message, error, data);
    }
}


