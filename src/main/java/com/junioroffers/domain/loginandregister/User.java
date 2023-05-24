package com.junioroffers.domain.loginandregister;

import lombok.Builder;

import java.util.UUID;

record User(UUID id,
            String username,
            String password) {
    @Builder User{}
}
