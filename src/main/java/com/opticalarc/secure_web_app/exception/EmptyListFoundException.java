package com.opticalarc.secure_web_app.exception;

public class EmptyListFoundException extends RuntimeException{

    String resourceName;

    Long resourceValue;

    public EmptyListFoundException(String resourceName){
        super(String.format(" %s ",resourceName));
        this.resourceName = resourceName;
    }

    public EmptyListFoundException(String resourceName,Long resourceValue){
        super(String.format(" %s ",resourceName));
        this.resourceName = resourceName;
        this.resourceValue = resourceValue;
    }
}
