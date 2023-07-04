package view.game;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.Lobby;
import model.PlayersToPlay;
import model.user.User;
import org.checkerframework.checker.units.qual.A;
import view.LaunchMenu;
import view.main.MainMenu;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LobbyMenuController implements Initializable {


    @FXML
    private Label lobbyId;
    @FXML
    private Rectangle avatar1;
    @FXML
    private Rectangle avatar2;
    @FXML
    private Rectangle avatar3;
    @FXML
    private Rectangle avatar4;

    @FXML
    private Label name1;
    @FXML
    private Label name2;
    @FXML
    private Label name3;
    @FXML
    private Label name4;
    @FXML
    private Label lobbyStatus;

    public ArrayList<Rectangle> avatars = new ArrayList<>();
    public ArrayList<Label> names = new ArrayList<>();

    public static Lobby currentLobby;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addAllAvatars();
        addAllNames();
        hideNames();
        hideAvatars();
        lobbyId.setText(lobbyId.getText() + currentLobby.getID());
        
        if (currentLobby != null && currentLobby.isPrivate())
            lobbyStatus.setText("Private");
        else lobbyStatus.setText("Public");
        showPlayers();




    }


    public void backToMain(MouseEvent mouseEvent) throws Exception {
        PlayersToPlay.getUsersToPlay().clear();
        new MainMenu().start(LaunchMenu.getStage());
    }

    public void addAllAvatars(){
        avatars.add(avatar1);
        avatars.add(avatar2);
        avatars.add(avatar3);
        avatars.add(avatar4);
    }

    public void addAllNames(){
        names.add(name1);
        names.add(name2);
        names.add(name3);
        names.add(name4);
    }

    public void hideAvatars(){
        avatar1.setVisible(false);
        avatar2.setVisible(false);
        avatar3.setVisible(false);
        avatar4.setVisible(false);
    }

    public void hideNames(){
        name1.setVisible(false);
        name2.setVisible(false);
        name3.setVisible(false);
        name4.setVisible(false);
    }
    public void setPrivate(MouseEvent mouseEvent) {
        currentLobby.setPrivate(true);
        try {
            LaunchMenu.dataOutputStream.writeUTF("change lobby -id " + currentLobby.getID() + " private");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        lobbyStatus.setText("Private");
    }

    public void setPublic(MouseEvent mouseEvent) {
        currentLobby.setPrivate(false);
        try {
            LaunchMenu.dataOutputStream.writeUTF("change lobby -id " + currentLobby.getID() + " public");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        lobbyStatus.setText("Public");
    }

    public void showPlayers(){
        hideAvatars();
        hideNames();
        for (int i = 0 ; i < currentLobby.getPlayers().size(); i++){
            avatars.get(i).setFill(new ImagePattern(new Image(currentLobby.getPlayers().get(i).getAvatarLink())));
            avatars.get(i).setVisible(true);
            names.get(i).setText(currentLobby.getPlayers().get(i).getName());
            names.get(i).setVisible(true);
        }
    }

    public void onToLobbyChat(MouseEvent mouseEvent) throws Exception{
        new LobbyChatMenu().start(LaunchMenu.getStage());
    }

    public void startGame(MouseEvent mouseEvent) {
        if(!currentLobby.getAdmin().getName().equals(User.currentUser.getName())) {
            Alert alert = new Alert(Alert.AlertType.ERROR , "You are not the admin!");
            alert.show();
            return;
        }
        if(currentLobby.getPlayers().size() == 1) {
            new Alert(Alert.AlertType.ERROR , "You can't play just by yourself go get a friend!").show();
            return;
        }
        try {
            LaunchMenu.dataOutputStream.writeUTF("start game -id " + currentLobby.getID());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            new MainMenu().start(LaunchMenu.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void refresh(MouseEvent mouseEvent) {
        try {
            LaunchMenu.dataOutputStream.writeUTF("get lobby -id " + currentLobby.getID());
            LobbyMenuController.currentLobby = new Gson().fromJson(LaunchMenu.dataInputStream.readUTF(), Lobby.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        showPlayers();
    }
}
