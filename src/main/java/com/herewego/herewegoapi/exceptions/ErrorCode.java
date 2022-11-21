package com.herewego.herewegoapi.exceptions;

public enum ErrorCode {
    RC400000("Bad Request. (%s)"),
    RC500000("Internal Server error");

    private final String message;

    ErrorCode(String message) { this.message = message; }

    public String getMessage() { return message; }
}
