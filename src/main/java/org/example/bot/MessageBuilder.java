package org.example.bot;

import org.example.quiz.Quiz;
import org.example.user.Callback;
import org.example.user.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButtonPollType;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import javax.swing.*;
import java.io.File;
import java.util.List;

public class MessageBuilder {

    public SendPhoto photo(User user) {
        return new SendPhoto(user.getId(), new InputFile(new File("D:\\Projects\\MavenProject\\newBot\\src\\" +
                "main\\java\\org\\example\\image\\photo_2023-07-28_12-06-00.jpg")));
    }



    public SendMessage getMainMenu(User user) {

        SendMessage sendMessage = new SendMessage(user.getId(), "Bu bot sizga quiz yaratishda yordam beradi" + "\n" + "/done");
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton createQuiz = new InlineKeyboardButton("Quiz yaratish");
        createQuiz.setCallbackData(Callback.CREATE_QUIZ.getValue());
        InlineKeyboardButton myQuiz = new InlineKeyboardButton("Quizlarm");
        myQuiz.setCallbackData(Callback.MY_QUIZ.getValue());

        inlineKeyboardMarkup.setKeyboard(List.of(List.of(createQuiz, myQuiz)));
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;

    }

    public SendMessage getWelcomeMessage(User user, String name) {

        SendMessage sendMessage = new SendMessage(user.getId(), "Botga xush kelbsiz " + name);
        sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
        return sendMessage;

    }

    public SendMessage ButtonDone(User user) {

        SendMessage sendMessage = new SendMessage(user.getId(), " done command bosildi ");
        sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
        return sendMessage;

    }

    public SendMessage createQuiz(Update update, User user) {
        return new SendMessage(user.getId(), "Quiz nomini kiriting");

    }

    public SendMessage requestCreateQuiz(User user) {
        SendMessage sendMessage = new SendMessage(user.getId(), "Menga poll yuboring");

        KeyboardButton keyboardButton = new KeyboardButton("Poll yuborish");

        KeyboardButtonPollType quiz = KeyboardButtonPollType.builder().type("quiz").build();
        keyboardButton.setRequestPoll(quiz);

        KeyboardRow keyboardButtons = new KeyboardRow(List.of(keyboardButton));

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(List.of(keyboardButtons));
        replyKeyboardMarkup.setResizeKeyboard(true);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        return sendMessage;


    }

    public SendMessage buildShowQuizzes(List<Quiz> quizzes, User user) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(user.getId());

        StringBuilder message = new StringBuilder(" ");

        for (int i = 0; i < quizzes.size(); i++) {
            message.append(i).append(". ").append(quizzes.get(i).getName()).append(" - ").append(quizzes.get(i).getStatus()).append("\n");
        }
        sendMessage.setText(String.valueOf(message));
        return sendMessage;

    }
}