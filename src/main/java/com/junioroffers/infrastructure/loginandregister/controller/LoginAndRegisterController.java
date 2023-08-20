package com.junioroffers.infrastructure.loginandregister.controller;

import com.junioroffers.domain.loginandregister.LoginAndRegisterFacade;
import com.junioroffers.domain.loginandregister.dto.RegisterDto;
import com.junioroffers.domain.loginandregister.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
//@AllArgsConstructor
public class LoginAndRegisterController {

//    private final LoginAndRegisterFacade facade;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody RegisterDto data) {
//        try {
//            final UserDto user = facade.findByUsername(data.username());
//            return ResponseEntity.ok(user);
//        } catch (UserNotFoundException exception) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
        if (data.username().equals("user") && data.password().equals("password")) {
            UserDto resultDto = UserDto.builder()
                    .id(UUID.randomUUID())
                    .username(data.username())
                    .password(data.password())
                                .build();
            return ResponseEntity.ok(resultDto);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
