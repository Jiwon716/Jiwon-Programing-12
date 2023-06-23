package com.example.library.view;

import com.example.library.DatabaseHandler;
import com.example.library.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import com.example.library.MainApp;
import com.example.library.model.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LibraryOverviewController {

    @FXML
    private TableColumn<Book, String> authorColumn;
    @FXML
    private TableColumn<Book, String> genreColumn;
    @FXML
    private TableColumn<Book, String> titleColumn;
    @FXML
    private TableColumn<Book, Integer> idColumn;
    @FXML
    private TableColumn<Book, Integer> quantityColumn;

    @FXML
    private Label authorLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private Label genreLabel;
    @FXML
    private Label quantityLabel;
    @FXML
    private Label idLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Label loggedUser;


    @FXML
    private Button returnButton;
    @FXML
    private Button borrowButton;

    @FXML
    private TableView<Book> libraryTable;


    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public LibraryOverviewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the library table with the two columns.
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        authorColumn.setCellValueFactory(cellData -> cellData.getValue().authorProperty());
        genreColumn.setCellValueFactory(cellData -> cellData.getValue().genreProperty());
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idNumberProperty().asObject());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());

        // Clear book details.
        showBookDetails(null);

        // Listen for selection changes and show the friend details when changed.
        libraryTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showBookDetails(newValue));
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        DatabaseHandler db = mainApp.getDBHandler();

        User u = mainApp.getLoginUser();
        loggedUser.setText(u.getFirstName() + " " + u.getLastName());

        ObservableList<Book> bookList = FXCollections.observableArrayList();
        String st = "SELECT * FROM LIBRARY";
        ResultSet resultSet = db.execQuery(st);

        try {
            while (resultSet.next()) {
                String title = resultSet.getString("TITLE");
                String author = resultSet.getString("AUTHOR");
                String genre = resultSet.getString("GENRE");
                long quantity = resultSet.getLong("QUANTITY");
                long id = resultSet.getLong("IDNUMBER");
                bookList.add(new Book(title, author, genre, Long.valueOf(quantity).intValue(), Long.valueOf(id).intValue()));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Add observable list data to the table
        libraryTable.setItems(bookList);

    }
    
    /**
     * Fills all text fields to show details about the book.
     * If the specified book is null, all text fields are cleared.
     * 
     * @param book the book or null
     */
    private void showBookDetails(Book book) {

        if (book != null) {
            User u = mainApp.getLoginUser();
            loggedUser.setText(u.getFirstName() + " " + u.getLastName());

            // Fill the labels with info from the book object.
            titleLabel.setText(book.getTitle());
            authorLabel.setText(book.getAuthor());
            genreLabel.setText(book.getGenre());
            quantityLabel.setText(Integer.toString(book.getQuantity()));
            idLabel.setText(Integer.toString(book.getIDNumber()));
            if (u.getbookID() > 0 && u.getbookID() == book.getIDNumber()) {
                statusLabel.setText("Borrowed");
            } else {
                statusLabel.setText("Available");
            }
        } else {
            // book data is null, remove all the text.
            titleLabel.setText("");
            authorLabel.setText("");
            genreLabel.setText("");
            quantityLabel.setText("");
            idLabel.setText("");
            statusLabel.setText("");
        }
    }

    /**
     * Called when the user clicks the Borrow button.
     * Change the status of the selected book to borrow.
     */

    @FXML
    private void handleBorrowBook() {
        DatabaseHandler db = mainApp.getDBHandler();
        User u = mainApp.getLoginUser();

        String userST = "UPDATE USERTBL " + " SET BOOKID = " + Integer.parseInt(idLabel.getText()) + " WHERE USERID = " + u.getUserID();
        db.execAction(userST);

        int quantity = Integer.parseInt(quantityLabel.getText())-1;
        String bookST = "UPDATE LIBRARY " + " SET QUANTITY = " + quantity + " WHERE IDNUMBER = " + Integer.parseInt(idLabel.getText());
        db.execAction(bookST);
        quantityLabel.setText(Integer.toString(quantity));
        statusLabel.setText("Borrowed");

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("Borrowed");
        alert.setHeaderText("Success!!");
        alert.setContentText("You borrowed " + titleLabel.getText());

        alert.showAndWait();

    }

    /**
     * Called when the user clicks the Return button.
     * Change the status of the selected book to not borrowed.
     */

    @FXML
    private void handleReturnBook() {
        DatabaseHandler db = mainApp.getDBHandler();
        User u = mainApp.getLoginUser();

        String userST = "UPDATE USERTBL " + " SET BOOKID = " + -1 + " WHERE USERID = " + u.getUserID();
        db.execAction(userST);

        int quantity = Integer.parseInt(quantityLabel.getText())+1;
        String bookST = "UPDATE LIBRARY " + " SET QUANTITY = " + quantity + " WHERE IDNUMBER = " + Integer.parseInt(idLabel.getText());
        db.execAction(bookST);

        quantityLabel.setText(Integer.toString(quantity));
        statusLabel.setText("Available");

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("Returned");
        alert.setHeaderText("Success!!");
        alert.setContentText("You returned " + titleLabel.getText());

        alert.showAndWait();

    }


}