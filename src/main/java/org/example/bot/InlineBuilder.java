package org.example.bot;




import org.apache.commons.lang3.tuple.Pair;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;


public class InlineBuilder {

    public static InlineKeyboardMarkup build(List<List<Pair<String, String>>> buttons) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inline = new ArrayList<>();

        for (List<Pair<String, String>> rows : buttons) {

            List<InlineKeyboardButton> row = new ArrayList<>();

            for (Pair<String, String> button : rows) {
                InlineKeyboardButton keyboardButton = new InlineKeyboardButton();
                keyboardButton.setText(button.getLeft());
                keyboardButton.setCallbackData(button.getRight());
                row.add(keyboardButton);
            }

            inline.add(row);
        }

        inlineKeyboardMarkup.setKeyboard(inline);
        return inlineKeyboardMarkup;
    }


}
