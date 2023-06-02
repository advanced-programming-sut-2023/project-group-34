package view.main;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.enums.Validations;
import model.user.User;
import view.LaunchMenu;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProfileMenuController implements Initializable {

    @FXML
    private Rectangle avatarRect;

    @FXML
    private Text userText;
    @FXML
    private TextField userTextField;
    @FXML
    private Rectangle editRectUser;
    private boolean isUserSelected;
    @FXML
    private Text userError;
    @FXML
    private Button userConfirm;
    @FXML
    private Text passText;
    @FXML
    private TextField passTextField;
    @FXML
    private Rectangle editRectPass;
    private boolean isPassSelected;
    @FXML
    private Text nickText;
    @FXML
    private TextField nickTextField;
    @FXML
    private Rectangle editRectNick;
    @FXML
    private Button nickConfirm;
    private boolean isNickSelected;
    @FXML
    private Text emailText;
    @FXML
    private TextField emailTextField;
    @FXML
    private Rectangle editRectEmail;
    @FXML
    private Text emailError;
    @FXML
    private Button emailConfirm;
    private boolean isEmailSelected;
    @FXML
    private Text sloganText;
    @FXML
    private TextField sloganTextField;
    @FXML
    private Rectangle editRectSlogan;
    @FXML
    private Text sloganError;
    @FXML
    private Button sloganConfirm;
    private boolean isSloganSelected;





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        avatarRect.setFill(new ImagePattern(new Image(User.currentUser.getAvatarLink())));
        editRectUser.setFill(new ImagePattern(new Image(
                ProfileMenu.class.getResource("/images/editPen.png").toString())));

        editRectPass.setFill(new ImagePattern(new Image(
                ProfileMenu.class.getResource("/images/editPen.png").toString())));

        editRectNick.setFill(new ImagePattern(new Image(
                ProfileMenu.class.getResource("/images/editPen.png").toString())));

        editRectEmail.setFill(new ImagePattern(new Image(
                ProfileMenu.class.getResource("/images/editPen.png").toString())));
        editRectSlogan.setFill(new ImagePattern(new Image(
                ProfileMenu.class.getResource("/images/editPen.png").toString())));



        userTextField.setVisible(false);
        userConfirm.setVisible(false);
        isUserSelected = false;

        passTextField.setVisible(false);
        isPassSelected = false;

        nickTextField.setVisible(false);
        nickConfirm.setVisible(false);
        isNickSelected = false;

        emailTextField.setVisible(false);
        emailConfirm.setVisible(false);
        isEmailSelected = false;

        sloganTextField.setVisible(false);
        sloganConfirm.setVisible(false);
        isSloganSelected = false;






        userText.setText(User.currentUser.getName());
        passText.setText(User.currentUser.getCurrentPassword());
        nickText.setText(User.currentUser.getNickname());
        emailText.setText(User.currentUser.getEmail());
        if (User.currentUser.getSlogan() != null){
            sloganText.setText(User.currentUser.getSlogan());
        } else {
            sloganText.setText("Empty!");
        }


        userTextField.textProperty().addListener((observable, oldText, newText)->{
            if (!Validations.check(newText, Validations.VALID_USERNAME)) {
                userError.setText("Invalid username format");
                return;
            }
            else {
                userError.setText("");
            }

            if (User.getUserByUsername(newText) != null && !User.getUserByUsername(newText).equals(User.currentUser)) {
                userError.setText("Username already taken");
                return;
            }

        });

        emailTextField.textProperty().addListener((observable, oldText, newText)-> {
            if (!Validations.check(newText, Validations.VALID_EMAIL))
                emailError.setText("Invalid email format");
            else if (User.getUserByEmail(emailTextField.getText()) != null
                    && !User.getUserByEmail(emailTextField.getText()).equals(User.currentUser)){
                emailError.setText("Email already taken");
            }else {
                emailError.setText("");
            }
        });

    }

    public void changeUser(MouseEvent mouseEvent) {
        if (!isUserSelected){
            isUserSelected = true;
            userTextField.setText(User.currentUser.getName());
            userTextField.setVisible(true);
            userConfirm.setVisible(true);
            userText.setVisible(false);
        } else {
            isUserSelected = false;
            userTextField.setVisible(false);
            userText.setVisible(true);
            userConfirm.setVisible(false);
        }
    }

    public void confirmChangingUser(MouseEvent mouseEvent) {
        if (userError.getText().isEmpty()){
            User.currentUser.setName(userTextField.getText());
            isUserSelected = false;
            userText.setText(userTextField.getText());
            userConfirm.setVisible(false);
            userTextField.setVisible(false);
            userError.setVisible(false);
            userText.setVisible(true);
            User.updateDataBase();
            User.currentUserJsonSaver();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initOwner(LaunchMenu.getStage());
            alert.setHeaderText("Change Username");
            alert.setContentText("Username changed successfully!");
            alert.showAndWait();
        }
    }

    public void changePass(MouseEvent mouseEvent) {
        Dialog<ButtonType> changePassDialog = new changePasswordDialog();
        changePassDialog.initOwner(LaunchMenu.getStage());
        Optional<ButtonType> result = changePassDialog.showAndWait();
        if (result.get() == ButtonType.CLOSE)
            changePassDialog.close();
        passText.setText(User.currentUser.getCurrentPassword());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Change Password");
        alert.setContentText("Password changed successfully!");
        alert.initOwner(LaunchMenu.getStage());
        alert.showAndWait();


    }

    public void changeNick(MouseEvent mouseEvent) {
        if (!isNickSelected){
            isNickSelected = true;
            nickTextField.setText(User.currentUser.getNickname());
            nickTextField.setVisible(true);
            nickConfirm.setVisible(true);
            nickText.setVisible(false);
        } else {
            isNickSelected = false;
            nickTextField.setVisible(false);
            nickText.setVisible(true);
            nickConfirm.setVisible(false);
        }
    }

    public void confirmChangingNick(MouseEvent mouseEvent) {
        User.currentUser.setNickname(nickTextField.getText());
        isNickSelected = false;
        nickText.setText(nickTextField.getText());
        nickConfirm.setVisible(false);
        nickTextField.setVisible(false);
        nickText.setVisible(true);
        User.updateDataBase();
        User.currentUserJsonSaver();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(LaunchMenu.getStage());
        alert.setHeaderText("Change Nickname");
        alert.setContentText("Nickname changed successfully!");
        alert.showAndWait();
    }

    public void changeEmail(MouseEvent mouseEvent) {
        if (!isEmailSelected){
            isEmailSelected = true;
            emailTextField.setText(User.currentUser.getEmail());
            emailTextField.setVisible(true);
            emailConfirm.setVisible(true);
            emailText.setVisible(false);
        } else {
            isEmailSelected = false;
            emailTextField.setVisible(false);
            emailText.setVisible(true);
            emailConfirm.setVisible(false);
        }
    }

    public void confirmChangingEmail(MouseEvent mouseEvent) {
        User.currentUser.setEmail(emailTextField.getText());
        isEmailSelected = false;
        emailText.setText(emailTextField.getText());
        emailConfirm.setVisible(false);
        emailTextField.setVisible(false);
        emailText.setVisible(true);
        User.updateDataBase();
        User.currentUserJsonSaver();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(LaunchMenu.getStage());
        alert.setHeaderText("Change Email");
        alert.setContentText("Email changed successfully!");
        alert.showAndWait();
    }

    public void changeSlogan(MouseEvent mouseEvent) {
        if (!isSloganSelected){
            isSloganSelected = true;
            sloganTextField.setText(User.currentUser.getSlogan());
            sloganTextField.setVisible(true);
            sloganConfirm.setVisible(true);
            sloganText.setVisible(false);
        } else {
            isSloganSelected = false;
            sloganTextField.setVisible(false);
            sloganText.setVisible(true);
            sloganConfirm.setVisible(false);
        }
    }

    public void confirmChangingSlogan(MouseEvent mouseEvent) {
        User.currentUser.setSlogan(sloganTextField.getText());
        isSloganSelected = false;
        sloganText.setText(sloganTextField.getText());
        sloganConfirm.setVisible(false);
        sloganTextField.setVisible(false);
        sloganText.setVisible(true);
        User.updateDataBase();
        User.currentUserJsonSaver();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(LaunchMenu.getStage());
        alert.setHeaderText("Change Slogan");
        alert.setContentText("Slogan changed successfully!");
        alert.showAndWait();
    }

    public void backToMain(MouseEvent mouseEvent) throws Exception{
        new MainMenu().start(LaunchMenu.getStage());
    }

    public void toAvatarChangingMenu(MouseEvent mouseEvent) throws Exception{
        new AvatarMenu().start(LaunchMenu.getStage());
    }

    public void deleteSlogan(MouseEvent mouseEvent) {
        sloganText.setText("Empty!");
        User.currentUser.setSlogan(null);
        User.updateDataBase();
        User.currentUserJsonSaver();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(LaunchMenu.getStage());
        alert.setHeaderText("Change Slogan");
        alert.setContentText("Slogan removed successfully!");
        alert.showAndWait();
    }
}
