package view.game;

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

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LobbyMenuController implements Initializable {


    @FXML
    private Label lobbyId;

    public Lobby currnetLobby;

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
//        Lobby lobby = new Lobby(3, "gg");
//        lobby.getPlayers().add(User.currentUser);
//        lobby.getPlayers().add(User.getUserByUsername("abud"));
//        currnetLobby = lobby;


        lobbyId.setText(lobbyId.getText() + currentLobby.getID());
        if (currnetLobby.isPrivate())
            lobbyStatus.setText("Private");
        else lobbyStatus.setText("Public");
        showPlayers();

        //TODO this might need a thread that checks if a player has joined




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
        currnetLobby.setPrivate(true);
        lobbyStatus.setText("Private");
    }

    public void setPublic(MouseEvent mouseEvent) {
        currnetLobby.setPrivate(false);
        lobbyStatus.setText("Public");
    }

    public void showPlayers(){
        hideAvatars();
        hideNames();
        for (int i = 0 ; i < currnetLobby.getPlayers().size(); i++){
            avatars.get(i).setFill(new ImagePattern(new Image(currnetLobby.getPlayers().get(i).getAvatarLink())));
            avatars.get(i).setVisible(true);
            names.get(i).setText(currnetLobby.getPlayers().get(i).getName());
            names.get(i).setVisible(true);
        }
    }

    public void onToLobbyChat(MouseEvent mouseEvent) throws Exception{
        new LobbyChatMenu().start(LaunchMenu.getStage());
    }
}