package org.example.user;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.common.BaseRepository;

@NoArgsConstructor(access = AccessLevel.PRIVATE)

public class UserRepository extends BaseRepository<User, String> {
    private static final UserRepository userRepository = new UserRepository();

    public static UserRepository getInstance() {
        return userRepository;
    }
}
