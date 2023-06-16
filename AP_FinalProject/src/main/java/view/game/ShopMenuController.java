package view.game;

import controller.GameController;
import controller.ShopController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.enums.make_able.Food;
import model.enums.make_able.Resources;
import model.enums.make_able.Weapons;
import view.LaunchMenu;
import view.game.trade.MainTradeController;
import view.game.trade.MainTradeMenu;

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
    @FXML
    private Button buyButton;
    @FXML
    private Button sellButton;
    @FXML
    private TextField amount;



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

//        goldAmount.setText("Gold: " + GameController.getCurrentGame().getCurrentGovernment().
//                getStorageDepartment().getResourcesStorage().get(Resources.GOLD));
//        resourceAmount.setText("Resource: " + GameController.getCurrentGame().getCurrentGovernment().getStorageDepartment().foodStorage.get(Food.ALE));
        buyButton.setText("Buy: " + Food.ALE.getPrice());
        sellButton.setText("Sell: " + Food.ALE.getPrice()*4/5);
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
                //resourceAmount.setText("Resource: " + getAmountLeft(shopMaterial.getName()));
                setPrice(shopMaterial.getName());
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
                //resourceAmount.setText("Resource: " + getAmountLeft(shopMaterial.getName()));
                setPrice(shopMaterial.getName());
                if (shopMaterial.getName().equals("metal armour") || shopMaterial.getName().equals("leather armour"))
                    itemName.setLayoutX(380);
                else
                    itemName.setLayoutX(430);
                break;
            }
        }
    }

//    public double getAmountLeft(String name){
//        for (Food food : Food.values()){
//            if (food.getName().equals(name))
//                return food.getAmount(GameController.getCurrentGame().getCurrentGovernment());
//        }
//
//        for (Resources resources : Resources.values()){
//            if (resources.getName().equals(name))
//                return resources.getAmount(GameController.getCurrentGame().getCurrentGovernment());
//        }
//
//        for (Weapons weapons : Weapons.values()){
//            if (weapons.getName().equals(name))
//                return weapons.getAmount(GameController.getCurrentGame().getCurrentGovernment());
//        }
//        return 0.0;
//    }

    public void setPrice(String name){
        for (Food food : Food.values()){
            if (food.getName().equals(name)){
                buyButton.setText("Buy: " + food.getPrice());
                sellButton.setText("Sell: " + food.getPrice()*4/5);
                return;
            }

        }

        for (Resources resources : Resources.values()){
            if (resources.getName().equals(name)){
                buyButton.setText("Buy: " + resources.getPrice());
                sellButton.setText("Sell: " + resources.getPrice()*4/5);
                return;
            }
        }

        for (Weapons weapons : Weapons.values()){
            if (weapons.getName().equals(name)){
                buyButton.setText("Buy: " + weapons.getPrice());
                sellButton.setText("Sell: " + weapons.getPrice()*4/5);
                return;
            }
        }
    }

    public void BackToGame(MouseEvent mouseEvent) {
    }

    public void OntoTradeCenter(MouseEvent mouseEvent) throws Exception{
        new MainTradeMenu().start(LaunchMenu.getStage());
    }

    public void buyItem(MouseEvent mouseEvent) {
        String name = "";
        for (ShopMaterial shopMaterial : ShopMaterial.values()){
            if (shopMaterial.getNumber() == itemIndex)
                name = shopMaterial.getName();
        }
        int wantedAmount = Integer.parseInt(amount.getText());
        String response = ShopController.buyItems(name, wantedAmount);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(LaunchMenu.getStage());
        alert.setHeaderText("Buying");
        alert.setContentText(response);
        alert.showAndWait();

        for (ShopMaterial shopMaterial : ShopMaterial.values()){
            if (shopMaterial.getNumber() == itemIndex) {
                //resourceAmount.setText("Resource: " + getAmountLeft(shopMaterial.getName()));
                break;
            }
        }

        // goldAmount.setText("Gold: " + GameController.getCurrentGame().getCurrentGovernment().
        // getStorageDepartment().getResourcesStorage().get(Resources.GOLD));


    }

    public void sellItem(MouseEvent mouseEvent) {
        String name = "";
        for (ShopMaterial shopMaterial : ShopMaterial.values()){
            if (shopMaterial.getNumber() == itemIndex)
                name = shopMaterial.getName();
        }

        int wantedAmount = Integer.parseInt(amount.getText());
        String response = ShopController.sellItems(name, wantedAmount);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(LaunchMenu.getStage());
        alert.setHeaderText("Selling");
        alert.setContentText(response);
        alert.showAndWait();


        for (ShopMaterial shopMaterial : ShopMaterial.values()){
            if (shopMaterial.getNumber() == itemIndex) {
                //resourceAmount.setText("Resource: " + getAmountLeft(shopMaterial.getName()));
                break;
            }
        }

        // goldAmount.setText("Gold: " + GameController.getCurrentGame().getCurrentGovernment().
        // getStorageDepartment().getResourcesStorage().get(Resources.GOLD));
    }

    //TODO: commented lines have to be uncommented

}
