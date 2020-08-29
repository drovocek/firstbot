package edu.volkov.firstbot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class Bot extends TelegramLongPollingBot {
    Book book = new Book();
    private long chat_id;

    //когда Бот получает сообщение выполняется эта функция
    public void onUpdateReceived(Update update) {

        update.getUpdateId();
        System.out.println("In first");
        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());


        System.out.println(update.getMessage().getText());
        if (update.getMessage().getText().equals("1234")) {
            sendMessage.setText("In first");
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public void onUpdatesReceived(List<Update> updates) {
        System.out.println("2");
        updates.get(0).getUpdateId();
        System.out.println("In second");

        chat_id = updates.get(0).getMessage().getChatId();
        String userMessage = updates.get(0).getMessage().getText();
        System.out.println(userMessage);

        SendMessage sendMessage = new SendMessage().setChatId(chat_id);


        String botMessage = getBotMessage(userMessage);
        sendMessage.setText(botMessage);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    private String getBotMessage(String userMessage) {
        String usMessNormalized = userMessage.toLowerCase().trim();
        if (usMessNormalized.startsWith("hi")
                || usMessNormalized.startsWith("привет")) return "Здарова мужик!";
        return "?";
    }

    //получение имени бота
    public String getBotUsername() {
        return "@FirstVolkovBot";
    }

    //получение токена бота
    public String getBotToken() {
        return "1382101652:AAHCInYcS9vs0M5p7blEjcln5Cz32AgoYbA";
    }
}
