package view.game;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ShopMenuController implements Initializable {

    @FXML
    private Rectangle itemImage;

    @FXML
    private Rectangle leftArrow;
    @FXML
    private Rectangle rightArrow;

    private int itemIndex;

    @FXML
    private Label itemName;
    @FXML
    private Text resourceAmount;
    @FXML
    private Text goldAmount;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        itemIndex = 1;
        itemName.setText("ale");

        itemImage.setFill(new ImagePattern(
                new Image(ShopMenu.class.getResource("/images/shop/ale.png").toString())));
        leftArrow.setFill(new ImagePattern
                (new Image(ShopMenu.class.getResource("/images/shop/leftArrow.png").toString())));

        rightArrow.setFill(new ImagePattern(new Image(ShopMenu.class.
                getResource("/images/shop/rightArrow.png").toString())));
    }

    public void moveItemToLeft(MouseEvent mouseEvent) {
        if (itemIndex == 1)
            itemIndex = 20;
        else
            itemIndex--;
        String link = "";
        for (ShopMaterial shopMaterial : ShopMaterial.values()){
            if (shopMaterial.getNumber() == itemIndex) {
                link = shopMaterial.getLink();
                itemImage.setFill(new ImagePattern(new Image(link)));
                itemName.setText(shopMaterial.getName());
                if (shopMaterial.getName().equals("metal armour") || shopMaterial.getName().equals("leather armour"))
                    itemName.setLayoutX(380);
                 else
                     itemName.setLayoutX(430);
                break;
            }
        }
    }

    public void moveItemToRight(MouseEvent mouseEvent) {
        if (itemIndex == 20)
            itemIndex = 1;
        else
            itemIndex++;
        String link = "a";
        for (ShopMaterial shopMaterial : ShopMaterial.values()){
            if (shopMaterial.getNumber() == itemIndex) {
                link = shopMaterial.getLink();
                itemImage.setFill(new ImagePattern(new Image(link)));
                itemName.setText(shopMaterial.getName());
                if (shopMaterial.getName().equals("metal armour") || shopMaterial.getName().equals("leather armour"))
                    itemName.setLayoutX(380);
                else
                    itemName.setLayoutX(430);
                break;
            }
        }
    }
}
