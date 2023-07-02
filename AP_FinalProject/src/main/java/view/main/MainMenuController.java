package view.main;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.user.User;
import view.ClientMenu;
import view.LaunchMenu;
import view.game.LobbyMenu;
import view.profile.ProfileMenu;
import view.starter.RegisterAndLoginMenu;

import java.net.URL;
import java.util.Optional;

public class MainMenuController {
    public void quitGame(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(LaunchMenu.getStage());
        alert.setHeaderText("Exiting");
        alert.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            Platform.exit();
        }
    }

    public void logout(MouseEvent mouseEvent) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(LaunchMenu.getStage());
        alert.setHeaderText("Logging Out");
        alert.setContentText("Are you sure you want to logout?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            User.currentUser = null;
            User.currentUserJsonSaver();
            User.updateDataBase();
            LaunchMenu.dataOutputStream.writeUTF("user logout");
            new RegisterAndLoginMenu().start(LaunchMenu.getStage());
        }
    }

    public void onToProfileMenu(MouseEvent mouseEvent) throws Exception{
        new ProfileMenu().start(LaunchMenu.getStage());
    }

    public void selectingPlayers(MouseEvent mouseEvent) throws Exception{
        Stage stage = new Stage();
        URL url = ProfileMenu.class.getResource("/FXML/creatingLobby.fxml");
        Pane pane = FXMLLoader.load(url);
        Scene scene = new Scene(pane, 300, 200);
        stage.setScene(scene);
        stage.initOwner(LaunchMenu.getStage());
        stage.show();
    }

    public void onToChatRoom(MouseEvent mouseEvent) throws Exception {
        new ChatRoomMenu().start(LaunchMenu.getStage());
    }

    public void onToFriendship(MouseEvent mouseEvent) throws Exception{
        new FriendShipMenu().start(LaunchMenu.getStage());
    }
    public void onToMapMenu(MouseEvent mouseEvent) throws Exception{
        new ClientMenu().start(LaunchMenu.getStage());
    }

    public void onToLobbyList(MouseEvent mouseEvent) throws Exception{
        new LobbyListMenu().start(LaunchMenu.getStage());
    }
}
