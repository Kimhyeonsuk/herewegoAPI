package com.herewego.herewegoapi.exceptions;

import lombok.Data;

@Data
public class ForwardException extends Exception{

    private static final long serialVersionUID = 1L;

    private String responseCode;
    private String responseMessage;

    public ForwardException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.responseCode = errorCode.name();
        this.responseMessage = errorCode.getMessage();
    }

    public ForwardException(ErrorCode errorCode, Object ... params) {
        super(String.format(errorCode.getMessage(), params));
        this.responseCode = errorCode.name();
        this.responseMessage = errorCode.getMessage();
    }
}
