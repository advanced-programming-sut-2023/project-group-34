package view.game;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.Lobby;
import view.LaunchMenu;

public class CreatingLobbyController {
    @FXML
    private TextField lobbyName;
    public void makeLobbyForTwo(MouseEvent mouseEvent) throws Exception{
        LaunchMenu.dataOutputStream.writeUTF("create lobby -name " + lobbyName.getText() + " -id 2");
        LobbyMenuController.currentLobby = new Gson().fromJson(LaunchMenu.dataInputStream.readUTF() , Lobby.class);
        new LobbyMenu().start(LaunchMenu.getStage());
    }

    public void makeLobbyForThree(MouseEvent mouseEvent) throws Exception{
        LaunchMenu.dataOutputStream.writeUTF("create lobby -name " + lobbyName.getText() + " -id 3");
        LobbyMenuController.currentLobby = new Gson().fromJson(LaunchMenu.dataInputStream.readUTF() , Lobby.class);
        new LobbyMenu().start(LaunchMenu.getStage());
    }

    public void makeLobbyForFour(MouseEvent mouseEvent) throws Exception{
        LaunchMenu.dataOutputStream.writeUTF("create lobby -name " + lobbyName.getText() + " -id 4");
        LobbyMenuController.currentLobby = new Gson().fromJson(LaunchMenu.dataInputStream.readUTF() , Lobby.class);
        new LobbyMenu().start(LaunchMenu.getStage());
    }
}
