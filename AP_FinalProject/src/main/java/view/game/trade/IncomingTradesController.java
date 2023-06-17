package view.game.trade;

import controller.GameController;
import controller.TradeController;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Game;
import model.Trade;
import model.user.User;
import org.checkerframework.checker.units.qual.A;
import view.LaunchMenu;
import view.profile.changeAvatarInScoreBoardDialog;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class IncomingTradesController implements Initializable {

    @FXML
    private TableView<Trade> tableView;
    @FXML
    private TableColumn<Trade, String> sender;
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
    private TableView<Trade> tableViewAccepted;
    @FXML
    private TableColumn<Trade, String> senderAccepted;
    @FXML
    private TableColumn<Trade, String> offeredAccepted;
    @FXML
    private TableColumn<Trade, String> offeredAmountAccepted;
    @FXML
    private TableColumn<Trade, String> wantedAccepted;
    @FXML
    private TableColumn<Trade, String> wantedAmountAccepted;
    @FXML
    private TableColumn<Trade, String> messageAccepted;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        sender.setCellValueFactory(new PropertyValueFactory<>("senderName"));
        offered.setCellValueFactory(new PropertyValueFactory<>("offeredName"));
        offeredAmount.setCellValueFactory(new PropertyValueFactory<>("offeredNumber"));
        wanted.setCellValueFactory(new PropertyValueFactory<>("wantedName"));
        wantedAmount.setCellValueFactory(new PropertyValueFactory<>("wantedNumber"));
        message.setCellValueFactory(new PropertyValueFactory<>("message"));

        tableView.getItems().setAll(parseUserList());
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        senderAccepted.setCellValueFactory(new PropertyValueFactory<>("senderName"));
        offeredAccepted.setCellValueFactory(new PropertyValueFactory<>("offeredName"));
        offeredAmountAccepted.setCellValueFactory(new PropertyValueFactory<>("offeredNumber"));
        wantedAccepted.setCellValueFactory(new PropertyValueFactory<>("wantedName"));
        wantedAmountAccepted.setCellValueFactory(new PropertyValueFactory<>("wantedNumber"));
        messageAccepted.setCellValueFactory(new PropertyValueFactory<>("message"));

        tableViewAccepted.getItems().setAll(parseUserListAccepted());
        tableViewAccepted.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);



        TableView.TableViewSelectionModel<Trade> selectionModel = tableView.getSelectionModel();
        ObservableList<Trade> selectedItems = selectionModel.getSelectedItems();

        selectedItems.addListener(new ListChangeListener<Trade>() {
            @Override
            public void onChanged(Change<? extends Trade> change) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Accept Trade");
                alert.setContentText("Are you sure you want to accept this trade?");
                alert.initOwner(LaunchMenu.getStage());
                alert.showAndWait();
                if (alert.getButtonTypes().get(0) == ButtonType.OK){
                    acceptTradeWork(change.getList().get(0));
                }
            }
        });


    }

    private ArrayList<Trade> parseUserListAccepted() {
        ArrayList<Trade> trades = new ArrayList<>();
        for (Trade trade : GameController.getCurrentGame().getAllTrades()){
            if (trade.getReceiver().equals(GameController.getCurrentGame().getCurrentGovernment().getOwner()) && trade.isAccepted())
                trades.add(trade);
        }
        return trades;
    }

    private ArrayList<Trade> parseUserList() {
        ArrayList<Trade> trades = new ArrayList<>();
        for (Trade trade : GameController.getCurrentGame().getAllTrades()){
            if (trade.getReceiver().equals(GameController.getCurrentGame().getCurrentGovernment().getOwner()) && !trade.isAccepted())
                trades.add(trade);
        }
        return trades;
    }

    public void backToTradeList(MouseEvent mouseEvent) throws Exception{
        new TradeList().start(LaunchMenu.getStage());
    }

    public void acceptTradeWork(Trade trade){
        String response = TradeController.acceptTradeItem(trade);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Accepting Trade");
        alert.setContentText(response);
        alert.initOwner(LaunchMenu.getStage());
        alert.showAndWait();
        if (alert.getButtonTypes().get(0) == ButtonType.OK){
            tableView.getItems().setAll(parseUserList());
            tableViewAccepted.getItems().setAll(parseUserListAccepted());
        }
    }
}
