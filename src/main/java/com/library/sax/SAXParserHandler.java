package com.library.sax;

import com.library.model.Book;
import com.library.model.constants.BookFields;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class SAXParserHandler extends DefaultHandler {

    private String currentTagName;

    private Book currBook = new Book();
    private boolean isBook = false;
    private final List<Book> books = new ArrayList<>();

    public void setBook(boolean book) {
        isBook = book;
    }

    public void setCurrentTagName(String currentTagName) {
        this.currentTagName = currentTagName;
    }

    public void setCurrBook(Book currBook) {
        this.currBook = currBook;
    }

    public Book getCurrBook() {
        return currBook;
    }

    public List<Book> getBooks() {
        return books;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        currentTagName = qName;
        if (currentTagName.equals(BookFields.TAG_NAME_BOOK)) {
            isBook = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);

        if (qName.equals(BookFields.TAG_NAME_BOOK)) {
            isBook = false;
            books.add(currBook);
            currBook = new Book();
        }

        currentTagName = null;
    }


    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);

        if (currentTagName != null) {

            if (isBook) {

                switch (currentTagName) {
                    case BookFields.TAG_NAME_AUTHOR -> currBook.setAuthor(new String(ch, start, length));
                    case BookFields.TAG_NAME_TITLE -> currBook.setTitle(new String(ch, start, length));
                    case BookFields.TAG_NAME_GENRE -> currBook.setGenre(new String(ch, start, length));
                    case BookFields.TAG_NAME_PRICE -> currBook.setPrice(new String(ch, start, length));
                    case BookFields.TAG_NAME_PUBLISH_DATE -> currBook.setPublishDate(new String(ch, start, length));
                    case BookFields.TAG_NAME_DESCRIPTION -> currBook.setDescription(new String(ch, start, length));

                }
            }
        }
    }
}
