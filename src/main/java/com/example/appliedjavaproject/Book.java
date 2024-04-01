package com.example.appliedjavaproject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents a Book object
 */
@Entity(name = "titles")
public class Book {
    @Id
    private String isbn;
    private String title;
    private int edNumber;
    private String copyright;
    @ManyToMany
    @JoinTable(
            name = "author_isbn",
            joinColumns = @JoinColumn(name = "isbn"),
            inverseJoinColumns = @JoinColumn(name = "id")
    )
    private List<Author> authors;

    /**
     * This method returns the ISBN
     * @return isbn
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * This method sets the ISBN
     * @param isbn
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * This method returns the title
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method sets the title
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * This method returns the edition number
     * @return edNumber
     */
    public int getEdNumber() {
        return edNumber;
    }

    /**
     * This method sets the edition number
     * @param edNumber
     */
    public void setEdNumber(int edNumber) {
        this.edNumber = edNumber;
    }

    /**
     * This method returns the copyright
     * @return copyright
     */
    public String getCopyright() {
        return copyright;
    }

    /**
     * This method sets the copyright
     * @param copyright
     */
    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    /**
     * This method returns the list of authors
     * @return authors
     */
    public List<Author> getAuthors() {
        return authors;
    }

    /**
     * This method sets the list of authors
     * @param authors
     */
    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    static void createBooksAuthorList(List<Book> books, List<Author> authors, List<List> authorISBN){
        for (Book book : books) {
            for (List authorISBNList : authorISBN) {
                if (Objects.equals(book.getIsbn(), authorISBNList.get(0))) {
                    for (Author author : authors) {
                        if (author.getAuthorID() == Integer.parseInt((String) authorISBNList.get(1))) {
                            book.getAuthors().add(author);
                        }
                    }
                }
            }
        }
    }
}
