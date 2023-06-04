package view.main;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import model.user.User;
import view.LaunchMenu;
import view.profile.ProfileMenu;
import view.starter.RegisterAndLoginMenu;

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
            new RegisterAndLoginMenu().start(LaunchMenu.getStage());
        }
    }

    public void onToProfileMenu(MouseEvent mouseEvent) throws Exception{
        new ProfileMenu().start(LaunchMenu.getStage());
    }
}
