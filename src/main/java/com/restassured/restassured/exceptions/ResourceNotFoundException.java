package com.restassured.restassured.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private String resourceName;
    private String sourceName;

    public ResourceNotFoundException(String resourceName, String sourceName) {

        super(String.format("%s was not found in %s.", resourceName, sourceName));

        this.resourceName = resourceName;
        this.sourceName = sourceName;
    }
}
