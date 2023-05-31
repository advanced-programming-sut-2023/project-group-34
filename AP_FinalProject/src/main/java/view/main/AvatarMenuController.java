package view.main;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import model.user.User;
import view.LaunchMenu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AvatarMenuController implements Initializable {

    @FXML
    private Rectangle first;

    @FXML
    private Rectangle second;
    @FXML
    private Rectangle third;
    @FXML
    private Rectangle fourth;
    @FXML
    private Rectangle fifth;
    @FXML
    private Rectangle sixth;
    @FXML
    private Button button;
    @FXML
    private Rectangle dragDrop;

    public void backToProfile(MouseEvent mouseEvent) throws Exception{
        new ProfileMenu().start(LaunchMenu.getStage());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        first.setFill(new ImagePattern(new
                Image(AvatarMenu.class.getResource("/images/inGameAvatars/1.png").toString())));
        second.setFill(new ImagePattern(new
                Image(AvatarMenu.class.getResource("/images/inGameAvatars/2.png").toString())));
        third.setFill(new ImagePattern(new
                Image(AvatarMenu.class.getResource("/images/inGameAvatars/3.png").toString())));
        fourth.setFill(new ImagePattern(new
                Image(AvatarMenu.class.getResource("/images/inGameAvatars/4.png").toString())));
        fifth.setFill(new ImagePattern(new
                Image(AvatarMenu.class.getResource("/images/inGameAvatars/5.png").toString())));
        sixth.setFill(new ImagePattern(new
                Image(AvatarMenu.class.getResource("/images/inGameAvatars/6.jpg").toString())));
    }

    public void chooseAvatar1(MouseEvent mouseEvent) {
        User.currentUser.setAvatarLink(AvatarMenu.class.getResource("/images/inGameAvatars/1.png").toString());
        User.updateDataBase();
        User.currentUserJsonSaver();
        showAvatarAlert();
    }

    public void chooseAvatar2(){
        User.currentUser.setAvatarLink(AvatarMenu.class.getResource("/images/inGameAvatars/2.png").toString());
        User.updateDataBase();
        User.currentUserJsonSaver();
        showAvatarAlert();
    }

    public void chooseAvatar3(){
        User.currentUser.setAvatarLink(AvatarMenu.class.getResource("/images/inGameAvatars/3.png").toString());
        User.updateDataBase();
        User.currentUserJsonSaver();
        showAvatarAlert();
    }
    public void chooseAvatar4(){
        User.currentUser.setAvatarLink(AvatarMenu.class.getResource("/images/inGameAvatars/4.png").toString());
        User.updateDataBase();
        User.currentUserJsonSaver();
        showAvatarAlert();
    }
    public void chooseAvatar5(){
        User.currentUser.setAvatarLink(AvatarMenu.class.getResource("/images/inGameAvatars/5.png").toString());
        User.updateDataBase();
        User.currentUserJsonSaver();
        showAvatarAlert();
    }
    public void chooseAvatar6(){
        User.currentUser.setAvatarLink(AvatarMenu.class.getResource("/images/inGameAvatars/6.jpg").toString());
        User.updateDataBase();
        User.currentUserJsonSaver();
        showAvatarAlert();
    }

    public void showAvatarAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(LaunchMenu.getStage());
        alert.setHeaderText("Avatar Change");
        alert.setContentText("Profile changed successfully");
        alert.showAndWait();
    }

    public void changeAvatarChoose(MouseEvent mouseEvent){
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("*.png", "*.jpg");
        fileChooser.getExtensionFilters().add(extFilter);
        button.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(LaunchMenu.getStage());
            try {
                User.currentUser.setAvatarLink(selectedFile.toURI().toURL().toString());
                User.updateDataBase();
                User.currentUserJsonSaver();
            } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
            }
            showAvatarAlert();
        });
    }

    public void handleDragOver(DragEvent dragEvent) {
        if (dragEvent.getDragboard().hasFiles())
            dragEvent.acceptTransferModes(TransferMode.ANY);
    }

    public void handleDragDropped(DragEvent dragEvent) throws FileNotFoundException, MalformedURLException {
        List<File> files = dragEvent.getDragboard().getFiles();
        Image image = new Image(new FileInputStream(files.get(0)));
        User.currentUser.setAvatarLink(files.get(0).toURI().toURL().toString());
        User.updateDataBase();
        User.currentUserJsonSaver();
        dragDrop.setFill(new ImagePattern(image));
        showAvatarAlert();
    }


}
