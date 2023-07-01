package view.main;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.friendshiprequest.FriendshipRequest;
import model.friendshiprequest.RequestTypes;
import model.user.User;
import view.LaunchMenu;
import view.profile.changeAvatarInScoreBoardDialog;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class FriendshipMenuController implements Initializable {
    @FXML
    private TextField username;
    @FXML
    private Text usernameError;
    @FXML
    private Rectangle avatar;
    @FXML
    private Label displayUsername;
    @FXML
    private Label displayNick;
    @FXML
    private Label displaySlogan;
    @FXML
    private Label displayScore;
    @FXML
    private Button acceptButton;
    @FXML
    private Label displayEmail;

    @FXML
    private TableView<FriendshipRequest> tableViewSent;
    @FXML
    private TableColumn<FriendshipRequest, String> receiver;
    @FXML
    private TableColumn<FriendshipRequest, String> status1;

    @FXML
    private TableView<FriendshipRequest> tableViewReceived;
    @FXML
    private TableColumn<FriendshipRequest, String> sender;
    @FXML
    private TableColumn<FriendshipRequest, String> status2;


    public static ArrayList<FriendshipRequest> requests = new ArrayList<>();


    public void initialize(URL url, ResourceBundle resourceBundle) {
        //TODO fills the requests with the data from server
        requests.clear();

        avatar.setVisible(false);
        displayUsername.setVisible(false);
        displayNick.setVisible(false);
        displaySlogan.setVisible(false);
        displayScore.setVisible(false);
        displayEmail.setVisible(false);
        acceptButton.setVisible(false);

        receiver.setCellValueFactory(new PropertyValueFactory<>("name"));
        status1.setCellValueFactory(new PropertyValueFactory<>("status"));

        tableViewSent.getItems().setAll(parseSentList());

        sender.setCellValueFactory(new PropertyValueFactory<>("name"));
        status2.setCellValueFactory(new PropertyValueFactory<>("status"));

        tableViewReceived.getItems().setAll(parseReceivedList());

        TableView.TableViewSelectionModel<FriendshipRequest> selectionModel = tableViewReceived.getSelectionModel();
        ObservableList<FriendshipRequest> selectedItems = selectionModel.getSelectedItems();

        selectedItems.addListener(new ListChangeListener<FriendshipRequest>() {
            @Override
            public void onChanged(Change<? extends FriendshipRequest> change) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Accepting Request");
                alert.setContentText("Do you want to accept this request?");
                alert.initOwner(LaunchMenu.getStage());
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    change.getList().get(0).setRequestType(RequestTypes.ACCEPTED);
                    tableViewReceived.getItems().clear();
                    //TODO arshia should check this because it is causing an error but I don't know if it is necessary to deal with
                    tableViewReceived.getItems().addAll(parseReceivedList());
                    //TODO tell server that this request was accepted
                }
                else {
                    change.getList().get(0).setRequestType(RequestTypes.DENIED);
                    tableViewReceived.getItems().clear();
                    tableViewReceived.getItems().addAll(parseReceivedList());
                    //TODO tell server that this request was denied
                }
            }
        });
    }

    private ArrayList<FriendshipRequest> parseSentList() {
        ArrayList<FriendshipRequest> sent = new ArrayList<>();
        for (FriendshipRequest friendshipRequest : requests){
            if (friendshipRequest.getSender().getName().equals(User.currentUser.getName()))
                sent.add(friendshipRequest);
        }
        return sent;
    }

    private ArrayList<FriendshipRequest> parseReceivedList() {
        ArrayList<FriendshipRequest> received = new ArrayList<>();
        for (FriendshipRequest friendshipRequest : requests){
            if (friendshipRequest.getReceiver().getName().equals(User.currentUser.getName()))
                received.add(friendshipRequest);
        }
        return received;
    }

    public void showUserInfo(MouseEvent mouseEvent) {
        avatar.setVisible(false);
        displayUsername.setVisible(false);
        displayNick.setVisible(false);
        displaySlogan.setVisible(false);
        displayScore.setVisible(false);
        displayEmail.setVisible(false);
        //TODO get the text from username and bring the user
        User user = null;
        //TODO if the user does not exist set an error in usernameError and keep the fields invisible


        avatar.setVisible(true);
        displayUsername.setVisible(true);
        displayNick.setVisible(true);
        displaySlogan.setVisible(true);
        displayScore.setVisible(true);
        displayEmail.setVisible(true);
        acceptButton.setVisible(true);
        avatar.setFill(new ImagePattern(new Image(user.getAvatarLink())));
        displayUsername.setText(displayUsername.getText() + user.getName());
        displayNick.setText(displayNick.getText() + user.getNickname());
        displaySlogan.setText(displaySlogan.getText() + user.getSlogan());
        displayScore.setText(displayScore.getText() + user.getScore());
    }

    public void sendFriendRequest(MouseEvent mouseEvent) {
        //TODO send request to someone and add that request to the servers list, also the table showing sent requests has to be updated
    }

    public void backToMain(MouseEvent mouseEvent) throws Exception{
        new MainMenu().start(LaunchMenu.getStage());
    }
}
