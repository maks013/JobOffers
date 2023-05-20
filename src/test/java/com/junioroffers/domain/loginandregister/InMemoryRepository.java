package com.junioroffers.domain.loginandregister;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class InMemoryRepository implements LoginRepository {

    private static final UUID FIRST_ACCOUNT_ID = UUID.randomUUID();
    private static final HashMap<String,User> database = new HashMap<>();

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(database.get(username));
    }

    @Override
    public User save(User userData) {
        User user = new User(
                FIRST_ACCOUNT_ID,
                userData.username(),
                userData.password()
        );
        database.put(user.username(),user);

        return user;
    }
}
