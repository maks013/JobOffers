package com.junioroffers.domain.loginandregister.dto;

import java.util.UUID;

public record UserDto(UUID id,
                      String username,
                      String password) {
}
