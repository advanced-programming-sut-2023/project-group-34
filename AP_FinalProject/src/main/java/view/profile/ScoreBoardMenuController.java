package view.profile;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.thoughtworks.xstream.io.binary.Token;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import model.user.User;
import view.LaunchMenu;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.ResourceBundle;

public class ScoreBoardMenuController implements Initializable {
    @FXML
    private ScrollPane tableScroll;

    @FXML
    private TableView<User> tableView;
    @FXML
    private TableColumn<User, String> username;
    @FXML
    private TableColumn<User, String> ranking;
    @FXML
    private TableColumn<User, String> score;
    @FXML
    private TableColumn<User, String> status;
    @FXML
    private TableColumn<User, String> lastSeen;
    private int tableIndex;
    Thread updater;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ranking.setCellValueFactory(new PropertyValueFactory<>("ranking"));
        username.setCellValueFactory(new PropertyValueFactory<>("name"));
        score.setCellValueFactory(new PropertyValueFactory<>("score"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        lastSeen.setCellValueFactory(new PropertyValueFactory<>("lastSeen"));
        tableView.getItems().setAll(parseUserList());
        customiseFactory(username);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableScroll.setContent(tableView);
        TableView.TableViewSelectionModel<User> selectionModel = tableView.getSelectionModel();
        ObservableList<User> selectedItems = selectionModel.getSelectedItems();
        selectedItems.addListener(new ListChangeListener<User>() {
            @Override
            public void onChanged(Change<? extends User> change) {
                Dialog<ButtonType> changePassDialog = new changeAvatarInScoreBoardDialog
                        (change.getList().get(0).getAvatarLink());
                changePassDialog.initOwner(LaunchMenu.getStage());
                Optional<ButtonType> result = changePassDialog.showAndWait();
                if (result.get() == ButtonType.CANCEL)
                    changePassDialog.close();
                else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Change Avatar");
                    alert.setContentText("Avatar changed successfully!");
                    alert.initOwner(LaunchMenu.getStage());
                    alert.showAndWait();
                }
            }
        });
        updater = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(9999);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                tableView.getItems().setAll(parseUserList());
            }
        });
        updater.start();
    }

    private ArrayList<User> parseUserList() {
        //TODO userlist
        ArrayList<User> allUsers;
        Type type = new TypeToken<ArrayList<User>>(){}.getType();
        try {
            LaunchMenu.dataOutputStream.writeUTF("get users");
            allUsers = new Gson().fromJson(LaunchMenu.dataInputStream.readUTF() , type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        User.getUsers().clear();
        User.getUsers().addAll(allUsers);
        ArrayList<User> limitedUsers = new ArrayList<>();
        User.getUsers().sort(Comparator.comparingInt(User::getScore).reversed());
        tableIndex = 0;
        for (User user : User.getUsers()) {
            if (tableIndex < 10) {
                limitedUsers.add(user);
                tableIndex++;
            }
            if (tableIndex == 10)
                break;
        }
        return limitedUsers;
    }



    public void backToProfile(MouseEvent mouseEvent) throws Exception{
        updater.interrupt();
        new ProfileMenu().start(LaunchMenu.getStage());
    }

    public void newUsersToShow(ScrollEvent scrollEvent) {
//        tableView.setOnScroll((ScrollEvent event) -> {
//            double deltaY = event.getDeltaY();
//            if (deltaY < -10 && User.getUsers().size() > 10){
//                try {
//                    users();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
    }

    public void users() throws IOException {
        //TODO userlist
        Type type = new TypeToken<ArrayList<User>>(){}.getType();
        LaunchMenu.dataOutputStream.writeUTF("get users");
        ArrayList<User> allUsers = new Gson().fromJson(LaunchMenu.dataInputStream.readUTF() , type);
        User.getUsers().clear();
        User.getUsers().addAll(allUsers);
        ArrayList<User> users = new ArrayList<>();
        int counter = 0;
        while (counter + tableIndex < User.getUsers().size() && counter < 10){
            users.add(User.getUsers().get(counter+tableIndex));
            counter++;
        }
        tableIndex += counter;
        tableView.getItems().addAll(users);
        tableView.getItems().removeAll();
    }


    private void customiseFactory(TableColumn<User, String> calltypel) {
        calltypel.setCellFactory(column -> {
            return new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? "" : getItem().toString());
                    setGraphic(null);

                    TableRow<User> currentRow = getTableRow();
                    if (!isEmpty()) {
                        if (item.equals(User.currentUser.getName()))
                            currentRow.setStyle("-fx-background-color: olive");
                        else
                            currentRow.setStyle("-fx-background-color: transparent");
                    }
                }
            };
        });
    }
}
