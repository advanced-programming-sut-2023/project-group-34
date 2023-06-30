package view.main;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.enums.Validations;
import model.messenger.Chat;
import model.messenger.PrivateChat;
import model.user.User;
import view.LaunchMenu;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class newPrivateChatController implements Initializable {
    @FXML
    private TextField username;
    @FXML
    private Text usernameError;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        username.textProperty().addListener((observable, oldText, newText)->{
            User user;
            try {
                LaunchMenu.dataOutputStream.writeUTF("get user -username " + newText);
                user = new Gson().fromJson(LaunchMenu.dataInputStream.readUTF() , User.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (user == null){
                usernameError.setText("No User with this ID");
                return;
            } else {
                usernameError.setText("");
            }


            for (Chat chat : ChatRoomMenuController.chats){
                if (chat instanceof PrivateChat && chat.getUsers().contains(user)){
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
            try {
                LaunchMenu.dataOutputStream.writeUTF("create pv");
                LaunchMenu.dataOutputStream.writeUTF(new Gson().toJson(
                        new ArrayList<String>(Arrays.asList(User.currentUser.getName() , username.getText()))));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
