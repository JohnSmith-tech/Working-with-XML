package com.library;

import com.library.model.Book;
import com.library.dom.XmlDomLibrary;
import com.library.sax.XmlSaxLibrary;

import java.net.URISyntaxException;
import java.util.List;

public class Main {


    public static void main(String[] args) {
        usingDOM();
        usingSAX();
    }

    public static void usingDOM() {
        XmlDomLibrary file = new XmlDomLibrary("books.xml");
        List<Book> books = file.getDataFromXmlFile();
        System.out.println(books);
        books.add(new Book("John", "The run", "Math", "49.25", "19.12.1997", "" +
                "Cars, guns and mafia"));
        file.addElementToXmlFile(books);
        System.out.println(file.getDataByKeyword("Corets, Eva"));
    }

    public static void usingSAX() {
        XmlSaxLibrary file;
        try {
            file = new XmlSaxLibrary("books.xml");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        file.getDataFromXmlFile();
        file.getDataByKeyword("Corets, Eva");

    }
}