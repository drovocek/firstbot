package edu.volkov.firstbot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Author {
    private Document document;
    private AuthorBookShelf authorBookShelf;
    private String authorLogin = "нет дынных";
    private String authorName = "нет дынных";
    private String authorBio = "нет дынных";
    private String authorDocURL = "нет дынных";
    private String authorPhotoURL = "нет дынных";
    private int valuesBooks;
    private int valuesBlog;
    private int valuesPoems;
    private int valuesSubscriber;
    private int valuesSubscription;

    public Author(String authorLogin) {
        this.authorLogin = authorLogin;
        authorDocURL = "https://surgebook.com/" + authorLogin;
        authorBookShelf = new AuthorBookShelf(authorDocURL);
        try{
            document = Jsoup.connect(authorDocURL).get();
        }catch (IOException e) {
            e.printStackTrace();
        }
        authorName = document.getElementsByClass("author-name bold").text();
        authorBio = document.getElementsByClass("author-bio").text();
        authorPhotoURL = document.getElementsByClass("user-avatar")
                .attr("style")
                .replace("background-image: url('","")
                .replace("');","");
        Elements values = document.getElementsByClass("info-stats-num");
        valuesBooks = Integer.valueOf(values.get(0).text());
        valuesBlog = Integer.valueOf(values.get(1).text());
        valuesPoems = Integer.valueOf(values.get(2).text());
        valuesSubscriber = Integer.valueOf(values.get(3).text());
        valuesSubscription = Integer.valueOf(values.get(4).text());
    }

    public String getAuthorLogin() {
        return authorLogin;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getAuthorBio() {
        return authorBio;
    }

    public String getAuthorDocURL() {
        return authorDocURL;
    }

    public String getAuthorPhotoURL() {
        return authorPhotoURL;
    }

    @Override
    public String toString() {
        return "Имя: " + authorName + "\n"
                + "Биография: " + authorBio + "\n\n"
                +  "Количество: " + "\n"
                +  "  - книг: " + valuesBooks +"\n"
                +  "  - блог: " + valuesBlog +"\n"
                +  "  - стихов: " + valuesPoems +"\n"
                +  "  - подписчиков: " + valuesSubscriber +"\n"
                +  "  - подписок: " + valuesSubscription +"\n\n"
                + "Книги:\n  - " + authorBookShelf.toString() + "\n";
    }
}
