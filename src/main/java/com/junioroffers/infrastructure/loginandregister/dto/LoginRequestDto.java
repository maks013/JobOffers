package com.junioroffers.infrastructure.loginandregister.dto;

import lombok.Builder;

import javax.validation.constraints.NotBlank;

public record LoginRequestDto(
        @NotBlank(message = "username can not be blank")
        String username,
        @NotBlank(message = "password can not be blank")
        String password
) {
    @Builder public LoginRequestDto{}
}
