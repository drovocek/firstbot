package edu.volkov.firstbot;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.w3c.dom.ls.LSOutput;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        String botMessage = getBotMessage(userMessage);

        System.out.println(userMessage);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chat_id);
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
                || usMessNormalized.startsWith("привет")) return getHello();
        else if (usMessNormalized.startsWith("book")
                || usMessNormalized.startsWith("книга")) return getBookInfo();
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

    private String getHello(){
        return "Здарова!";
    }

    private String getBookInfo(){
        sendBookImg();
        return book.getBookTextInfo();
    }

    private void sendBookImg(){
        SendPhoto sendPhotoRequest = new SendPhoto();
        try{
            sendPhotoRequest.setChatId(chat_id);
            sendPhotoRequest.setPhoto(book.getImgFile());
            execute(sendPhotoRequest);
            book.cleanImgBuffer();
        }  catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
