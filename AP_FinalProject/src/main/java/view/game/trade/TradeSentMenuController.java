package view.game.trade;

import controller.GameController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Game;
import model.Trade;
import model.enums.make_able.Resources;
import model.user.User;
import view.LaunchMenu;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TradeSentMenuController implements Initializable {

    @FXML
    private TableView<Trade> tableView;
    @FXML
    private TableColumn<Trade, String> receiver;
    @FXML
    private TableColumn<Trade, String> offered;
    @FXML
    private TableColumn<Trade, String> offeredAmount;
    @FXML
    private TableColumn<Trade, String> wanted;
    @FXML
    private TableColumn<Trade, String> wantedAmount;
    @FXML
    private TableColumn<Trade, String> message;
    @FXML
    private TableColumn<Trade, String> status;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        receiver.setCellValueFactory(new PropertyValueFactory<>("receiverName"));
        offered.setCellValueFactory(new PropertyValueFactory<>("offeredName"));
        offeredAmount.setCellValueFactory(new PropertyValueFactory<>("offeredNumber"));
        wanted.setCellValueFactory(new PropertyValueFactory<>("wantedName"));
        wantedAmount.setCellValueFactory(new PropertyValueFactory<>("wantedNumber"));
        message.setCellValueFactory(new PropertyValueFactory<>("message"));
        status.setCellValueFactory(new PropertyValueFactory<>("checkStatus"));


        tableView.getItems().setAll(parseUserList());
        customiseFactory(status);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void customiseFactory(TableColumn<Trade, String> calltypel) {
        calltypel.setCellFactory(column -> {
            return new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);


                    setText(empty ? "" : getItem().toString());
                    setGraphic(null);

                    TableRow<Trade> currentRow = getTableRow();
                    if (!isEmpty()) {
                        if (item.equals("accepted"))
                            currentRow.setStyle("-fx-background-color: #509329");
                        else
                            currentRow.setStyle("-fx-background-color: #FF5B5B");
                    }
                }
            };
        });
    }

    private ArrayList<Trade> parseUserList() {
        ArrayList<Trade> trades = new ArrayList<>();
        for (Trade trade : GameController.getCurrentGame().getAllTrades()){
            if (trade.getSender().getName().equals(GameController.currentGame.getCurrentGovernment().getOwner().getName()))
                trades.add(trade);
        }
        return trades;
    }

    public void backToTradeList(MouseEvent mouseEvent) throws Exception{
        new TradeList().start(LaunchMenu.getStage());
    }
}
