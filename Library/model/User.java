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
public class User {

    private final StringProperty firstName;
    private final StringProperty lastName;
    private final IntegerProperty pin;
    private final IntegerProperty userID;
    private final int bookID;

    /**
     * Default constructor.
     */
    public User() {
        this(null, null, null, null);
    }

    /**
     * Constructor with some initial data.
     *
     * @param firstName, lastName, pin, userID
     */
    public User(String firstName, String lastName, Integer pin, Integer userID) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.pin = new SimpleIntegerProperty(pin);
        this.userID = new SimpleIntegerProperty(userID);
        this.bookID = -1;
    }

    public String getFirstName() {
        return firstName.get();
    }
    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }
    public StringProperty firstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }
    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }
    public StringProperty lastNameProperty() {
        return lastName;
    }

    public int getPIN() { return pin.get();}
    public void setPIN(int pin) {
        this.pin.set(pin);
    }
    public IntegerProperty pinProperty() {
        return pin;
    }

    public int getUserID() {
        return userID.get();
    }
    public void setUserID(int userID) {
        this.userID.set(userID);
    }
    public IntegerProperty userIDProperty() {
        return userID;
    }

    public int getbookID() {
        return bookID;
    }
    public void setbookID(int bookID) {
        bookID = bookID;
    }
    public int bookIDProperty() {
        return bookID;
    }

}