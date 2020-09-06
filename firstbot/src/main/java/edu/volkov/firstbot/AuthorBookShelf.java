package edu.volkov.firstbot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AuthorBookShelf {
    private Document document;
    private String authorDocURL;
    private List<Book> authorBooks;
    private int valuesLikesBooks;
    private int valuesCommentsBooks;
    private int valuesViewsBooks;

    public AuthorBookShelf(String authorDocURL) {
        this.authorDocURL =authorDocURL;
        this.authorBooks = new ArrayList<>();
        try{
            document = Jsoup.connect(authorDocURL + "/books/all").get();
        }catch (IOException e) {
            e.printStackTrace();
        }
        fillBooksShelf();
        fillBooksStatistic();
    }

    private void fillBooksShelf() {
        Elements booksURL = document.getElementsByClass("book_view_mv1v2_cover");
        for(Element bookEl : booksURL){
            authorBooks.add(new Book(bookEl.attr("href")));
        }
    }

    private void fillBooksStatistic() {
        for(Book book : authorBooks){
            valuesLikesBooks += book.getBookLikes();
            valuesCommentsBooks += book.getBookCountComments();
            valuesViewsBooks += book.getBookViews();
        }
    }

    @Override
    public String toString() {
        return authorBooks.stream()
                .map(Book::getBookName)
                .collect(Collectors.joining("\n  - "));
    }
}
