package com.junioroffers.domain.loginandregister;

import com.junioroffers.domain.loginandregister.dto.RegisterDto;
import com.junioroffers.domain.loginandregister.dto.RegistrationResultDto;
import com.junioroffers.domain.loginandregister.dto.UserDto;
import com.junioroffers.domain.loginandregister.exception.InvalidUserRegistration;
import com.junioroffers.domain.loginandregister.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginAndRegisterFacadeTest {

    private final LoginAndRegisterFacade loginFacade = new LoginAndRegisterFacade(
            new InMemoryRepository()
    );

    @Test
     void should_throw_exception_when_user_not_found() {
        //given
        String user = "username";
        //when
        //then
        assertThrows(UserNotFoundException.class, () -> loginFacade.findByUsername(user));
    }

    @Test
    void should_find_user_by_username() {
        //given
        RegisterDto user = new RegisterDto("username","password");
        loginFacade.register(user);
        //when
        UserDto userDto = loginFacade.findByUsername("username");
        //then
        assertEquals(userDto.username(),"username");
    }

    @Test
    void should_register_user() {
        //given
        RegisterDto registerUserDto = new RegisterDto("username", "password");
        //when
        RegistrationResultDto register = loginFacade.register(registerUserDto);
        //then
        assertAll(
                () -> assertTrue(register.created()),
                () -> assertEquals(register.username(),"username")
        );
    }

    @Test
    void should_throw_exception_when_username_is_null() {
        //given
        RegisterDto registerUserDto = new RegisterDto(null, "password");
        //when
        //then
        assertThrows(InvalidUserRegistration.class,()-> loginFacade.register(registerUserDto));
    }

    @Test
    void should_throw_exception_when_password_is_null() {
        //given
        RegisterDto registerUserDto = new RegisterDto("username", null);
        //when
        //then
        assertThrows(InvalidUserRegistration.class,()-> loginFacade.register(registerUserDto));
    }

}
