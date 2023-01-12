package com.library.sax;


import com.library.dom.XmlDomLibrary;
import com.library.model.Book;
import com.library.parsers.XmlParser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

public class XmlSaxLibrary implements XmlParser {

    private final SAXParser parser;

    private SAXParserHandler handler;
    private final File file;

    public XmlSaxLibrary(String filename) throws URISyntaxException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            parser = factory.newSAXParser();
        } catch (ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        }

        this.file = new File(Objects.requireNonNull(XmlDomLibrary.class.getResource("/" + filename)).toURI().getPath());

    }


    @Override
    public List<Book> getDataByKeyword(String keyword) {
        handler = new SAXParserByKeyword(keyword);
        try {
            parser.parse(file, this.handler);
        } catch (SAXException | IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(handler.getBooks());
        return handler.getBooks();
    }

    @Override
    public List<Book> getDataFromXmlFile() {
        handler = new SAXParserHandler();
        try {
            parser.parse(file, this.handler);
        } catch (SAXException | IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(handler.getBooks());
        return handler.getBooks();
    }

}

