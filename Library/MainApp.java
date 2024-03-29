package com.example.library;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

//import com.example.library.view.SelectFriendBookController;
//import com.example.library.view.StoreFriendBookController;
import com.example.library.model.User;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.example.library.model.Book;
//import com.example.library.view.FriendEditDialogController;
import com.example.library.view.LibraryOverviewController;
import com.example.library.view.LoginViewController;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    public static DatabaseHandler handler;
    public static String libraryTable = "LIBRARY";
    public static String userTable = "userTBL";
    public static User loginUser;


    /**
     * The data as an observable list of Book.
     */
    private ObservableList<Book> bookData = FXCollections.observableArrayList();

    /**
     * Constructor
     */
    public MainApp() {
        // Add some sample data


        addBook(new Book("Sherlock Homes", "Conan Doyle", "Mystery", 12383, 5));
        addBook(new Book("Harry Potter", "J.K. Rowling", "Fantasy", 72621, 10));
        addBook(new Book("Percy Jackson", "Rick Riordan", "Adventure", 38475, 3));
        //bookData.add(new Book("Sherlock Homes", "Conan Doyle", "Mystery", 12383, 5));
        //bookData.add(new Book("Harry Potter", "J.K. Rowling", "Fantasy", 72621, 10));
        //bookData.add(new Book("Percy Jackson", "Rick Riordan", "Adventure", 38475, 3));

        addUser(new User("Lady", "Gaga", 1234, 28729));
        addUser(new User("Ariana", "Grande", 1234, 63372));
        addUser(new User("Swift", "Taylor", 1234, 28932));
    }

    /**
     * Add book information to the library table
     */
    public static void addBook(Book b){
        String qu = "INSERT INTO " + libraryTable + " VALUES (" +
                "'" + b.getTitle() + "'," +
                "'" + b.getAuthor() + "'," +
                "'" + b.getGenre() + "'," +
                b.getIDNumber() + "," +
                b.getQuantity() + ")";
        System.out.println(qu);
        handler.execAction(qu);
    }

    /**
     * Add user information to the library table
     */
    public static void addUser(User u){
        String qu = "INSERT INTO " + userTable + " VALUES (" +
                "'" + u.getFirstName() + "'," +
                "'" + u.getLastName() + "'," +
                u.getPIN() + "," + null + "," +
                u.getUserID() + ")";
        System.out.println(qu);
        handler.execAction(qu);
    }

    /**
     * Returns the data as an observable list of friends.
     * @return
     */
    public ObservableList<Book> getBookData() {
        return bookData;
    }

    public DatabaseHandler getDBHandler() { return handler; }

    public User getLoginUser() { return loginUser; }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Online Library");

        initRootLayout();

        if (showLoginView()) {
            showLibraryOverview();
        }
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the friend overview inside the root layout.
     */
    public boolean showLoginView() {
        try {
            // Load friend overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/LoginView.fxml"));
            AnchorPane loginView = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Login page");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(loginView);
            dialogStage.setScene(scene);


            // Set friend overview into the center of root layout.
            //rootLayout.setCenter(loginView);

            // Give the controller access to the main app.
            LoginViewController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainApp(this);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            loginUser = controller.getLoginUser();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Shows the friend overview inside the root layout.
     */
    public void showLibraryOverview() {
        try {
            // Load friend overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/LibraryOverview.fxml"));
            AnchorPane libraryOverview = (AnchorPane) loader.load();

            // Set friend overview into the center of root layout.
            rootLayout.setCenter(libraryOverview);

            // Give the controller access to the main app.
            LibraryOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    


    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        handler = DatabaseHandler.getHandler();
        launch(args);
    }
}