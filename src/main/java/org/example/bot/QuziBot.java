package org.example.bot;

import org.example.handlers.UserHandler;
import org.example.user.User;
import org.example.user.UserService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;


public class QuziBot extends TelegramLongPollingBot {

    private final UserService userService = UserService.getInstance();
    private final UserHandler userHandler = new UserHandler(this);

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage()) {
            User user = userService.getOrElseCreate(update.getMessage().getChatId());

            handle(update, user);

        }
        if (update.hasCallbackQuery()) {

            User user = userService.getOrElseCreate(update.getCallbackQuery().getFrom().getId());

            handle(update, user);

        }


    }

    private void handle(Update update, User user) {
        switch (user.getRole()) {
            case USER -> userHandler.handle(update, user);
        }
    }

    @Override
    public String getBotUsername() {
        return "https://t.me/zaxaG27_bot";
    }

    @Override
    public String getBotToken() {
        return "6064452007:AAHHrlezRH1yMvssBntZp4QewlwNSylVlbE";
    }
}
