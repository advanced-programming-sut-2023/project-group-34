package view.starter;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.enums.Validations;
import model.user.User;

import java.net.URL;
import java.util.ResourceBundle;

public class ForgotPasswordMenuController implements Initializable {
    @FXML
    private TextField userField;
    @FXML
    private Text userError;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userField.textProperty().addListener((observable, oldText, newText)->{
            if (User.getUserByUsername(userField.getText()) == null){
                userError.setText("No account with this username");
            } else {

            }
        });
    }
}
