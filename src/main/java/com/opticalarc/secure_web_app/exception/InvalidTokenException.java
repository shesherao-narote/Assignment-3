package com.opticalarc.secure_web_app.exception;

public class InvalidTokenException extends RuntimeException{
    String resourceName;
    String fieldName;
    String fieldValue;

    public InvalidTokenException(String resourceName){
        super(String.format(" %s is Invalid",resourceName));
        this.resourceName = resourceName;
    }
}
