package com.bq.task.response;

/**
 * @author JHON
 * @date 2020/06/19
 * @description:
 **/
public class ErrorResponseData<T> extends ResponseData<T> {
    /**
     * 异常的具体类名称
     */
    private String exceptionClazz;

    public ErrorResponseData(String message) {
        super(false, ResponseData.DEFAULT_ERROR_CODE, message, null);
    }

    public ErrorResponseData(Integer code, String message) {
        super(false, code, message, null);
    }

    public ErrorResponseData(Integer code, String message, String exceptionClazz) {
        super(false, code, message, null);
        this.exceptionClazz = exceptionClazz;
    }

    public ErrorResponseData(Integer code, String message, T object) {
        super(false, code, message, object);
    }

    public ErrorResponseData(Integer code, String message, T object, String exceptionClazz) {
        super(false, code, message, object);
        this.exceptionClazz = exceptionClazz;
    }

    public String getExceptionClazz() {
        return exceptionClazz;
    }

    public void setExceptionClazz(String exceptionClazz) {
        this.exceptionClazz = exceptionClazz;
    }
}
