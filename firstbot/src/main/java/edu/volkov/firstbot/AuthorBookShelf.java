package edu.volkov.firstbot;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AuthorBookShelf {
    private DocumentConnector document;
    private String authorName;
    private List<Book> authorBooks;
    private String authorBooksAsString;

    public AuthorBookShelf(String authorName) {
        this.authorName = authorName;
        document = new DocumentConnector("https://surgebook.com/" + authorName + "/books/all");
    }

//    public List getAuthorBooks(){
//        if(authorBooks == null){
//            synchronized (document){
//                String books = document.getElementsTextMapByClass(String keysClassName, String valuesClassName)
//            }
//        }
//        return authorBooks;
//    }

//    public List getAuthorBooks() {
//        if (authorBooks == null) {
//
//            String books = document.getElementsTextMapByClass(
//                    "book_view_mviw", String valuesClassName)
//
//        }
//        return authorBooks;
//    }


}
