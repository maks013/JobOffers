package com.junioroffers.infrastructure.loginandregister.dto;

import lombok.Builder;

public record LoginResponseDto(
        String username,
        String token
) {
    @Builder public LoginResponseDto{}
}
