package com.common.lib.api.response;

import org.springframework.http.HttpStatus;

import java.util.List;

public class PlantillaResponse<RES> {
    private boolean rta;
    private String message;
    private HttpStatus httpStatus;
    private RES data;
    private List<RES> dataList;

    public PlantillaResponse() {
        this(false, "", HttpStatus.OK, null, null);
    }

    public PlantillaResponse(boolean rta, String message, HttpStatus httpStatus, RES data, List<RES> dataList) {
        this.rta = rta;
        this.message = message;
        this.httpStatus = httpStatus;
        this.data = data;
        this.dataList = dataList;
    }

    public boolean isRta() {
        return rta;
    }

    public void setRta(boolean rta) {
        this.rta = rta;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public RES getData() {
        return data;
    }

    public void setData(RES data) {
        this.data = data;
    }

    public List<RES> getDataList() {
        return dataList;
    }

    public void setDataList(List<RES> dataList) {
        this.dataList = dataList;
    }

    public static <RES> Builder<RES> builder() {
        return new Builder<>();
    }

    public static class Builder<RES> {
        private boolean rta = false;
        private String message = "";
        private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        private RES data;
        private List<RES> dataList;

        public Builder<RES> rta(boolean rta) {
            this.rta = rta;
            return this;
        }

        public Builder<RES> message(String message) {
            this.message = message;
            return this;
        }

        public Builder<RES> httpStatus(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
            return this;
        }

        public Builder<RES> data(RES data) {
            this.data = data;
            return this;
        }

        public Builder<RES> dataList(List<RES> dataList) {
            this.dataList = dataList;
            return this;
        }

        public PlantillaResponse<RES> build() {
            return new PlantillaResponse<>(rta, message, httpStatus, data, dataList);
        }
    }
}


