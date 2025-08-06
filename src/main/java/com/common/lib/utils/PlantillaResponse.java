package com.common.lib.utils;

import org.springframework.http.HttpStatus;

/**
 * Clase para manejar la tabla de datos compartida
 * 
 * @param <E> Clase de respuesta que contiene la data
 * @version 1
 * @author Daniel juliao
 */
public class PlantillaResponse<E> {
    private Boolean rta;
    private String message;
    private HttpStatus httpStatus;
    private E data;
    private E[] dataList;

    public PlantillaResponse() {
    }

    public PlantillaResponse(Boolean rta, String message, HttpStatus httpStatus, E data, E[] dataList) {
        this.rta = rta;
        this.message = message;
        this.httpStatus = httpStatus;
        this.data = data;
        this.dataList = dataList;
    }

    // Getters y Setters
    public Boolean getRta() {
        return rta;
    }

    public void setRta(Boolean rta) {
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

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public E[] getDataList() {
        return dataList;
    }

    public void setDataList(E[] dataList) {
        this.dataList = dataList;
    }

    public static <E> Builder<E> builder() {
        return new Builder<>();
    }

    public static class Builder<E> {
        private Boolean rta;
        private String message;
        private HttpStatus httpStatus;
        private E data;
        private E[] dataList;

        public Builder<E> rta(Boolean rta) {
            this.rta = rta;
            return this;
        }

        public Builder<E> message(String message) {
            this.message = message;
            return this;
        }

        public Builder<E> httpStatus(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
            return this;
        }

        public Builder<E> data(E data) {
            this.data = data;
            return this;
        }

        public Builder<E> dataList(E[] dataList) {
            this.dataList = dataList;
            return this;
        }

        public PlantillaResponse<E> build() {
            return new PlantillaResponse<>(rta, message, httpStatus, data, dataList);
        }
    }
} 