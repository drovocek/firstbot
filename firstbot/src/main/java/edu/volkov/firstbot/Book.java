package edu.volkov.firstbot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Book {
    private Document document;

    private void connectToPage(){
        try{
            document = Jsoup.connect("https://qna.habr.com/q/533035").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
