package com.junioroffers.domain.loginandregister.dto;

import java.util.UUID;

public record RegistrationResultDto(UUID id,
                                    boolean created,
                                    String username) {
}
