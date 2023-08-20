package com.junioroffers.domain.loginandregister;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class LoginAndRegisterFacadeConfig {

    @Bean
    public LoginAndRegisterFacade loginAndRegisterFacade(){
        LoginRepository loginRepository = new LoginRepository() {
            @Override
            public Optional<User> findByUsername(String username) {
                return Optional.empty();
            }

            @Override
            public User save(User user) {
                return null;
            }
        };
        return new LoginAndRegisterFacade(loginRepository);
    }
}
