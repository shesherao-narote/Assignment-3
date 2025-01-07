package com.opticalarc.secure_web_app.exception;

public class InvalidUsernamePasswordException extends RuntimeException{

    String resourceName;
    String fieldValue;

    public InvalidUsernamePasswordException(String resourceName, String fieldValue){
        super(String.format("%s : %s is Invalid",resourceName,  fieldValue));
        this.resourceName = resourceName;
        this.fieldValue = fieldValue;
    }
}
