package view.starter;

import controller.UserController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.util.Callback;
import model.enums.Slogan;
import model.enums.Validations;
import model.user.Password;
import model.user.User;
import view.LaunchMenu;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RegisterMenuController implements Initializable {
    @FXML
    private TextField userField;
    @FXML
    private Text usernameError;
    @FXML
    private Text emailError;
    @FXML
    private TextField emailField;
    @FXML
    private TextField nickNameField;
    @FXML
    private TextField invisiblePassword;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Rectangle maskField;
    private boolean isPasswordShown;
    @FXML
    private Text passwordError;
    @FXML
    private CheckBox sloganCheck;
    @FXML
    private TextField sloganField;
    @FXML
    private Button randomSlogan;
    @FXML
    private ComboBox <String> sloganChoice;

    @FXML
    private Text or;

    @FXML
    private Text nickNameError;

    @FXML Text sloganError;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        sloganChoice.maxWidth(5);
        sloganChoice.maxHeight(5);
        ObservableList <String> item = sloganChoice.getItems();
        item.add("Dale que se puede");
        item.add("El mundo es mio!");
        item.add("Duty, Honor, Empire");
        item.add("Can't stop, Won't stop");
        sloganChoice.setStyle("-fx-fill: gold");


        sloganChoice.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override public ListCell<String> call(ListView<String> p) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item);
                            getStyleClass().add("my-list-cell");
                            setStyle("-fx-font-size: 12; -fx-font-family: Bahnschrift; -fx-font-style: oblique; " +
                                    "-fx-text-fill: gold; -fx-background-color: #787350; -fx-font-weight: bold;");
                        }
                    }
                };
            }
        });

        maskField.setFill(new ImagePattern(new Image(RegisterAndLoginMenuController
                .class.getResource("/images/showField.jpg").toString())));
        isPasswordShown = false;
        invisiblePassword.setVisible(false);
        sloganField.setVisible(false);
        sloganChoice.setVisible(false);
        or.setVisible(false);

        userField.textProperty().addListener((observable, oldText, newText)->{
            if (!Validations.check(newText, Validations.VALID_USERNAME)) {
                usernameError.setText("Invalid username format");
                return;
            }
            else {
                usernameError.setText("");
            }

            if (User.getUserByUsername(newText) != null)
                usernameError.setText("Username already taken");

        });

        emailField.textProperty().addListener((observable, oldText, newText)->{
            if (!Validations.check(newText, Validations.VALID_EMAIL))
                emailError.setText("Invalid email format");
            else {
                emailError.setText("");
            }
        });

        passwordField.textProperty().addListener((observable, oldText, newText)->{
            if (newText.length() < 6) {
                passwordError.setText("Password needs to be more than 6 characters");
                return;
            } else if (newText.length() >= 6){
                passwordError.setText("");
            }

            if (!Validations.check(newText, Validations.STRONG_PASSWORD))
                passwordError.setText("Weak Password");
            else {
                passwordError.setText("");
            }
        });




    }

    public void displayPassword(MouseEvent mouseEvent) {
        String password1 = passwordField.getText();
        invisiblePassword.setText(password1);
        invisiblePassword.setVisible(true);
        passwordField.setVisible(false);
        maskField.setFill(new ImagePattern(new Image(RegisterAndLoginMenuController
                .class.getResource("/images/hideField.jpg").toString())));
        isPasswordShown = true;
    }

    public void randomPasswordGenerate(MouseEvent mouseEvent) {
        String randomPass = Password.randomPassword();
        isPasswordShown = true;
        invisiblePassword.setText(randomPass);
        passwordField.setText(randomPass);
        invisiblePassword.setVisible(true);
        passwordField.setVisible(false);
        maskField.setFill(new ImagePattern(new Image(RegisterAndLoginMenuController
                .class.getResource("/images/hideField.jpg").toString())));

    }

    public void displaySlogan(MouseEvent mouseEvent) {
        if (sloganCheck.isSelected()){
            sloganChoice.setVisible(true);
            sloganField.setVisible(true);
            or.setVisible(true);
        } else {
            sloganChoice.setVisible(false);
            sloganField.setVisible(false);
            or.setVisible(false);
        }
    }

    public void backToRegisterAndLoginMenu(MouseEvent mouseEvent) throws Exception {
        new RegisterAndLoginMenu().start(LaunchMenu.getStage());
    }

    public void hidePassword(MouseEvent mouseEvent) {
        maskField.setFill(new ImagePattern(new Image(RegisterAndLoginMenuController
                .class.getResource("/images/showField.jpg").toString())));
        isPasswordShown = false;
        invisiblePassword.setVisible(false);
        passwordField.setVisible(true);
    }

    public void randomSloganGenerate(MouseEvent mouseEvent) {
        String randomSlogan = UserController.randomSloganGenerator();
        sloganField.setText(randomSlogan);
    }

    public void confirmTheInfo(MouseEvent mouseEvent) throws Exception {
        if (userField.getText().isEmpty()) {
            usernameError.setText("Empty Field!");
            return;
        }
        if (passwordField.getText().isEmpty()) {
            passwordError.setText("Empty Field!");
            return;
        }
        if (emailField.getText().isEmpty()) {
            emailError.setText("Empty Field!");
            return;
        }
        if (nickNameField.getText().isEmpty()){
            nickNameError.setText("Empty Field!");
        }
        String slogan = "";
        if (sloganCheck.isSelected()){
            if (sloganChoice.getValue() == null && sloganField.getText().isEmpty()){
                sloganError.setText("Empty Field!");
                return;
            }

            if (sloganChoice.getValue() != null && !sloganField.getText().isEmpty()){
                sloganError.setText("You cannot select both ways");
                return;
            }
            if (sloganChoice.getValue() != null)
                slogan = sloganChoice.getValue();
            else
                slogan = sloganField.getText();
        }

//        Password password = new Password(passwordField.getText());
//        User user = new User(userField.getText(), password, nickNameField.getText(), emailField.getText());
//        if (!slogan.equals("")) user.setSlogan(slogan);
//        User.getUsers().add(user);
//        User.updateDataBase();


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Registration");
        alert.setContentText("Your information have been received, please complete the process to finish" +
                "your registration");
        alert.initOwner(LaunchMenu.getStage());
        alert.show();
        if (alert.getButtonTypes().get(0) == ButtonType.OK){
            new SecurityAndCaptchaRegister().start(LaunchMenu.getStage());
        }



    }
}
