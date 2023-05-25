package view.starter;

import controller.UserController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import model.enums.Validations;

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
            if (counter == 0)
                answerError.setText("You have not chosen a question yet!");
            else if (counter > 1){
                answerError.setText("You can only choose one question");
            } else {
                answerError.setText("");
            }
        });


        int randNum = UserController.randomCaptcha();
        captchaRect.setFill(new ImagePattern(new Image(SecurityAndCaptchaRegister.
                class.getResource("/images/captcha/" + randNum + ".png").toString())));


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
}
