package org.example.quiz;

import lombok.*;
import org.example.common.BaseEntity;
import org.telegram.telegrambots.meta.api.objects.polls.Poll;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@ToString(callSuper = true)
public class Quiz extends BaseEntity<UUID> {

    private List<Poll> pols;
    private String name;
    private QuizStatus status;
    private String creatorId;

    @Builder

    public Quiz(UUID uuid, List<Poll> pols, String name, QuizStatus status, String creatorId) {
        super(uuid);
        this.pols = pols;
        this.name = name;
        this.status = status;
        this.creatorId = creatorId;
    }
}
