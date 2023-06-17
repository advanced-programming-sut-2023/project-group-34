package view.game.trade;

import controller.GameController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.Trade;
import model.enums.make_able.Resources;
import model.user.User;
import view.LaunchMenu;

import java.net.URL;
import java.util.ResourceBundle;

public class NewTradeMenuController implements Initializable {

    @FXML
    private Rectangle wantedGhir;

    @FXML
    private Rectangle wantedStone;

    @FXML
    private Rectangle wantedIron;

    @FXML
    private Rectangle wantedWood;

    @FXML
    private Rectangle wantedStoneSelection;
    @FXML
    private Rectangle wantedWoodSelection;
    @FXML
    private Rectangle wantedGhirSelection;
    @FXML
    private Rectangle wantedIronSelection;
    private boolean isWantedSelected;
    private Resources selectedWantedResource;

    public static User receiver;

    @FXML
    private TextField wantedAmount;
    @FXML
    private Rectangle plusSign;
    @FXML
    private Rectangle minusSign;
    @FXML
    private Rectangle offeredStoneSelection;
    @FXML
    private Rectangle offeredWoodSelection;
    @FXML
    private Rectangle offeredGhirSelection;
    @FXML
    private Rectangle offeredIronSelection;
    @FXML
    private Rectangle offeredGhir;

    @FXML
    private Rectangle offeredStone;

    @FXML
    private Rectangle offeredIron;

    @FXML
    private Rectangle offeredWood;
    private boolean isOfferedSelected;

    @FXML
    private Rectangle plusSign1;
    @FXML
    private Rectangle minusSign1;
    @FXML
    private TextField offeredAmount;
    private Resources selectedOfferedResource;

    @FXML
    private TextArea message;
    @FXML
    private Text tradeError;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        wantedIronSelection.setVisible(false);
        wantedGhirSelection.setVisible(false);
        wantedStoneSelection.setVisible(false);
        wantedWoodSelection.setVisible(false);
        offeredStoneSelection.setVisible(false);
        offeredIronSelection.setVisible(false);
        offeredGhirSelection.setVisible(false);
        offeredWoodSelection.setVisible(false);
        wantedAmount.setText("0");
        offeredAmount.setText("0");

        wantedIron.setFill(new ImagePattern(new
                Image(NewTradeMenu.class.getResource("/images/shop/iron.png").toString())));
        wantedWood.setFill(new ImagePattern(new
                Image(NewTradeMenu.class.getResource("/images/shop/wood.png").toString())));
        wantedGhir.setFill(new ImagePattern(new
                Image(NewTradeMenu.class.getResource("/images/shop/ghir.png").toString())));
        wantedStone.setFill(new ImagePattern(new
                Image(NewTradeMenu.class.getResource("/images/shop/stone.png").toString())));
        plusSign.setFill(new ImagePattern(new Image
                (NewTradeMenu.class.getResource("/images/plusSign.jpg").toString())));
        minusSign.setFill(new ImagePattern(new Image
                (NewTradeMenu.class.getResource("/images/minusSign.jpg").toString())));

        offeredIron.setFill(new ImagePattern(new
                Image(NewTradeMenu.class.getResource("/images/shop/iron.png").toString())));
        offeredWood.setFill(new ImagePattern(new
                Image(NewTradeMenu.class.getResource("/images/shop/wood.png").toString())));
        offeredGhir.setFill(new ImagePattern(new
                Image(NewTradeMenu.class.getResource("/images/shop/ghir.png").toString())));
        offeredStone.setFill(new ImagePattern(new
                Image(NewTradeMenu.class.getResource("/images/shop/stone.png").toString())));
        plusSign1.setFill(new ImagePattern(new Image
                (NewTradeMenu.class.getResource("/images/plusSign.jpg").toString())));
        minusSign1.setFill(new ImagePattern(new Image
                (NewTradeMenu.class.getResource("/images/minusSign.jpg").toString())));
        

    }


    public void selectWantedIron(MouseEvent mouseEvent) {
        if (!isWantedSelected) {
            wantedIronSelection.setVisible(true);
            isWantedSelected = true;
            selectedWantedResource = Resources.IRON;
        } else {
            wantedIronSelection.setVisible(false);
            isWantedSelected = false;
            selectedWantedResource = null;
        }
    }

    public void selectWantedStone(MouseEvent mouseEvent) {
        if (!isWantedSelected) {
            wantedStoneSelection.setVisible(true);
            isWantedSelected = true;
            selectedWantedResource = Resources.STONE;
        } else {
            wantedStoneSelection.setVisible(false);
            isWantedSelected = false;
            selectedWantedResource = null;
        }
    }

    public void selectWantedWood(MouseEvent mouseEvent) {
        if (!isWantedSelected) {
            wantedWoodSelection.setVisible(true);
            isWantedSelected = true;
            selectedWantedResource = Resources.WOOD;
        } else {
            wantedWoodSelection.setVisible(false);
            isWantedSelected = false;
            selectedWantedResource = null;
        }
    }

    public void selectWantedGhir(MouseEvent mouseEvent) {
        if (!isWantedSelected) {
            wantedGhirSelection.setVisible(true);
            isWantedSelected = true;
            selectedWantedResource = Resources.GHIR;
        } else {
            wantedGhirSelection.setVisible(false);
            isWantedSelected = false;
            selectedWantedResource = null;
        }
    }

    public void decreaseWantedAmount(MouseEvent mouseEvent) {
        if (Integer.parseInt(wantedAmount.getText()) < 5)
            wantedAmount.setText("0");
        else
            wantedAmount.setText(Integer.toString(Integer.parseInt(wantedAmount.getText())-5));
    }

    public void increaseWantedAmount(MouseEvent mouseEvent) {
        wantedAmount.setText(Integer.toString(Integer.parseInt(wantedAmount.getText())+5));
    }

    public void selectOfferedIron(MouseEvent mouseEvent) {
        if (!isOfferedSelected) {
            offeredIronSelection.setVisible(true);
            isOfferedSelected = true;
            selectedOfferedResource = Resources.IRON;
        } else {
            offeredIronSelection.setVisible(false);
            isOfferedSelected = false;
            selectedOfferedResource = null;
        }
    }

    public void selectOfferedStone(MouseEvent mouseEvent) {
        if (!isOfferedSelected) {
            offeredStoneSelection.setVisible(true);
            isOfferedSelected = true;
            selectedOfferedResource = Resources.IRON;
        } else {
            offeredStoneSelection.setVisible(false);
            isOfferedSelected = false;
            selectedOfferedResource = null;
        }
    }

    public void selectOfferedWood(MouseEvent mouseEvent) {
        if (!isOfferedSelected) {
            offeredWoodSelection.setVisible(true);
            isOfferedSelected = true;
            selectedOfferedResource = Resources.IRON;
        } else {
            offeredWoodSelection.setVisible(false);
            isOfferedSelected = false;
            selectedOfferedResource = null;
        }
    }

    public void selectOfferedGhir(MouseEvent mouseEvent) {
        if (!isOfferedSelected) {
            offeredWoodSelection.setVisible(true);
            isOfferedSelected = true;
            selectedOfferedResource = Resources.IRON;
        } else {
            offeredWoodSelection.setVisible(false);
            isOfferedSelected = false;
            selectedOfferedResource = null;
        }
    }

    public void decreaseOfferedAmount(MouseEvent mouseEvent) {
        if (Integer.parseInt(offeredAmount.getText()) < 5)
            offeredAmount.setText("0");
        else
            offeredAmount.setText(Integer.toString(Integer.parseInt(offeredAmount.getText())-5));
    }

    public void increaseOfferedAmount(MouseEvent mouseEvent) {
        offeredAmount.setText(Integer.toString(Integer.parseInt(offeredAmount.getText())+5));
    }

    public void backToMainTrade(MouseEvent mouseEvent) throws Exception{
        new MainTradeMenu().start(LaunchMenu.getStage());
    }

    public void makeNewTradeOffer(MouseEvent mouseEvent) {
        if (!isWantedSelected && Integer.parseInt(wantedAmount.getText()) != 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("New Trade Error");
            alert.setContentText("You are donating something so you cannot have and wanted amount");
            alert.initOwner(LaunchMenu.getStage());
            alert.showAndWait();
            return;
        }

        if (!isOfferedSelected && Integer.parseInt(offeredAmount.getText()) != 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("New Trade Error");
            alert.setContentText("You are begging people to help you so you cannot have an offered amount");
            alert.initOwner(LaunchMenu.getStage());
            alert.showAndWait();
            return;

        }

        if (message.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("New Trade Error");
            alert.setContentText("You have to send a message along with it!");
            alert.initOwner(LaunchMenu.getStage());
            alert.showAndWait();
            return;
        }

        Trade trade = new Trade(selectedWantedResource, Integer.parseInt(wantedAmount.getText()), selectedOfferedResource,
                Integer.parseInt(offeredAmount.getText()),
                GameController.currentGame.getCurrentGovernment().getOwner(), message.getText());
        trade.setReceiver(User.getUserByUsername(NewTradeMenuController.receiver.getName()));
        GameController.getPlayerByUsername(receiver.getName()).addToMyTrades(trade);
        GameController.getPlayerByUsername(receiver.getName()).putNotificationList(trade);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("New Trade");
        alert.setContentText("Trade recorded successfully!");
        alert.initOwner(LaunchMenu.getStage());
        alert.showAndWait();

    }
}
