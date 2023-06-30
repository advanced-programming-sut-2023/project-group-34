package view.main;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.messenger.Chat;
import model.messenger.PrivateChat;
import model.user.User;
import view.LaunchMenu;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class NewGroupChatController implements Initializable {

    public static ArrayList<User> usersToJoinGroup = new ArrayList<>();

    @FXML
    private Text error;
    @FXML
    private TextField username;
    @FXML
    private TextField groupName;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usersToJoinGroup.clear();
        usersToJoinGroup.add(User.currentUser);

        username.textProperty().addListener((observable, oldText, newText)->{
            if (User.getUserByUsername(newText) == null){
                error.setText("No User with this ID");
            } else {
                error.setText("");
            }
        });
    }

    public void addUserToJoinGroup(MouseEvent mouseEvent) {
        if (!error.getText().isEmpty() && !error.getText().equals("User added successfully")){

        } else {
            usersToJoinGroup.add(User.getUserByUsername(username.getText()));
            error.setText("User added successfully");
        }
    }

    public void createGroup(MouseEvent mouseEvent) {
        if (!error.getText().isEmpty()){

        } else {
            //TODO this part has to be done by Arshia createGroup
        }
    }
}
