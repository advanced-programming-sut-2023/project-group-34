package view.starter;

import controller.UserController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import model.enums.SecurityQuestion;
import model.enums.Validations;
import model.user.User;
import view.LaunchMenu;

import java.net.URL;
import java.util.ResourceBundle;

public class SecurityAndCaptchaRegisterController implements Initializable {

    @FXML
    private TextField answerField;
    @FXML
    private CheckBox firstChoice;
    @FXML
    private CheckBox secondChoice;
    @FXML
    private CheckBox thirdChoice;
    private CheckBox theChosenOne;
    @FXML
    private Text answerError;

    @FXML
    private Rectangle captchaRect;

    @FXML
    private Rectangle reloadCaptcha;
    @FXML
    private TextField captchaField;
    @FXML
    private Text captchaError;
    @FXML
    private Button register;

    private int randNum;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        answerField.setVisible(false);

        answerField.textProperty().addListener((observable, oldText, newText)->{
            int counter = 0;
            if (firstChoice.isSelected())
                counter++;
            if (secondChoice.isSelected())
                counter++;
            if (thirdChoice.isSelected())
                counter++;
            if (counter > 1){
                answerError.setText("You can only choose one question");
            } else {
                answerError.setText("");
            }
        });


        setRandomFill();

        reloadCaptcha.setFill(new ImagePattern(new Image(SecurityAndCaptchaRegister
                .class.getResource("/images/reloadCaptcha.png").toString())));



    }

    public void displayAnswerField(MouseEvent mouseEvent) {
        if (firstChoice.isSelected() || secondChoice.isSelected() || thirdChoice.isSelected()) {
            answerField.setVisible(true);
            answerError.setVisible(true);
        }
        else {
            answerField.setVisible(false);
            answerError.setVisible(false);
        }
    }

    public void changeCaptcha(MouseEvent mouseEvent) {
        setRandomFill();
    }

    public void backToLogin(MouseEvent mouseEvent) throws Exception{
        User.getUsers().remove(User.getUsers().size()-1);
        new RegisterAndLoginMenu().start(LaunchMenu.getStage());
    }

    public void registerUser(MouseEvent mouseEvent) throws Exception {
        int counter = 0;
        if (firstChoice.isSelected())
            counter++;
        if (secondChoice.isSelected())
            counter++;
        if (thirdChoice.isSelected())
            counter++;

        if (counter == 0){
            answerError.setText("You have to choose a question first");
            setRandomFill();
            return;
        } else if (answerField.getText().isEmpty()){
            answerError.setText("Empty Field!");
            setRandomFill();
            return;
        }

        if (captchaField.getText().isEmpty()){
            captchaError.setText("You have to fill in the captcha");
            setRandomFill();
            return;
        }


        if (!checkCaptcha())
            return;

        if (firstChoice.isSelected()){
            User.getUsers().get(User.getUsers().size()-1).getPassword().
                    setSecurityQuestion(SecurityQuestion.FIRST_QUESTION);
            User.getUsers().get(User.getUsers().size()-1).getPassword().setAnswer(answerField.getText());
        } else if (secondChoice.isSelected()){
            User.getUsers().get(User.getUsers().size()-1).getPassword().
                    setSecurityQuestion(SecurityQuestion.SECOND_QUESTION);
            User.getUsers().get(User.getUsers().size()-1).getPassword().setAnswer(answerField.getText());
        } else {
            User.getUsers().get(User.getUsers().size()-1).getPassword().
                    setSecurityQuestion(SecurityQuestion.THIRD_QUESTION);
            User.getUsers().get(User.getUsers().size()-1).getPassword().setAnswer(answerField.getText());
        }
        User.updateDataBase();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(LaunchMenu.getStage());
        alert.setHeaderText("Registration");
        alert.setContentText("Registration Complete, going back to Login Menu");
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
