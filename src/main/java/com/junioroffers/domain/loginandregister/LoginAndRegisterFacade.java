package com.junioroffers.domain.loginandregister;

import com.junioroffers.domain.loginandregister.dto.RegisterDto;
import com.junioroffers.domain.loginandregister.dto.RegistrationResultDto;
import com.junioroffers.domain.loginandregister.dto.UserDto;
import com.junioroffers.domain.loginandregister.exception.InvalidUserRegistration;
import com.junioroffers.domain.loginandregister.exception.UserNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginAndRegisterFacade {

    private final LoginRepository repository;

    public UserDto findByUsername(String username) {
        return repository.findByUsername(username)
                .map(user -> new UserDto(user.id(), user.username(), user.password()))
                .orElseThrow(UserNotFoundException::new);
    }

    public RegistrationResultDto register(RegisterDto registerDto) {
        final User user;
        if (registerDto.username() == null || registerDto.password() == null) {
            throw new InvalidUserRegistration();
        } else {
            user = User.builder()
                    .username(registerDto.username())
                    .password(registerDto.password())
                    .build();
        }
        User savedUser = repository.save(user);
        return new RegistrationResultDto(savedUser.id(), true, savedUser.username());
    }

}
