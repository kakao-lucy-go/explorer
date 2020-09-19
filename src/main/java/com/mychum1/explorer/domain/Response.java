package com.mychum1.explorer.domain;

/**
 * code는 HTTP 의 코드 의미를 따른다.
 * @param <T>
 */
public class Response<T> {
    private Integer code;
    private String msg;
    private T data;

    public Response(){}
    public Response(Integer code, String msg, T data) {
        this.code=code;
        this.msg=msg;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
