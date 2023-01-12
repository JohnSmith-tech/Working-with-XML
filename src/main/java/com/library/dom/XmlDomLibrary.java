package com.library.dom;

import com.library.model.Book;
import com.library.model.constants.BookFields;
import com.library.parsers.XmlParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author johnsmith
 *
 */
public class XmlDomLibrary implements XmlParser {

    private final Document document;


    public XmlDomLibrary(String filename) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        try {
            document = builder.parse(new File(Objects.requireNonNull(XmlDomLibrary.class.getResource("/" + filename)).toURI().getPath()));
        } catch (SAXException | IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }


    public void addElementToXmlFile(List<Book> books) {

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        try {
            docBuilder = docFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }

        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement(BookFields.TAG_NAME_CATALOG);
        doc.appendChild(rootElement);

        for (Book book : books) {
            Element root = doc.createElement(BookFields.TAG_NAME_BOOK);


            Element author = doc.createElement(BookFields.TAG_NAME_AUTHOR);
            Element title = doc.createElement(BookFields.TAG_NAME_TITLE);
            Element genre = doc.createElement(BookFields.TAG_NAME_GENRE);
            Element price = doc.createElement(BookFields.TAG_NAME_PRICE);
            Element publishDate = doc.createElement(BookFields.TAG_NAME_PUBLISH_DATE);
            Element description = doc.createElement(BookFields.TAG_NAME_DESCRIPTION);

            author.setTextContent(book.getAuthor());
            title.setTextContent(book.getTitle());
            genre.setTextContent(book.getGenre());
            price.setTextContent(book.getPrice());
            publishDate.setTextContent(book.getPublishDate());
            description.setTextContent(book.getDescription());

            root.appendChild(author);
            root.appendChild(title);
            root.appendChild(genre);
            root.appendChild(price);
            root.appendChild(publishDate);
            root.appendChild(description);


            rootElement.appendChild(root);

        }

        Transformer t;
        try {
            t = TransformerFactory.newInstance().newTransformer();
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        }
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        try {
            t.transform(new DOMSource(doc), new StreamResult(new FileOutputStream("bookUpdated.xml")));
        } catch (TransformerException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Book> getDataByKeyword(String keyword) {


        Node rootNode = document.getFirstChild();

        NodeList rootChild = rootNode.getChildNodes();
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < rootChild.getLength(); i++) {

            if (rootChild.item(i).getNodeType() != Node.ELEMENT_NODE ||
                    !rootChild.item(i).getNodeName().equals(BookFields.TAG_NAME_BOOK)) {
                continue;
            }

            NodeList elementChild = rootChild.item(i).getChildNodes();
            Book book = new Book();
            boolean isValid = false;
            for (int j = 0; j < elementChild.getLength(); j++) {

                if (rootChild.item(j).getNodeType() != Node.ELEMENT_NODE) {
                    continue;
                }

                if (elementChild.item(j).getTextContent().contains(keyword) || isValid) {
                    book = setAttributeBook(elementChild, j, book);
                    isValid = true;
                }

            }

            if (checkFieldNull(book)) {
                books.add(book);
            }


        }

        return books;
    }

    public boolean checkFieldNull(Book book) {
        return book.getAuthor() != null ||
                book.getTitle() != null ||
                book.getGenre() != null ||
                book.getPrice() != null ||
                book.getPublishDate() != null ||
                book.getDescription() != null;
    }

    public Book setAttributeBook(NodeList elementChild, int iter, Book book) {
        switch (elementChild.item(iter).getNodeName()) {
            case BookFields.TAG_NAME_AUTHOR -> book.setAuthor(elementChild.item(iter).getTextContent());
            case BookFields.TAG_NAME_TITLE -> book.setTitle(elementChild.item(iter).getTextContent());
            case BookFields.TAG_NAME_GENRE -> book.setGenre(elementChild.item(iter).getTextContent());
            case BookFields.TAG_NAME_PRICE -> book.setPrice(elementChild.item(iter).getTextContent());
            case BookFields.TAG_NAME_PUBLISH_DATE -> book.setPublishDate(elementChild.item(iter).getTextContent());
            case BookFields.TAG_NAME_DESCRIPTION -> book.setDescription(elementChild.item(iter).getTextContent());
        }
        return book;
    }

    @Override
    public List<Book> getDataFromXmlFile() {

        Node rootNode = document.getFirstChild();

        NodeList rootChild = rootNode.getChildNodes();
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < rootChild.getLength(); i++) {

            if (rootChild.item(i).getNodeType() != Node.ELEMENT_NODE ||
                    !rootChild.item(i).getNodeName().equals(BookFields.TAG_NAME_BOOK)) {
                continue;
            }

            NodeList elementChild = rootChild.item(i).getChildNodes();
            Book book = new Book();
            for (int j = 0; j < elementChild.getLength(); j++) {

                if (rootChild.item(j).getNodeType() != Node.ELEMENT_NODE) {
                    continue;
                }

                book = setAttributeBook(elementChild, j, book);
            }

            books.add(book);
        }
        return books;
    }


}
