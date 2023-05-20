package com.junioroffers.domain.loginandregister.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(){
        super("User not found");
    }
}
