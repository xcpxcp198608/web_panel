package com.wiatec.panel.xutils.result;

public class XException extends RuntimeException {

    private int code;

    public XException(int code, String message) {
        super(message);
        this.code = code;
    }
}
