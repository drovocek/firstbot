package edu.volkov.firstbot;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//TODO добавить проверку на нули
public class DocumentConnector {
    private Document document;
    private String docURL;

    public DocumentConnector(String docURL){
        this.docURL = docURL;
        document = getDocument(docURL);
    }

    public String getDocTittle(){
        System.out.println(document.title());
        return document.title();
    }

    public Document getDocument(String url){
        if(document == null){
            synchronized (DocumentConnector.class){
                try{
                    document = Jsoup.connect(url).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return document;
    }

    public String getElementsTextByClass(String className){
        Elements element = document.getElementsByClass(className);
        System.out.println(element.text());
        return element.text();
    }

    public String getElementTextById(String id){
        Element element = document.getElementById(id);
        System.out.println(element.text());
        return element.text();
    }

    public String getImgURLByClass(String className){
        Elements elements = document.getElementsByClass(className);
        String imgURL = elements.attr("style")
                .replace("background-image: url('","")
                .replace("');","");
        return imgURL ;
    }
    //TODO переделать через стримы
    public String getElementsTextMapByClass(String keysClassName, String valuesClassName){
        String result = "";
        Elements keyElenents = document.getElementsByClass(keysClassName);
        Elements valElenents = document.getElementsByClass(valuesClassName);
        for (int i = 0; i < keyElenents.size(); i++) {
            result += keyElenents.get(i).text() + ":" + valElenents.get(i).text() +"\n";
        }
        return result;
    }





}
