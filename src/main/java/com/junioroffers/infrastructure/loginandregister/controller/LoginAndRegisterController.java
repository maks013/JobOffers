package com.junioroffers.infrastructure.loginandregister.controller;

import com.junioroffers.domain.loginandregister.LoginAndRegisterFacade;
import com.junioroffers.domain.loginandregister.dto.RegisterDto;
import com.junioroffers.domain.loginandregister.dto.RegistrationResultDto;
import com.junioroffers.infrastructure.loginandregister.dto.LoginRequestDto;
import com.junioroffers.infrastructure.loginandregister.dto.LoginResponseDto;
import com.junioroffers.infrastructure.security.jwt.JwtAuthenticator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class LoginAndRegisterController {

    private final JwtAuthenticator jwtAuthenticator;

    private final LoginAndRegisterFacade loginAndRegisterFacade;
    private final PasswordEncoder bCryptEncoder;

    @PostMapping("/login")

    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        final LoginResponseDto loginResponseDto = jwtAuthenticator.authenticate(loginRequestDto);
        return ResponseEntity.ok(loginResponseDto);
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationResultDto> register(@Valid @RequestBody RegisterDto registerDto) {
        String encodedPassword = bCryptEncoder.encode(registerDto.password());
        RegistrationResultDto registrationResultDto = loginAndRegisterFacade.register(
                new RegisterDto(registerDto.username(), encodedPassword));

        return ResponseEntity.status(HttpStatus.CREATED).body(registrationResultDto);
    }
}
