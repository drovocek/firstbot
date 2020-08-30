package edu.volkov.firstbot;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;

public class Author {
    private DocumentConnector document;
    private AuthorBookShelf authorBookShelf;
    private Document bookDoc;
    private String authorName;
    private int valuesLikeBooks;
    private int valuesViewsBooks;
    private int valuesCommentsBooks;

    public Author(String name){
        authorName = name;
        document = new DocumentConnector("https://surgebook.com/" + authorName);
        authorBookShelf = new AuthorBookShelf(authorName);
    }

    public String getName(){
        return document.getElementsTextByClass("author_name bold");
    }

    public String getBio(){
        return document.getElementsTextByClass("author_bio");
    }

    private String getImgURL(){
        return document.getImgURLByClass("user_avatar") ;
    }

    public String getPersonInfo(){
        String info ="";
        info += "Имя: " + getName();
        info += "Статус: " + getName();
        info += document.getElementsTextMapByClass("info_stats_name","info_stats_name");
       // info += getAuthorBooks();
        return info;
    }

//    private String getAuthorBooks() {
//
//
//    }


}
