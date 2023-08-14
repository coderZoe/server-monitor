package com.coderzoe.model.common;

import lombok.Data;

/**
 * @author coderZoe
 * @date 2023/8/13 21:55
 */
@Data
public class CommonReturn<T> {
    public Boolean success;
    private String message;
    private T result;

    public static <T> CommonReturn<T> success(T result) {
        return new CommonReturn<T>(result);
    }

    public static <T> CommonReturn<T> fail(String failReason) {
        return new CommonReturn<>(failReason);
    }

    private CommonReturn(T result) {
        this.success = true;
        this.result = result;
    }

    private CommonReturn(String message) {
        this.success = false;
        this.message = message;
    }
}
