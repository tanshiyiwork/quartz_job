package com.bq.task.response;

/**
 * @author JHON
 * @date 2020/06/19
 * @description:
 **/
public class SuccessResponseData<T> extends ResponseData<T> {
    public SuccessResponseData() {
        super(true, DEFAULT_SUCCESS_CODE, DEFAULT_SUCCESS_MESSAGE, null);
    }

    public SuccessResponseData(T object) {
        super(true, DEFAULT_SUCCESS_CODE, DEFAULT_SUCCESS_MESSAGE, object);
    }

    public SuccessResponseData(Integer code, String message, T object) {
        super(true, code, message, object);
    }
}
