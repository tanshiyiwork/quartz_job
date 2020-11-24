package com.bq.task.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author JHON
 * @date 2020/06/19
 * @description:
 **/

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ResponseData<T> {
    public static final String DEFAULT_SUCCESS_MESSAGE = "请求成功";

    public static final String DEFAULT_ERROR_MESSAGE = "网络异常";

    public static final Integer DEFAULT_SUCCESS_CODE = 0;

    public static final Integer DEFAULT_ERROR_CODE = 500;

    /**
     * 请求是否成功
     */
    private Boolean success;

    /**
     * 响应状态码
     */
    private Integer errcode;

    /**
     * 响应信息
     */
    private String errmessage;

    /**
     * 响应对象
     */
    private T data;

    public ResponseData() {
    }

    public ResponseData(Boolean success, Integer code, String message, T data) {
        this.success = success;
        this.errcode = code;
        this.errmessage = message;
        this.data = data;
    }

    public static SuccessResponseData<?> success() {
        return new SuccessResponseData<>();
    }

    public static <T> SuccessResponseData<T> success(T object) {
        return new SuccessResponseData<T>(object);
    }

    public static <T> SuccessResponseData<T> success(Integer code, String message, T object) {
        return new SuccessResponseData<T>(code, message, object);
    }

    public static ErrorResponseData<?> error(String message) {
        return new ErrorResponseData<>(message);
    }

    public static ErrorResponseData<?> error(Integer code, String message) {
        return new ErrorResponseData<>(code, message);
    }

    public static <T> ErrorResponseData<T> error(Integer code, String message, T object) {
        return new ErrorResponseData<T>(code, message, object);
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmessage() {
        return errmessage;
    }

    public void setErrmessage(String errmessage) {
        this.errmessage = errmessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
