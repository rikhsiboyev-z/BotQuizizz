package org.example.user;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserService {

    private static final UserService userService = new UserService();

    private final UserRepository userRepository = UserRepository.getInstance();

    public static UserService getInstance() {
        return userService;
    }


    public User getOrElseCreate(Long chatId) {

        Optional<User> optionalUser =
                userRepository.findById(chatId.toString());

        if (optionalUser.isPresent()) {

            return optionalUser.get();
        }
        User user = User.builder()
                .id(chatId.toString())
                .userState(UserState.NEW)
                .quizId(new ArrayList<>())
                .role(Role.USER)
                .build();
        userRepository.create(user);
        return user;

    }
}
