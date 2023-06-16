package view.game.trade;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.enums.make_able.Resources;
import model.user.User;

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



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        wantedIronSelection.setVisible(false);
        wantedGhirSelection.setVisible(false);
        wantedStoneSelection.setVisible(false);
        wantedWoodSelection.setVisible(false);
        wantedAmount.setText("0");
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
        

    }


    public void selectWantedIron(MouseEvent mouseEvent) {
        if (!isWantedSelected) {
            wantedIronSelection.setVisible(true);
            isWantedSelected = true;
        } else {
            wantedIronSelection.setVisible(false);
            isWantedSelected = false;
        }
    }

    public void selectWantedStone(MouseEvent mouseEvent) {
    }

    public void selectWantedWood(MouseEvent mouseEvent) {
    }

    public void selectWantedGhir(MouseEvent mouseEvent) {
    }

    public void decreaseWantedAmount(MouseEvent mouseEvent) {
        if (Integer.parseInt(wantedAmount.getText()) < 5)
            wantedAmount.setText("0");
        else
            wantedAmount.setText(Integer.toString(Integer.parseInt(wantedAmount.getText())-5));
    }

    public void IncreaseWantedAmount(MouseEvent mouseEvent) {
        wantedAmount.setText(Integer.toString(Integer.parseInt(wantedAmount.getText())+5));
    }
}
