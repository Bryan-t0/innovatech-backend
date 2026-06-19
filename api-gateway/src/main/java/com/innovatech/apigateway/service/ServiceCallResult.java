package com.innovatech.apigateway.service;

public record ServiceCallResult<T>(T data, boolean fallbackUsed) {
    public static <T> ServiceCallResult<T> success(T data) {
        return new ServiceCallResult<>(data, false);
    }

    public static <T> ServiceCallResult<T> fallback(T data) {
        return new ServiceCallResult<>(data, true);
    }
}
