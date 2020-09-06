package edu.volkov.firstbot;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Book {
    private Document document;
    private String bookAuthorName = "нет дынных";
    private String bookAuthorURL = "нет дынных";
    private String bookName = "нет дынных";
    private String bookDocURL = "нет дынных";
    private String bookImgURL = "нет дынных";
    private String bookGenres = "нет дынных";
    private String bookDescription = "нет дынных";
    private String bookComments = "нет дынных";
    private int bookLikes;
    private int bookViews;
    private int bookCountComments;

    public Book(String bookURL){
        try{
            document = Jsoup.connect(bookURL).get();
        }catch (IOException e) {
            e.printStackTrace();
        }
        bookDocURL = bookURL;
        bookAuthorURL = document.getElementsByClass("text-decoration-none column-author-name bold max-w-140 text-overflow-ellipsis").attr("href");
        bookAuthorName = document.getElementsByClass("text-decoration-none column-author-name bold max-w-140 text-overflow-ellipsis").text();
        bookName = document.getElementsByClass("title d-block bold").text();
        bookImgURL = document.getElementsByClass("cover-book")
                .attr("style")
                .replace("background-image: url('","")
                .replace("');","");
        bookGenres = document.getElementsByClass("genres d-block").text();
        bookDescription = document.getElementById("description").text().replace("★Аннотация: ","");
        bookComments = document.getElementsByClass("comment_mv1_item").text()
                .replaceAll("Ответить","\n\n")
                .replaceAll("Нравится","")
                .replaceAll("\\d{4}-\\d{2}-\\d{2}","")
                .replaceAll("\\d{2}:\\d{2}:\\d{2}","");
        Elements elements = document.getElementsByClass("font-size-14 color-white ml-5");
        bookLikes = Integer.valueOf(elements.get(0).text());
        bookViews = Integer.valueOf(elements.get(1).text());
        bookCountComments = Integer.valueOf(elements.get(2).text());
    }

    public String getBookAuthorName() {
        return bookAuthorName;
    }

    public String getBookAuthorURL() {
        return bookAuthorURL;
    }

    public String getBookName() {
        return bookName;
    }

    public String getBookDocURL() {
        return bookDocURL;
    }

    public String getBookImgURL() {
        return bookImgURL;
    }

    public String getBookGenres() {
        return bookGenres;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public String getBookComments() {
        return bookComments;
    }

    public int getBookLikes() {
        return bookLikes;
    }

    public int getBookViews() {
        return bookViews;
    }

    public int getBookCountComments() {
        return bookCountComments;
    }

    @Override
    public String toString() {
        return "\nНазвание: " + getBookName()
                + "\nАвтор: " + getBookAuthorName()
                + "\nЖанр: " + getBookGenres()
                + "\n\nОписание:\n" + getBookDescription()
                + "\n\nКоличество лайков: " + getBookLikes()
                + "\nКоличество просмотров: " + getBookViews()
                + "\nКоличество комментариев: " + getBookCountComments()
                + "\n\nПоследние комментарии:\n" + getBookComments();
    }
}
