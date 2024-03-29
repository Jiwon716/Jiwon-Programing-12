package com.example.library.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a Library.
 *
 * @author Marco Jakob
 */
public class Book {

    private final StringProperty title;
    private final StringProperty author;
    private final StringProperty genre;
    private final IntegerProperty quantity;
    private final IntegerProperty idnumber;

    /**
     * Default constructor.
     */
    public Book() {
        this(null, null, null, null, null);
    }

    /**
     * Constructor with some initial data.
     * 
     * @param title, author, genre and idnumber
     */
    public Book(String title, String author, String genre, Integer idnumber, Integer quantity) {
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.genre = new SimpleStringProperty(genre);
        this.idnumber = new SimpleIntegerProperty(idnumber);
        this.quantity = new SimpleIntegerProperty(quantity);
    }

    public String getTitle() {
        return title.get();
    }
    public void setTitle(String title) {
        this.title.set(title);
    }
    public StringProperty titleProperty() {
        return title;
    }

    public String getAuthor() {
        return author.get();
    }
    public void setAuthor(String author) {
        this.author.set(author);
    }
    public StringProperty authorProperty() {
        return author;
    }

    public String getGenre() {
        return genre.get();
    }
    public void setGenre(String genre) {
        this.genre.set(genre);
    }
    public StringProperty genreProperty() {
        return genre;
    }

    public int getIDNumber() {
        return idnumber.get();
    }
    public void setIdnumber(int idnumber) {
        this.idnumber.set(idnumber);
    }
    public IntegerProperty idNumberProperty() {
        return idnumber;
    }

    public int getQuantity() {
        return quantity.get();
    }
    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }
    public IntegerProperty quantityProperty() {
        return quantity;
    }

}