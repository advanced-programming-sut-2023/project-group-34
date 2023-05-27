package view.starter;

import controller.UserController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.enums.Validations;
import model.user.User;
import view.LaunchMenu;

import java.net.URL;
import java.util.ResourceBundle;

public class ForgotPasswordMenuController implements Initializable {
    @FXML
    private TextField userField;
    @FXML
    private Text userError;

    @FXML
    private TextField answerField;

    @FXML
    private Text answerError;
    @FXML
    private Text answerTitle;
    @FXML
    private PasswordField newPass;
    @FXML
    private PasswordField confirm;
    @FXML
    private TextField newPassDisplay;
    @FXML
    private TextField confirmDisplay;
    @FXML
    private Text passError;
    @FXML
    private Rectangle passShower;
    @FXML
    private Rectangle captchaRect;
    @FXML
    private Rectangle reloadCaptcha;
    @FXML
    private TextField captchaField;
    @FXML
    private Text captchaError;
    private int randNum;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        passShower.setFill(new ImagePattern(new
                Image(ForgotPasswordMenuController.class.getResource("/images/showField.jpg").toString())));

        reloadCaptcha.setFill(new ImagePattern(new Image(ForgotPasswordMenuController.
                class.getResource("/images/reloadCaptcha.png").toString())));

        setRandomFill();

        User.loadAllUsersFromDataBase(); //Just for debug
        answerField.setVisible(false);
        answerError.setVisible(false);
        answerTitle.setVisible(false);
        newPassDisplay.setVisible(false);
        confirmDisplay.setVisible(false);
        newPass.setVisible(false);
        confirm.setVisible(false);
        passError.setVisible(false);
        passShower.setVisible(false);

        userField.textProperty().addListener((observable, oldText, newText)->{

            if (User.getUserByUsername(userField.getText()) == null  && !userField.getText().equals("")) {
                userError.setText("No account with this username");
                answerField.setVisible(false);
                answerError.setVisible(false);
                answerTitle.setVisible(false);
                newPass.setVisible(false);
                newPassDisplay.setVisible(false);
                confirm.setVisible(false);
                confirmDisplay.setVisible(false);
                passError.setVisible(false);
                passShower.setVisible(false);
            } else if (userField.getText().isEmpty()){
                    userError.setText("");
            } else {
                answerTitle.setText(answerTitle.getText() + ":   " + User.getUserByUsername(userField.getText()).getPassword().getSecurityQuestion().toString());
                userError.setText("");
                answerField.setVisible(true);
                answerTitle.setVisible(true);
                answerError.setVisible(true);
                newPass.setVisible(true);
                confirm.setVisible(true);
                passError.setVisible(true);
                passShower.setVisible(true);
            }
        });

        newPass.textProperty().addListener((observable, oldText, newText)->{
            if (newText.length() < 6) {
                passError.setText("Password needs to be more than 6 characters");
                return;
            } else if (newText.length() >= 6){
                passError.setText("");
            }

            if (!Validations.check(newText, Validations.STRONG_PASSWORD))
                passError.setText("Weak Password");
            else {
                passError.setText("");
            }
        });

    }

    public void displayPasswords(MouseEvent mouseEvent) {
        newPassDisplay.setText(newPass.getText());
        confirmDisplay.setText(confirm.getText());
        newPassDisplay.setVisible(true);
        confirmDisplay.setVisible(true);
        confirm.setVisible(false);
        newPass.setVisible(false);
        passShower.setFill(new ImagePattern(new Image(ForgotPasswordMenuController.class.getResource
                ("/images/hideField.jpg").toString())));
    }

    public void hidePasswords(MouseEvent mouseEvent) {
        newPassDisplay.setVisible(false);
        confirmDisplay.setVisible(false);
        confirm.setVisible(true);
        newPass.setVisible(true);
        passShower.setFill(new ImagePattern(new Image(ForgotPasswordMenuController.class.getResource
                ("/images/showField.jpg").toString())));

    }

    public void changeCaptcha(MouseEvent mouseEvent) {
        setRandomFill();
    }

    public void backToLogin(MouseEvent mouseEvent) throws Exception{
        new RegisterAndLoginMenu().start(LaunchMenu.getStage());
    }

    public void changePassword(MouseEvent mouseEvent) throws Exception {

        if (userField.getText().isEmpty()){
            userError.setText("Empty Field!");
            setRandomFill();
            return;
        } else {
            userError.setText("");
        }

        if (!User.getUserByUsername(userField.getText()).getPassword().checkAnswer(answerField.getText())){
            answerError.setText("Wrong Answer!");
            setRandomFill();
            return;
        } else {
            answerError.setText("");
        }

        if (newPass.getText().isEmpty()){
            passError.setText("You have to fill this in");
            setRandomFill();
            return;
        } else if (!newPass.getText().equals(confirm.getText())){
            passError.setText("Confirmation failed");
            setRandomFill();
            return;
        } else {
            passError.setText("");
        }

        if (captchaField.getText().isEmpty()){
            captchaError.setText("Empty Captcha Field!");
            setRandomFill();
            return;
        } else {
            captchaError.setText("");
        }


        if (!checkCaptcha())
            return;

        User.getUserByUsername(userField.getText()).getPassword().setPasswordName(newPass.getText());
        User.updateDataBase();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(LaunchMenu.getStage());
        alert.setHeaderText("Change Password");
        alert.setContentText("Changed password successfully, going back to Login Menu");
        alert.showAndWait();
        if (alert.getButtonTypes().get(0) == ButtonType.OK)
            new RegisterAndLoginMenu().start(LaunchMenu.getStage());
    }

    private void setRandomFill(){
        randNum = UserController.randomCaptcha();
        captchaRect.setFill(new ImagePattern(new Image(SecurityAndCaptchaRegister.
                class.getResource("/images/captcha/" + randNum + ".png").toString())));
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
