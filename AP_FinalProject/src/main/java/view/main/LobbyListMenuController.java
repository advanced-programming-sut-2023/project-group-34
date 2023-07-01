package view.main;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.Lobby;
import model.user.User;
import view.LaunchMenu;
import view.game.LobbyMenu;
import view.profile.changeAvatarInScoreBoardDialog;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class LobbyListMenuController implements Initializable {

    @FXML
    private TextField lobbyName;
    @FXML
    private Button joinButton;
    @FXML
    private Text lobbyError;

    @FXML
    private TableView<Lobby> tableView;
    @FXML
    private TableColumn<Lobby, String> name;
    @FXML
    private TableColumn<Lobby, String> status;
    @FXML
    private TableColumn<Lobby, String> players;
    @FXML
    private TableColumn<Lobby, String> capacity;

    public static ArrayList<Lobby> lobbies = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lobbies.clear();
        joinButton.setVisible(false);
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        players.setCellValueFactory(new PropertyValueFactory<>("playersNames"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        status.setCellValueFactory(new PropertyValueFactory<>("capacity"));

        tableView.getItems().setAll(parseUserList());
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableView.TableViewSelectionModel<Lobby> selectionModel = tableView.getSelectionModel();
        ObservableList<Lobby> selectedItems = selectionModel.getSelectedItems();
        selectedItems.addListener(new ListChangeListener<Lobby>() {
            @Override
            public void onChanged(Change<? extends Lobby> change) {
                if (!change.getList().get(0).isPrivate()){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.initOwner(LaunchMenu.getStage());
                    alert.setHeaderText("Joining Lobby");
                    alert.setContentText("Are you sure you want to join this lobby?");
                    alert.showAndWait();
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK){
                        //TODO set currentLobby in lobbyMenu to the lobby given in lobbyName and start a new page
                        try {
                            new LobbyMenu().start(LaunchMenu.getStage());
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });



    }

    private ArrayList<Lobby> parseUserList() {
        //TODO fill in the lobbies arraylist with the information from the server
        ArrayList<Lobby> lobbiesForTable = new ArrayList<>();
        for (Lobby lobby : lobbies)
            lobbiesForTable.add(lobby);
        return lobbiesForTable;
    }

    public void lookForLobby(MouseEvent mouseEvent) {
        joinButton.setVisible(false);
        //TODO look for the lobby called in lobbyName and if it was null set text for lobbyError and return



        joinButton.setVisible(true);
    }

    public void backToMain(MouseEvent mouseEvent) throws Exception{
        new MainMenu().start(LaunchMenu.getStage());
    }

    public void updateList(MouseEvent mouseEvent) {
        //TODO update the lobbies list
        tableView.getItems().clear();
        tableView.getItems().setAll(parseUserList());
    }

    public void joinTheLobby(MouseEvent mouseEvent) throws Exception{
        //TODO set currentLobby in lobbyMenu to the lobby given in lobbyName and start a a new page
        new LobbyMenu().start(LaunchMenu.getStage());
    }
}
