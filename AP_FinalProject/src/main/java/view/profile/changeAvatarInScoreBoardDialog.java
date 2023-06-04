package view.profile;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.user.User;

public class changeAvatarInScoreBoardDialog extends Dialog<ButtonType> {
    private Label label;
    private Rectangle newProfile;
    private String avatarLink;

    public changeAvatarInScoreBoardDialog(String avatarLink){
        super();
        this.setTitle("Avatar Change");
        this.setResizable(true);
        this.avatarLink = avatarLink;
        buildUI();
    }

    private void buildUI() {
        Pane pane = createPane();
        getDialogPane().setContent(pane);
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        Button button = (Button) getDialogPane().lookupButton(ButtonType.OK);
        button.addEventFilter(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                User.currentUser.setAvatarLink(avatarLink);
                User.currentUserJsonSaver();
                User.updateDataBase();
            }
        });
    }

    private Pane createPane() {
        VBox content = new VBox(10);
        content.setAlignment(Pos.CENTER);
        content.setMinWidth(300);
        this.label = new Label();
        this.newProfile = new Rectangle();
        this.newProfile.setWidth(100);
        this.newProfile.setHeight(100);
        this.label.setText("Do you want to use this player's avatar?");
        newProfile.setFill(new ImagePattern(new Image(avatarLink)));
        label.setStyle("-fx-font-size: 20; -fx-text-fill: red;-fx-font-family: " +
                "\"Bahnschrift\";-fx-font-style: oblique");
        content.getChildren().addAll(label, newProfile);
        return content;
    }
}
