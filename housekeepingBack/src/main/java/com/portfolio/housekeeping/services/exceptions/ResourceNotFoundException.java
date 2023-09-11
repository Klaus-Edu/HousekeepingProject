package com.portfolio.housekeeping.services.exceptions;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(Object id){
        super("Resource not found! Id " + id);
    }

    public ResourceNotFoundException(){
        super("Resource not found!");
    }
}
