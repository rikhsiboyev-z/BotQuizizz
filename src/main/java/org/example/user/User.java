package org.example.user;

import lombok.*;
import org.example.common.BaseEntity;


import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@Data
public class User extends BaseEntity<String> {
    private UserState userState;
    private String phoneNumber;
    private List<UUID> quizId;
    private Role role;


    @Builder
    public User(String id, UserState userState, String phoneNumber, List<UUID> quizId, Role role) {
        super(id);
        this.userState = userState;
        this.phoneNumber = phoneNumber;
        this.quizId = quizId;
        this.role = role;
    }
}

