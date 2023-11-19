package org.example.handlers;


import lombok.SneakyThrows;
import org.example.bot.MessageBuilder;
import org.example.quiz.Quiz;
import org.example.quiz.QuizRepository;
import org.example.quiz.QuizStatus;
import org.example.user.User;
import org.example.user.UserState;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.polls.Poll;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserHandler {

    private final MessageBuilder messageBuilder = new MessageBuilder();
    private final QuizRepository quizRepository = QuizRepository.getInstance();
    private final DefaultAbsSender sender;

    public UserHandler(DefaultAbsSender defaultAbsSender) {

        this.sender = defaultAbsSender;
    }

    public void handle(Update update, User user) {


        switch (user.getUserState()) {
            case NEW -> requestPhoneNumber(update, user);
            case MAIN -> handleMain(update, user);
            case ENTER_QUIZ_NAME -> createQuiz(update, user);
        }

    }

    @SneakyThrows
    private void createQuiz(Update update, User user) {

        if (update.getMessage().hasText()) {
            String quizName = update.getMessage().getText();

            Optional<Quiz> optionalQuiz = quizRepository.findByUserAndStatus(user.getId(), QuizStatus.IN_PROGRESS);

            if (quizName.equals("/done")) {
                optionalQuiz.get().setStatus(QuizStatus.FINISHED);
                user.setUserState(UserState.MAIN);
                sender.execute(messageBuilder.ButtonDone(user));
                sender.execute(messageBuilder.getMainMenu(user));
                return;

            }

            if (optionalQuiz.isPresent()) {
                return;
            }

            Quiz newQuiz = Quiz.builder()
                    .name(quizName)
                    .pols(new ArrayList<>())
                    .creatorId(user.getId())
                    .status(QuizStatus.IN_PROGRESS)
                    .build();
            quizRepository.create(newQuiz);

        }
        if (update.getMessage().hasPoll()) {

            Poll poll = update.getMessage().getPoll();
            Quiz quiz = quizRepository.findByUserAndStatus(user.getId(), QuizStatus.IN_PROGRESS).get();
            List<Poll> pols = quiz.getPols();
            pols.add(poll);


        }

        SendMessage sendMessage = messageBuilder.requestCreateQuiz(user);
        sender.execute(sendMessage);
    }

    @SneakyThrows
    private void handleMain(Update update, User user) {
        if (update.hasMessage()) {
            sender.execute(messageBuilder.getMainMenu(user));
        } else {
            switch (update.getCallbackQuery().getData()) {
                case "1" -> {
                    sender.execute(messageBuilder.createQuiz(update, user));
                    user.setUserState(UserState.ENTER_QUIZ_NAME);
                }
                case "2" -> showMyQuiz(user);
            }
        }

    }

    @SneakyThrows
    private void showMyQuiz(User user) {

        List<Quiz> quizzes = quizRepository.findByUserId(user.getId());
        SendMessage sendMessage = messageBuilder.buildShowQuizzes(quizzes, user);

        if (quizzes.isEmpty()) {
            sendMessage.setChatId(user.getId());
            sendMessage.setText("Sizda quizlar mavjud emas");
        }

        sender.execute(sendMessage);

    }

    @SneakyThrows
    private void requestPhoneNumber(Update update, User user) {
        if (update.getMessage().hasContact()) {

            Contact contact = update.getMessage().getContact();
            user.setPhoneNumber(contact.getPhoneNumber());
            user.setUserState(UserState.MAIN);
            sender.execute(messageBuilder.getWelcomeMessage(user, update.getMessage().getChat().getFirstName()));
            sender.execute(messageBuilder.photo(user));
            sender.execute(messageBuilder.getMainMenu(user));

        } else {

            SendMessage sendMessage = getSendMessage(user);
            sender.execute(sendMessage);
        }


    }

    private static SendMessage getSendMessage(User user) {
        SendMessage sendMessage = new SendMessage(user.getId(), "Telefon raqamagizni yuboring \uD83D\uDCDE");

        KeyboardButton keyboardButton = new KeyboardButton("Phone \uD83D\uDCDE");
        keyboardButton.setRequestContact(true);
        KeyboardRow keyboardButtons = new KeyboardRow(List.of(keyboardButton));
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(List.of(keyboardButtons));
        replyKeyboardMarkup.setResizeKeyboard(true);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }


}