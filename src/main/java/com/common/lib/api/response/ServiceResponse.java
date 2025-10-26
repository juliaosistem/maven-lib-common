package com.common.lib.api.response;

public class ServiceResponse<RES> {
    private final boolean success;
    private final String message;
    private final String error;
    private final RES data;

    public ServiceResponse(boolean success, String message, String error, RES data) {
        this.success = success;
        this.message = message;
        this.error = error;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }

    public RES getData() {
        return data;
    }
}


