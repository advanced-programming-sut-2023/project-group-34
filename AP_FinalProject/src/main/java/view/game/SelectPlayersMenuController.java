package view.game;

import com.google.gson.Gson;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.PlayersToPlay;
import model.enums.Validations;
import model.user.User;
import view.LaunchMenu;
import view.main.MainMenu;
import view.profile.changeAvatarInScoreBoardDialog;

import java.net.URL;
import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.ResourceBundle;

public class SelectPlayersMenuController implements Initializable {

    @FXML
    private TextField username;
    @FXML
    private Text error;
    @FXML
    private Button confirm;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        PlayersToPlay.getUsersToPlay().add(User.currentUser);
        username.textProperty().addListener((observable, oldText, newText)->{
            if (User.getUserByUsername(username.getText()) == null) {
                error.setText("no such user");
            } else if (User.getUserByUsername(username.getText()).equals(User.currentUser)){
                error.setText("You cannot pick yourself");
            } else if (PlayersToPlay.getUsersToPlay().size() == 4){
                error.setText("Maximum Players");
            } else
                error.setText("");
        });

    }


    public void backToMain(MouseEvent mouseEvent) throws Exception {
        PlayersToPlay.getUsersToPlay().clear();
        new MainMenu().start(LaunchMenu.getStage());
    }

    public void addPlayer(MouseEvent mouseEvent) {
        if (!error.getText().isEmpty())
            return;
        else {
            PlayersToPlay.getUsersToPlay().add(User.getUserByUsername(username.getText()));
            PlayersToPlay.updateDataBase();
        }
    }
}
