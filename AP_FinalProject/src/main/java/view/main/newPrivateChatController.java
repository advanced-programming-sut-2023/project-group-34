package view.main;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.enums.Validations;
import model.messenger.Chat;
import model.messenger.PrivateChat;
import model.user.User;

import java.net.URL;
import java.util.ResourceBundle;

public class newPrivateChatController implements Initializable {
    @FXML
    private TextField username;
    @FXML
    private Text usernameError;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        username.textProperty().addListener((observable, oldText, newText)->{
            if (User.getUserByUsername(newText) == null){
                usernameError.setText("No User with this ID");
                return;
            } else {
                usernameError.setText("");
            }


            for (Chat chat : ChatRoomMenuController.chats){
                if (chat instanceof PrivateChat && ((PrivateChat) chat).getUsers().contains(User.getUserByUsername(username.getText()))){
                    usernameError.setText("You already have a chat with this user");
                    return;
                } else {
                    usernameError.setText("");
                }
            }


        });
    }

    public void createNewChat(MouseEvent mouseEvent) {
        if (!usernameError.getText().isEmpty())
            return;
        else {
            //TODO this part has to be done by Arshia
        }
    }
}
