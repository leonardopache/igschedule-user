package com.pache.igscheduleuser.exception;

/**
 * Object returned as JSON if error
 * Created by lpache on 7/22/17.
 */
public class ErrorResponse {

    private int errorCode;
    private String message;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
