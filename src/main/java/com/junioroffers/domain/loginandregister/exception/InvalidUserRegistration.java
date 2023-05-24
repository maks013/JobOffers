package com.junioroffers.domain.loginandregister.exception;

public class InvalidUserRegistration extends RuntimeException {
    public InvalidUserRegistration(){
        super("Can not register account");
    }
}
