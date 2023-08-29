package com.junioroffers.infrastructure.loginandregister.exception;

import org.springframework.http.HttpStatus;

public record LoginErrorResponse(String message,
                                 HttpStatus status) {
}
