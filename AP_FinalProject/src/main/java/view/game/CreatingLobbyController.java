package view.game;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.Lobby;
import view.LaunchMenu;

public class CreatingLobbyController {
    @FXML
    private TextField lobbyName;
    public void makeLobbyForTwo(MouseEvent mouseEvent) throws Exception{
        Lobby lobby = new Lobby(2, lobbyName.getText());
        LobbyMenuController.currentLobby = lobby;
        new LobbyMenu().start(LaunchMenu.getStage());
    }

    public void makeLobbyForThree(MouseEvent mouseEvent) throws Exception{
        Lobby lobby = new Lobby(3, lobbyName.getText());
        LobbyMenuController.currentLobby = lobby;
        new LobbyMenu().start(LaunchMenu.getStage());
    }

    public void makeLobbyForFour(MouseEvent mouseEvent) throws Exception{
        Lobby lobby = new Lobby(4, lobbyName.getText());
        LobbyMenuController.currentLobby = lobby;
        new LobbyMenu().start(LaunchMenu.getStage());
    }
}
