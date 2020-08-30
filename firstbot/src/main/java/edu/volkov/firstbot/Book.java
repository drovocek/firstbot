package edu.volkov.firstbot;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Book {
    private DocumentConnector document;
    private String bookName;

    private static final String BUFFER_IMG = "C:\\Users\\sky\\Desktop\\PROG\\MyJProjects\\loadresourses";

    public Book(){
        document = new DocumentConnector("https://surgebook.com/pepelna/book/chernilnaya");
    }

    public String getTittle(){
        return document.getDocTittle();
    }

    public String getLikes(){
        return document.getElementTextById("likes");
    }

    public String getDescription(){
        return document.getElementTextById("description");
    }

    public String getGenres(){
        return document.getElementsTextByClass("genres d-block");
    }

    public String getComments(){
        String comment = document.getElementsTextByClass("comment_mv1_item");
        return cleanComment(comment);
    }

    private String cleanComment(String comment){
        return comment.replaceAll("Ответить","\n\n")
                .replaceAll("Нравится","")
                .replaceAll("\\d{4}-\\d{2}-\\d{2}","")
                .replaceAll("\\d{2}:\\d{2}:\\d{2}","");
    }

    private String getImgURL(){
        return document.getImgURLByClass("cover-book") ;
    }

    public File getImgFile(){
        File fileImg = new File(BUFFER_IMG);
        try(InputStream in = new URL(getImgURL()).openStream()) {
            Path bufferPath = Paths.get(BUFFER_IMG);
            Files.copy(in, bufferPath);
        }  catch (IOException e) {
            e.printStackTrace();
        }
        return fileImg;
    }

    public void cleanImgBuffer(){
        try{
            Files.delete(Paths.get(BUFFER_IMG));
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public String getAuthorName(){
        return document.getElementsTextByClass("" +
                "text-decoration-none column-author-name" +
                "bold max-w-140 text-overflow-ellipsis");
    }

    public String getBookTextInfo(){
        String info = getTittle()
                + "\n:smile "
                + "\nАвтор: " + getAuthorName()
                + "\nЖанр: " + getGenres()
                + "\n\nОписание\n:" + getDescription()
                + "\n\nКоличество лайков: " + getLikes()
                + "\n\nПоследние комментарии: " + getComments();
        return info;
    }

}
