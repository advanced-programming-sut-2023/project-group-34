package view.main;

import javafx.scene.input.MouseEvent;
import view.LaunchMenu;

public class ScoreBoardMenuController {
    public void backToProfile(MouseEvent mouseEvent) throws Exception{
        new ProfileMenu().start(LaunchMenu.getStage());
    }
}
