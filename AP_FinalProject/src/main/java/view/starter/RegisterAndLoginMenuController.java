package view.starter;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import view.LaunchMenu;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterAndLoginMenuController implements Initializable {

    @FXML
    private Rectangle showPass;

    private boolean isPasswordShown;

    @FXML
    private PasswordField password;

    @FXML private TextField invisibleText;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showPass.setFill(new ImagePattern(new Image(RegisterAndLoginMenuController
                .class.getResource("/images/showField.jpg").toString())));
        isPasswordShown = false;
        invisibleText.setVisible(false);


    }

    public void displayPassword(MouseEvent mouseEvent) {
        String password1 = password.getText();
            invisibleText.setText(password1);
            invisibleText.setVisible(true);
            password.setVisible(false);
            showPass.setFill(new ImagePattern(new Image(RegisterAndLoginMenuController
                    .class.getResource("/images/hideField.jpg").toString())));
            isPasswordShown = true;
    }

    public void enterRegisterMenu(MouseEvent mouseEvent) throws Exception {
        new RegisterMenu().start(LaunchMenu.getStage());
    }

    public void hidePassword(MouseEvent mouseEvent) {
        showPass.setFill(new ImagePattern(new Image(RegisterAndLoginMenuController
                .class.getResource("/images/showField.jpg").toString())));
        isPasswordShown = false;
        invisibleText.setVisible(false);
        password.setVisible(true);
    }

    public void toForgotPasswordMenu(MouseEvent mouseEvent) throws Exception{
        new ForgotPasswordMenu().start(LaunchMenu.getStage());
    }
}
