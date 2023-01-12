package com.library.parsers;

import com.library.model.Book;

import java.util.List;

/**
 * @author johnsmith
 * <p>
 * Interface for xml parsers
 */
public interface XmlParser {

    /**
     * @param keyword string
     * @return list of book
     */
    List<Book> getDataByKeyword(String keyword);

    /**
     * @return list of book
     */
    List<Book> getDataFromXmlFile();


}
