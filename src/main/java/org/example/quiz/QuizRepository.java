package org.example.quiz;

import org.example.common.BaseRepository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class QuizRepository extends BaseRepository<Quiz, UUID> {

    private static final QuizRepository quizRepository = new QuizRepository();

    public static QuizRepository getInstance() {
        return quizRepository;
    }

    public Optional<Quiz> findByUserAndStatus(String id, QuizStatus quizStatus) {

        List<Quiz> all = getAll();
        return all.stream()
                .filter(quiz -> quiz.getCreatorId().equals(id))
                .filter(quiz -> quiz.getStatus().equals(quizStatus))
                .findFirst();


    }

    public List<Quiz> findByUserId(String  id) {

        return getAll().stream().filter(quiz -> quiz.getCreatorId().equals(id)).toList();


    }
}
