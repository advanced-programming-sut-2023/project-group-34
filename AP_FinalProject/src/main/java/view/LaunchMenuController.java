package view;

import javafx.scene.input.MouseEvent;
import model.user.User;
import view.starter.RegisterAndLoginMenu;

public class LaunchMenuController {
    public void launchTheGame(MouseEvent mouseEvent) throws Exception {
        if (User.currentUser == null){
            new RegisterAndLoginMenu().start(LaunchMenu.getStage());
        }
    }
}
