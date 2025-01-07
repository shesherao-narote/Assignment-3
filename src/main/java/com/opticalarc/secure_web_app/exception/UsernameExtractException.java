package com.opticalarc.secure_web_app.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsernameExtractException extends RuntimeException{

    String resourceName;
    String fieldName;
    String fieldValue;

    public UsernameExtractException(String resourceName){
        super(String.format(" %s ",resourceName));
        this.resourceName = resourceName;
    }
}
