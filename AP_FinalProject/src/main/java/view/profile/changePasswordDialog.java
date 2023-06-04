package view.profile;

import controller.UserController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.enums.Validations;
import model.user.User;
import view.starter.CaptchaImages;
import view.starter.SecurityAndCaptchaRegister;

public class changePasswordDialog extends Dialog<ButtonType> {
    private TextField oldPass;
    private Text oldError;
    private TextField newPass;
    private Text newError;
    private TextField captcha;
    private Text captchaError;
    private TextField confirmation;
    private Text confirmError;
    private Rectangle rectangle;
    private int randNum;
    public changePasswordDialog(){
        super();
        this.setTitle("Password Change");
        this.setResizable(true);
        buildUI();
    }

    private void buildUI() {
        Pane pane = createPane();
        getDialogPane().setContent(pane);
        Node image1 = new ImageView(new Image(changePasswordDialog.class.getResource("/images/lock.png").toString()));
        getDialogPane().setGraphic(image1);
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        Button button = (Button) getDialogPane().lookupButton(ButtonType.OK);
        button.addEventFilter(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!validateDialog()){
                    actionEvent.consume();
                } else {
                    User.currentUser.getPassword().setPasswordName(newPass.getText());
                    User.currentUser.setCurrentPassword(newPass.getText());
                    User.currentUserJsonSaver();
                    User.updateDataBase();

                }
            }
        });


    }

    public Pane createPane(){
        VBox content = new VBox(10);
        content.setMinWidth(300);
        Label oldPassLabel = new Label("Old Password:");
        Label newPassLabel = new Label("New Password:");
        Label confirmLabel = new Label("Confirmation:");
        Label captchaLabel = new Label("Captcha:");
        this.oldError = new Text();
        this.newError = new Text();
        this.confirmError = new Text();
        this.captchaError = new Text();
        oldError.setStyle("-fx-fill: red");
        newError.setStyle("-fx-fill: red");
        confirmError.setStyle("-fx-fill: red");
        captchaError.setStyle("-fx-fill: red");
        this.oldPass = new TextField();
        this.newPass = new TextField();
        this.confirmation = new TextField();
        this.captcha = new TextField();
        newPass.textProperty().addListener((observable, oldText, newText)->{
            if (newText.length() < 6) {
                newError.setText("Password needs to be more than 6 characters");
                return;
            } else if (newText.length() >= 6){
                newError.setText("");
            }

            if (!Validations.check(newText, Validations.STRONG_PASSWORD))
                newError.setText("Weak Password");
            else {
                newError.setText("");
            }
        });
        HBox hBox = new HBox();
        rectangle = new Rectangle();
        rectangle.setWidth(180);
        rectangle.setHeight(40);
        setCaptcha(rectangle);
        Rectangle reload = new Rectangle();
        reload.setWidth(15);
        reload.setHeight(15);
        reload.setFill(new ImagePattern(new Image(changePasswordDialog.
                class.getResource("/images/reloadCaptcha.png").toString())));
        hBox.getChildren().addAll(rectangle, reload);
        reload.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setCaptcha(rectangle);
            }
        });
        content.getChildren().addAll(oldPassLabel, oldPass, oldError,
                newPassLabel, newPass, newError,confirmLabel, confirmation, confirmError,
                hBox, captchaLabel, captcha, captchaError);
        return content;
    }

    private void setCaptcha(Rectangle rectangle){
        randNum = UserController.randomCaptcha();
        rectangle.setFill(new ImagePattern(new Image(SecurityAndCaptchaRegister.
                class.getResource("/images/captcha/" + randNum + ".png").toString())));
    }

    private boolean validateDialog(){
        if (oldPass.getText().isEmpty()){
            oldError.setText("Empty Field!");
            setCaptcha(rectangle);
            return false;
        } else if (newPass.getText().isEmpty()){
            newError.setText("Empty Field!");
            setCaptcha(rectangle);
            return false;
        } else if (confirmation.getText().isEmpty()){
            confirmError.setText("Empty Field!");
            setCaptcha(rectangle);
            return false;
        } else if (captcha.getText().isEmpty()){
            captchaError.setText("Empty Field!");
            setCaptcha(rectangle);
            return false;
        } else if (!oldPass.getText().equals(User.currentUser.getCurrentPassword())){
            oldError.setText("Wrong Password");
            setCaptcha(rectangle);
            return false;
        } else if (!newPass.getText().equals(confirmation.getText())){
            confirmError.setText("Password and confirmation do not match!");
            setCaptcha(rectangle);
            return false;
        } else if (!checkCaptcha()){
            setCaptcha(rectangle);
            return false;
        }
        return true;
    }

    public boolean checkCaptcha(){
        for (CaptchaImages captchaImages : CaptchaImages.values()){
            if (captchaImages.getNumber() == randNum){
                if (!Integer.toString(captchaImages.getNumber()).equals(captcha.getText())) {
                    captchaError.setText("Wrong captcha");
                    setCaptcha(rectangle);
                    captcha.setText("");
                    return false;
                }
            }
        }
        captchaError.setText("");
        return true;
    }

}
