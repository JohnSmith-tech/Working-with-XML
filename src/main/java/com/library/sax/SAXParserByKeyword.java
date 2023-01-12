package com.library.sax;

import com.library.model.Book;
import com.library.model.constants.BookFields;

public class SAXParserByKeyword extends SAXParserHandler {


    private final String keyword;

    public SAXParserByKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (qName.equals(BookFields.TAG_NAME_BOOK)) {
            setBook(false);
            if (checkFieldNull() && searchDataByKeyword())
                getBooks().add(getCurrBook());
            setCurrBook(new Book());
        }

        setCurrentTagName(null);
    }

    public boolean checkFieldNull() {
        return getCurrBook().getAuthor() != null ||
                getCurrBook().getTitle() != null ||
                getCurrBook().getGenre() != null ||
                getCurrBook().getPrice() != null ||
                getCurrBook().getPublishDate() != null ||
                getCurrBook().getDescription() != null;
    }

    public boolean searchDataByKeyword() {
        return getCurrBook().getAuthor().equals(keyword) ||
                getCurrBook().getTitle().equals(keyword) ||
                getCurrBook().getGenre().equals(keyword) ||
                getCurrBook().getPrice().equals(keyword) ||
                getCurrBook().getPublishDate().equals(keyword) ||
                getCurrBook().getDescription().equals(keyword);
    }

}

