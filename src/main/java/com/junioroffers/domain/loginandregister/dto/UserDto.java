package com.junioroffers.domain.loginandregister.dto;

import lombok.Builder;

import java.util.UUID;

public record UserDto(String id,
                      String username,
                      String password) {
    @Builder public UserDto{}
}
