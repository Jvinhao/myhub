package org.lf.community.exception;


public class CustomizeException extends RuntimeException{
    private String message;
    private Integer code;
    public CustomizeException(CustomizeExceptionCode customizeExceptionCode) {
        this.code = customizeExceptionCode.getCode();
        this.message = customizeExceptionCode.getMessage();
    }

    public CustomizeException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
