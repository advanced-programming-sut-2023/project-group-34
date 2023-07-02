package view.main;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
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
import view.game.LobbyMenuController;
import view.profile.changeAvatarInScoreBoardDialog;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
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
        selectedItems.addListener((ListChangeListener<Lobby>) change -> {
            if (!change.getList().get(0).isPrivate()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.initOwner(LaunchMenu.getStage());
                alert.setHeaderText("Joining Lobby");
                alert.setContentText("Are you sure you want to join this lobby?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    try {
                        LaunchMenu.dataOutputStream.writeUTF("get lobby -id " + change.getList().get(0).getID());
                        LobbyMenuController.currentLobby = new Gson().fromJson(LaunchMenu.dataInputStream.readUTF(), Lobby.class);
                        LaunchMenu.dataOutputStream.writeUTF("join lobby -id " + LobbyMenuController.currentLobby.getID() + " -username " + User.currentUser.getName());
                        new LobbyMenu().start(LaunchMenu.getStage());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });


    }

    public void setCurrentLobbyWithLobbyName() {
        try {
            LaunchMenu.dataOutputStream.writeUTF("get lobby -id " + lobbyName.getText());
            LobbyMenuController.currentLobby = new Gson().fromJson(LaunchMenu.dataInputStream.readUTF(), Lobby.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ArrayList<Lobby> parseUserList() {
        try {
            LaunchMenu.dataOutputStream.writeUTF("get lobbies");
            Type type = new TypeToken<ArrayList<Lobby>>() {
            }.getType();
            lobbies = new Gson().fromJson(LaunchMenu.dataInputStream.readUTF(), type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ArrayList<>(lobbies);
    }

    public void lookForLobby(MouseEvent mouseEvent) {
        lobbyError.setText("");
        joinButton.setVisible(false);
        setCurrentLobbyWithLobbyName();
        if (LobbyMenuController.currentLobby == null) {
            lobbyError.setText("No lobby found!");
        }
        joinButton.setVisible(true);
    }

    public void backToMain(MouseEvent mouseEvent) throws Exception {
        new MainMenu().start(LaunchMenu.getStage());
    }

    public void updateList(MouseEvent mouseEvent) {
        tableView.getItems().clear();
        tableView.getItems().setAll(parseUserList());
    }

    public void joinTheLobby(MouseEvent mouseEvent) throws Exception {
        setCurrentLobbyWithLobbyName();
        LaunchMenu.dataOutputStream.writeUTF("join lobby -id " + LobbyMenuController.currentLobby.getID() + " -username " + User.currentUser.getName());
        new LobbyMenu().start(LaunchMenu.getStage());
    }
}
