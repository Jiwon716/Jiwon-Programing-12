package com.example.library.view;

import com.example.library.DatabaseHandler;
import com.example.library.MainApp;
import com.example.library.model.Book;
import com.example.library.model.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class LoginViewController {

    @FXML
    private TextField idField;
    @FXML
    private TextField pinField;

    @FXML
    private Button signUPButton;
    @FXML
    private Button loginButton;


    // Reference to the main application.
    private MainApp mainApp;

    private Stage dialogStage;
    private boolean okClicked = false;
    private User loginUser;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public LoginViewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    public User getLoginUser() {return loginUser; }

    /**
     * Called when the user clicks the SignUP button.
     * Pop-up window and get user information for signup.
     */
    @FXML
    private void handleSignUP() {
        Dialog<User> dialog = new Dialog<>();
        dialog.setTitle("Sign-UP Dialog");
        dialog.setHeaderText("Please provide user information.");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        TextField firstNameField = new TextField("First Name");
        TextField lastNameField = new TextField("Last Name");
        TextField userIDField = new TextField("User ID");
        TextField userPINField = new TextField("User PIN");

        dialogPane.setContent(new VBox(8, firstNameField, lastNameField, userIDField, userPINField));
        Platform.runLater(firstNameField::requestFocus);
        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                return new User(firstNameField.getText(),
                        lastNameField.getText(),
                        Integer.parseInt(userPINField.getText()),
                        Integer.parseInt(userIDField.getText()));
            }
            return null;
        });
        Optional<User> userInput = dialog.showAndWait();

        userInput.ifPresent((User u) -> {
            DatabaseHandler db = mainApp.getDBHandler();

            String qu = "INSERT INTO USERTBL " + " VALUES (" +
                    "'" + u.getFirstName() + "'," +
                    "'" + u.getLastName() + "'," +
                    u.getPIN() + "," + null + "," +
                    u.getUserID() + ")";
            System.out.println(qu);
            db.execAction(qu);

            System.out.println(
                    u.getFirstName() + " " + u.getLastName() + " " + u.getUserID());
        });

    }

    /**
     * Called when the user clicks the Login button.
     * Check given id and pin information.
     */
    @FXML
    private void handleLogIN() {
        DatabaseHandler db = mainApp.getDBHandler();

        if (isInputValid()) {
            System.out.println("user input: " + idField.getText() + ", " + pinField.getText());
            String st = "SELECT * FROM USERTBL WHERE USERID = " + Integer.parseInt(idField.getText());
            ResultSet resultSet = db.execQuery(st);

            try {
                if (resultSet.next()) {
                    String firstName = resultSet.getString("FIRSTNAME");
                    String lastName = resultSet.getString("LASTNAME");
                    long pin = resultSet.getLong("PIN");
                    if (pin == Integer.parseInt(pinField.getText())) {
                        System.out.println("Successful LogIN: first Name" + firstName + ", last Name = " + lastName);
                        loginUser = new User(firstName, lastName, Integer.parseInt(pinField.getText()), Long.valueOf(resultSet.getLong("USERID")).intValue());
                        if (resultSet.getLong("BOOKID") > 0) {
                            loginUser.setbookID(Long.valueOf(resultSet.getLong("BOOKID")).intValue());
                        }
                        okClicked = true;
                        dialogStage.close();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.initOwner(mainApp.getPrimaryStage());
                        alert.setTitle("No Proper Login");
                        alert.setHeaderText("Incorrect Login");
                        alert.setContentText("Please provide correct login information.");

                        alert.showAndWait();
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            okClicked = true;
            dialogStage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Proper Login");
            alert.setHeaderText("Incorrect Login");
            alert.setContentText("Please provide correct login information.");

            alert.showAndWait();
        }
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (idField.getText() == null || idField.getText().length() == 0) {
            errorMessage += "No valid user ID!\n";
        }
        if (pinField.getText() == null || pinField.getText().length() == 0) {
            errorMessage += "No valid PIN!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

}