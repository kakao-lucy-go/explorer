package com.mychum1.explorer.exception;

public class SearchException extends Exception {

    private Integer code;

    public SearchException(Integer code, String msg, Throwable e) {
        super(msg, e);
        this.code=code;
    }

    public SearchException(Integer code, String msg) {
        super(msg);
        this.code=code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
