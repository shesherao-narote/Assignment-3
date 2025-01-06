package com.opticalarc.secure_web_app.exception;

public class InvalidTokenException extends RuntimeException{
    String resourceName;
    String fieldName;
    String fieldValue;

    public InvalidTokenException(String resourceName, String fieldName, String fieldValue){
        super(String.format("%s With %s : %s is Invalid",resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
