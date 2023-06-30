package view.starter;

import controller.UserController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.user.User;
import view.LaunchMenu;
import view.main.MainMenu;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterAndLoginMenuController implements Initializable {

    @FXML
    private Text userPassError;
    @FXML
    private Rectangle showPass;

    private boolean isPasswordShown;

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    @FXML private TextField invisibleText;

    @FXML
    private Rectangle captchaRect;
    @FXML
    private Rectangle reloadCaptcha;
    @FXML
    private TextField captchaField;
    @FXML
    private Text captchaError;
    private int randNum;
    @FXML
    private CheckBox stayLoggedIn;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showPass.setFill(new ImagePattern(new Image(RegisterAndLoginMenuController
                .class.getResource("/images/showField.jpg").toString())));
        isPasswordShown = false;
        invisibleText.setVisible(false);

        setRandomFill();
        reloadCaptcha.setFill(new ImagePattern(new
                Image(RegisterAndLoginMenu.class.getResource("/images/reloadCaptcha.png").toString())));


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

    private void setRandomFill(){
        randNum = UserController.randomCaptcha();
        captchaRect.setFill(new ImagePattern(new Image(SecurityAndCaptchaRegister.
                class.getResource("/images/captcha/" + randNum + ".png").toString())));
    }

    public void changeCaptcha(MouseEvent mouseEvent) {
        setRandomFill();
    }


    public void loginUser(MouseEvent mouseEvent) throws Exception {
        if (username.getText().isEmpty() || password.getText().isEmpty()){
            userPassError.setText("Empty Field!");
            setRandomFill();
            return;
        } else {
            userPassError.setText("");
        }

        if (User.getUserByUsername(username.getText()) == null){
            userPassError.setText("No account with this name exists!");
            setRandomFill();
            return;
        } else {
            userPassError.setText("");
        }

        if (captchaField.getText().isEmpty()){
            captchaError.setText("Empty Captcha Field!");
            setRandomFill();
            return;
        } else {
            captchaError.setText("");
        }

        if (!User.getUserByUsername(username.getText()).getPassword().checkPassword(password.getText())){
            userPassError.setText("Username and Password did not match!");
            setRandomFill();
            return;
        } else {
            userPassError.setText("");
        }

        if (!checkCaptcha())
            return;

        User.currentUser = User.getUserByUsername(username.getText());
        if (stayLoggedIn.isSelected())
            User.currentUserJsonSaver();
        new MainMenu().start(LaunchMenu.getStage());

        //TODO fix this part for logging in
    }

    public boolean checkCaptcha(){
        for (CaptchaImages captchaImages : CaptchaImages.values()){
            if (captchaImages.getNumber() == randNum){
                if (!Integer.toString(captchaImages.getNumber()).equals(captchaField.getText())) {
                    captchaError.setText("Wrong captcha");
                    setRandomFill();
                    captchaField.setText("");
                    return false;
                }
            }
        }
        captchaError.setText("");
        return true;
    }
}
