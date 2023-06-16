package view.game.trade;

import controller.GameController;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.user.Password;
import model.user.User;
import view.LaunchMenu;
import view.profile.changeAvatarInScoreBoardDialog;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ChoosingTradePartnerMenuController implements Initializable {

    @FXML
    private TableColumn<User, String> name;

    @FXML
    private TableView<User> tableView;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        tableView.getItems().setAll(parseUserList());

        TableView.TableViewSelectionModel<User> selectionModel = tableView.getSelectionModel();
        ObservableList<User> selectedItems = selectionModel.getSelectedItems();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        selectedItems.addListener(new ListChangeListener<User>() {
            @Override
            public void onChanged(Change<? extends User> change) {
                try {
                    NewTradeMenuController.receiver = change.getList().get(0);
                    new NewTradeMenu().start(LaunchMenu.getStage());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private ArrayList<User> parseUserList() {
        ArrayList<User> users = new ArrayList<>();
//        for (User user : GameController.getCurrentGame().getPlayers()){
//            if (!user.equals(GameController.currentGame.getCurrentGovernment().getOwner()))
//                users.add(user);
//        }
        Password password = new Password("1234");
        User user1 = new User("ahmad", "1234", password, "hamed", "a");
        users.add(user1);
        User user2 = new User("hamed", "1234", password, "hamed", "a");
        users.add(user2);
        return users;
    }

    public void backToMainTrade(MouseEvent mouseEvent) throws Exception{
        new MainTradeMenu().start(LaunchMenu.getStage());
    }
}
