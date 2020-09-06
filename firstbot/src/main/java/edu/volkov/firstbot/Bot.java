package edu.volkov.firstbot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    private SendMessage sendTextMessage = new SendMessage();
    private SendPhoto sendPhotoMessage  = new SendPhoto();
    private ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    private final static String USERNAME = "@FirstVolkovBot";
    private final static String TOKEN = "1382101652:AAHCInYcS9vs0M5p7blEjcln5Cz32AgoYbA";
    private static final String BUFFER_IMG = "C:\\Users\\sky\\Desktop\\PROG\\MyJProjects\\newrep";

    private long chat_id;

    //когда Бот получает сообщение выполняется эта функция
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()) {
            update.getUpdateId();

            chat_id = update.getMessage().getChatId();
            String userMessage = update.getMessage().getText();
            responceSwitcher(userMessage);
            System.out.println(userMessage);

            cleanImgBuffer();
        }
    }


//    public void onUpdatesReceived(List<Update> updates) {
//        if(!updates.get(0).hasMessage()) {
//            System.out.println(updates.get(0).getCallbackQuery());
//        }
//
//        updates.get(0).getUpdateId();
//        System.out.println("In second");
//
//        chat_id = updates.get(0).getMessage().getChatId();
//        String userMessage = updates.get(0).getMessage().getText();
//        responceSwitcher(userMessage);
//        System.out.println(userMessage);
//
//        cleanImgBuffer();
//    }

    public synchronized void setButtons(SendMessage sendMessage) {
        // Создаем клавиуатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Добавляем кнопки в первую строчку клавиатуры
        keyboardFirstRow.add(new KeyboardButton("Привет"));

        // Вторая строчка клавиатуры
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        // Добавляем кнопки во вторую строчку клавиатуры
        keyboardSecondRow.add(new KeyboardButton("Помощь"));

        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
    }
    private void setInline() {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> buttons1 = new ArrayList<>();
        buttons1.add(new InlineKeyboardButton().setText("Кнопка").setCallbackData("17"));
        buttons.add(buttons1);

        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();
        markupKeyboard.setKeyboard(buttons);
    }


    private void responceSwitcher(String userMessage) {
        String usMessNormalized = userMessage.toLowerCase().trim();
        if (usMessNormalized.startsWith("hi")
                || usMessNormalized.startsWith("привет")) getHello();
        else if (usMessNormalized.startsWith("book")
                || usMessNormalized.startsWith("книга")) getBookInfo();
        else if (usMessNormalized.startsWith("/person")
                || usMessNormalized.startsWith("/автор"))
            getAuthorInfo(usMessNormalized.split(" ")[1]);
    }

    private void getHello(){
        sendTextMessage.setChatId(chat_id);
        sendTextMessage.setText("Здарова!");
        setButtons(sendTextMessage);
        try {
            //execute(sendPhotoMessage);
            execute(sendTextMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void getBookInfo(){
        Book book = new Book("https://surgebook.com/pepelna/book/chernilnaya");
        setImgMessage(book.getBookImgURL());
        setTextMessage(book.toString());
        try {
            execute(sendPhotoMessage);
            execute(sendTextMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void getAuthorInfo(String authorLogin){
        Author author = new Author(authorLogin);
        setImgMessage(author.getAuthorPhotoURL());
        setTextMessage(author.toString());
        try {
            execute(sendPhotoMessage);
            execute(sendTextMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void setImgMessage(String url){
        sendPhotoMessage.setChatId(chat_id);
        sendPhotoMessage.setPhoto(getImgFile(url));
    }

    private File getImgFile(String url){
        File fileImg = new File(BUFFER_IMG);
        try(InputStream in = new URL(url).openStream()) {
            Path bufferPath = Paths.get(BUFFER_IMG);
            Files.copy(in, bufferPath);
        }  catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(fileImg.exists());
        return fileImg;
    }

    private void cleanImgBuffer(){
        try{
            Files.delete(Paths.get(BUFFER_IMG));
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private void setTextMessage(String text){
        sendTextMessage.setChatId(chat_id);
        sendTextMessage.setText(text);
    }

    //получение имени бота
    public String getBotUsername() {
        return USERNAME;
    }

    //получение токена бота
    public String getBotToken() {
        return TOKEN;
    }
}
