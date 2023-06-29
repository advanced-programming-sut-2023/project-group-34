package view.main;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.messenger.Chat;
import model.messenger.Group;
import model.messenger.Message;
import model.messenger.PrivateChat;
import model.user.User;
import org.checkerframework.checker.units.qual.C;
import view.LaunchMenu;
import view.profile.ProfileMenu;
import view.profile.changeAvatarInScoreBoardDialog;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

public class ChatRoomMenuController implements Initializable {

    public static ArrayList<Chat> chats = new ArrayList<>();

    @FXML
    private TableView<Chat> tableView1;
    @FXML
    private TableColumn<Chat, String> chatName1;


    @FXML
    private Rectangle avatar1;
    @FXML
    private Rectangle avatar2;
    @FXML
    private Rectangle avatar3;
    @FXML
    private Rectangle avatar4;
    @FXML
    private Rectangle avatar5;
    @FXML
    private Rectangle avatar6;
    @FXML
    private Rectangle avatar7;
    @FXML
    private Rectangle avatar8;
    @FXML
    private Rectangle avatar9;
    @FXML
    private Rectangle avatar10;

    @FXML
    private Rectangle seen1;
    @FXML
    private Rectangle seen2;
    @FXML
    private Rectangle seen3;
    @FXML
    private Rectangle seen4;
    @FXML
    private Rectangle seen5;
    @FXML
    private Rectangle seen6;
    @FXML
    private Rectangle seen7;
    @FXML
    private Rectangle seen8;
    @FXML
    private Rectangle seen9;
    @FXML
    private Rectangle seen10;

    @FXML
    private Line line1;
    @FXML
    private Line line2;
    @FXML
    private Line line3;
    @FXML
    private Line line4;
    @FXML
    private Line line5;
    @FXML
    private Line line6;
    @FXML
    private Line line7;
    @FXML
    private Line line8;
    @FXML
    private Line line9;
    @FXML
    private Line line10;

    @FXML
    private Text message1;
    @FXML
    private Text message2;
    @FXML
    private Text message3;
    @FXML
    private Text message4;
    @FXML
    private Text message5;
    @FXML
    private Text message6;
    @FXML
    private Text message7;
    @FXML
    private Text message8;
    @FXML
    private Text message9;
    @FXML
    private Text message10;

    @FXML
    private TextField textMessage;

    @FXML
    private Button sendButton;
    private int show;

    public ArrayList<Rectangle> seens = new ArrayList<>();
    public ArrayList<Rectangle> avatars = new ArrayList<>();
    public ArrayList<Text> messages = new ArrayList<>();
    public ArrayList<Line> lines = new ArrayList<>();

    public Chat currentChat;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chats.clear();
        ArrayList<User> userPrivate = new ArrayList<>();
        userPrivate.add(User.currentUser);
        userPrivate.add(User.getUserByUsername("hame"));
        PrivateChat privateChat = new PrivateChat(userPrivate);
        Group group = new Group("Dadash");
        chats.add(privateChat);
        chats.add(group);
        privateChat.getMessages().add(new Message("Hello", User.currentUser));
        privateChat.getMessages().add(new Message("How Are you", User.currentUser));
        privateChat.getMessages().add(new Message("doing well?", User.currentUser));
        privateChat.getMessages().add(new Message("Hi", User.getUserByUsername("hame")));
        privateChat.getMessages().add(new Message("doing fine", User.getUserByUsername("hame")));
        privateChat.getMessages().add(new Message("what about you?", User.getUserByUsername("hame")));
        privateChat.getMessages().add(new Message("Nothing much", User.currentUser));
        privateChat.getMessages().add(new Message("Are you free?", User.currentUser));
        privateChat.getMessages().add(new Message("We are planning to go somewhere", User.currentUser));
        privateChat.getMessages().add(new Message("saddly no", User.getUserByUsername("hame")));
        privateChat.getMessages().add(new Message("I have a project to finish", User.getUserByUsername("hame")));
        privateChat.getMessages().add(new Message("I have a project to finish", User.getUserByUsername("hame")));
        group.getMessages().add(new Message("Hello", User.currentUser));

        //TODO fill in the chats with what comes from the server;

        getLinesRead();
        getMessagesRead();
        getAvatarRectRead();
        getSeenRectRead();
        hideEveryAvatar();
        hideEveryLine();
        hideEveryMessage();
        hideEverySeen();
        textMessage.setVisible(false);
        sendButton.setVisible(false);

        chatName1.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableView1.getItems().setAll(parseUserListPrivate());


        tableView1.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        TableView.TableViewSelectionModel<Chat> selectionModel = tableView1.getSelectionModel();
        ObservableList<Chat> selectedItems = selectionModel.getSelectedItems();

        selectedItems.addListener(new ListChangeListener<Chat>() {
            @Override
            public void onChanged(Change<? extends Chat> change) {
                show = change.getList().get(0).getMessages().size();
                currentChat = change.getList().get(0);
                displayMessages(change.getList().get(0));
            }
        });


    }

    private ArrayList<PrivateChat> parseUserListPrivate() {
        ArrayList<PrivateChat> privateChats = new ArrayList<>();
        for (Chat chat : chats)
            if (chat instanceof PrivateChat)
                privateChats.add((PrivateChat) chat);

        return privateChats;
    }

    private ArrayList<Group> parseUserListGroup() {
        ArrayList<Group> groups = new ArrayList<>();
        for (Chat chat : chats)
            if (chat instanceof Group)
                groups.add((Group) chat);

        return groups;
    }

    public void getAvatarRectRead(){
        avatars.add(avatar1);
        avatars.add(avatar2);
        avatars.add(avatar3);
        avatars.add(avatar4);
        avatars.add(avatar5);
        avatars.add(avatar6);
        avatars.add(avatar7);
        avatars.add(avatar8);
        avatars.add(avatar9);
        avatars.add(avatar10);
    }

    public void getSeenRectRead(){
        seens.add(seen1);
        seens.add(seen2);
        seens.add(seen3);
        seens.add(seen4);
        seens.add(seen5);
        seens.add(seen6);
        seens.add(seen7);
        seens.add(seen8);
        seens.add(seen9);
        seens.add(seen10);
    }

    public void getLinesRead(){
        lines.add(line1);
        lines.add(line2);
        lines.add(line3);
        lines.add(line4);
        lines.add(line5);
        lines.add(line6);
        lines.add(line7);
        lines.add(line8);
        lines.add(line9);
        lines.add(line10);
    }

    public void getMessagesRead(){
        messages.add(message1);
        messages.add(message2);
        messages.add(message3);
        messages.add(message4);
        messages.add(message5);
        messages.add(message6);
        messages.add(message7);
        messages.add(message8);
        messages.add(message9);
        messages.add(message10);
    }

    public void hideEverySeen(){
        seen1.setVisible(false);
        seen2.setVisible(false);
        seen3.setVisible(false);
        seen4.setVisible(false);
        seen5.setVisible(false);
        seen6.setVisible(false);
        seen7.setVisible(false);
        seen8.setVisible(false);
        seen9.setVisible(false);
        seen10.setVisible(false);
    }

    public void hideEveryAvatar(){
        avatar1.setVisible(false);
        avatar2.setVisible(false);
        avatar3.setVisible(false);
        avatar4.setVisible(false);
        avatar5.setVisible(false);
        avatar6.setVisible(false);
        avatar7.setVisible(false);
        avatar8.setVisible(false);
        avatar9.setVisible(false);
        avatar10.setVisible(false);
    }

    public void hideEveryMessage(){
        message1.setVisible(false);
        message2.setVisible(false);
        message3.setVisible(false);
        message4.setVisible(false);
        message5.setVisible(false);
        message6.setVisible(false);
        message7.setVisible(false);
        message8.setVisible(false);
        message9.setVisible(false);
        message10.setVisible(false);
    }

    public void hideEveryLine(){
        line1.setVisible(false);
        line2.setVisible(false);
        line3.setVisible(false);
        line4.setVisible(false);
        line5.setVisible(false);
        line6.setVisible(false);
        line7.setVisible(false);
        line8.setVisible(false);
        line9.setVisible(false);
        line10.setVisible(false);
    }


    public void displayMessages(Chat chat){
        textMessage.setVisible(true);
        sendButton.setVisible(true);
        hideEveryMessage();
        hideEverySeen();
        hideEveryLine();
        hideEveryAvatar();
        int counter = 9;
        int size = chat.getMessages().size();
        if (size >= 10){
            for (int i = show-1; i >= show-10; i--){
                messages.get(counter).setText(chat.getMessages().get(i).getSender().getName() + ": " + chat.getMessages().get(i).getMessage() + " (" + chat.getMessages().get(i).getSendTime() + ")");
                messages.get(counter).setVisible(true);
                avatars.get(counter).setFill(new ImagePattern(new Image(chat.getMessages().get(i).getSender().getAvatarLink())));
                avatars.get(counter).setVisible(true);
                lines.get(counter).setVisible(true);
                if (chat.getMessages().get(i).isSeen() && chat.getMessages().get(i).getSender().equals(User.currentUser)) {
                    seens.get(counter).setFill(new ImagePattern(new Image(ChatRoomMenu.class.getResource("/images/seen.png").toString())));
                    seens.get(counter).setVisible(true);
                } else if (chat.getMessages().get(i).getSender().equals(User.currentUser)){
                    seens.get(counter).setFill(new ImagePattern(new Image(ChatRoomMenu.class.getResource("/images/sent.png").toString())));
                    seens.get(counter).setVisible(true);
                }
                if (!chat.getMessages().get(i).getSender().equals(User.currentUser))
                    chat.getMessages().get(i).setSeen(true);
                counter--;
            }
        } else {
            for (int i = chat.getMessages().size()-1; i >= 0; i--){
                messages.get(counter).setText(chat.getMessages().get(i).getSender().getName() + ": " + chat.getMessages().get(i).getMessage() + " (" + chat.getMessages().get(i).getSendTime() + ")");
                messages.get(counter).setVisible(true);
                avatars.get(counter).setFill(new ImagePattern(new Image(chat.getMessages().get(i).getSender().getAvatarLink())));
                avatars.get(counter).setVisible(true);
                lines.get(counter).setVisible(true);
                if (chat.getMessages().get(i).isSeen() && chat.getMessages().get(i).getSender().equals(User.currentUser)) {
                    seens.get(counter).setFill(new ImagePattern(new Image(ChatRoomMenu.class.getResource("/images/seen.png").toString())));
                    seens.get(counter).setVisible(true);
                } else if (chat.getMessages().get(i).getSender().equals(User.currentUser)){
                    seens.get(counter).setFill(new ImagePattern(new Image(ChatRoomMenu.class.getResource("/images/sent.png").toString())));
                    seens.get(counter).setVisible(true);
                }
                if (!chat.getMessages().get(i).getSender().equals(User.currentUser))
                    chat.getMessages().get(i).setSeen(true);
                counter--;
            }
        }
    }

    public void sendNewMessage(MouseEvent mouseEvent) {
        if (!textMessage.getText().isEmpty()) {
            currentChat.getMessages().add(new Message(textMessage.getText(), User.currentUser));
            textMessage.setPromptText("Enter your Message");
            show++;
            displayMessages(currentChat);
        }
    }

    public void goHigher(MouseEvent mouseEvent) {
        if (show > 10) {
            show--;
            displayMessages(currentChat);
        }
    }

    public void goLower(MouseEvent mouseEvent) {
        if (show < currentChat.getMessages().size()) {
            show++;
            displayMessages(currentChat);
        }
    }

    public void privateTable(MouseEvent mouseEvent) {
        chatName1.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableView1.getItems().setAll(parseUserListPrivate());
        tableView1.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public void groupTable(MouseEvent mouseEvent) {
        chatName1.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableView1.getItems().setAll(parseUserListGroup());


        tableView1.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public void showPublicChat(MouseEvent mouseEvent) {
        //TODO implement showing public chat
    }

    public void textOptions(MouseEvent mouseEvent) throws IOException {
        Text text = (Text) mouseEvent.getSource();
        Message message = null;
        for (int i = currentChat.getMessages().size()-1; i >= 0; i--) {
            String checker = currentChat.getMessages().get(i).getSender().getName() + ": " + currentChat.getMessages().get(i).getMessage() + " (" + currentChat.getMessages().get(i).getSendTime() + ")";
            if (checker.equals(text.getText()))
                if (currentChat.getMessages().get(i).getSender().equals(User.currentUser)){
                    message = currentChat.getMessages().get(i);
                    break;
                }

        }
        if (message == null)
            return;

        Stage stage = new Stage();
        URL url = ProfileMenu.class.getResource("/FXML/messageOptions.fxml");
        Pane pane = FXMLLoader.load(url);
        Scene scene = new Scene(pane, 300, 200);
        Button delete = new Button("Delete");
        delete.setLayoutX(90);
        delete.setLayoutY(150);
        Button edit = new Button("Edit");
        edit.setLayoutX(190);
        edit.setLayoutY(150);
        TextField textField = new TextField();
        textField.setPromptText("Enter your Message");
        textField.setLayoutX(50);
        textField.setLayoutY(50);
        pane.getChildren().addAll(delete, edit, textField);
        Message finalMessage = message;
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.initOwner(stage);
                alert.setHeaderText("Deleting Message");
                alert.setContentText("Are you sure you want to delete this message?");
                alert.showAndWait();
                if (alert.getButtonTypes().get(0) == ButtonType.OK){
                    stage.close();
                    currentChat.getMessages().remove(finalMessage);
                    show--;
                    displayMessages(currentChat);
                }

            }
        });

        edit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (textField.getText().isEmpty())
                    stage.close();
                else{
                    stage.close();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.initOwner(LaunchMenu.getStage());
                    alert.setHeaderText("Editing Message");
                    alert.setContentText("Message Edited Successfully");
                    alert.showAndWait();
                    finalMessage.setMessage(textField.getText());
                    displayMessages(currentChat);
                }
            }
        });
        stage.initOwner(LaunchMenu.getStage());
        stage.setScene(scene);
        stage.show();
    }

    public void startPrivateChat(MouseEvent mouseEvent) throws IOException {
        Stage stage = new Stage();
        URL url = ProfileMenu.class.getResource("/FXML/newPrivateChat.fxml");
        Pane pane = FXMLLoader.load(url);
        Scene scene = new Scene(pane, 300, 200);
        stage.initOwner(LaunchMenu.getStage());
        stage.setScene(scene);
        stage.show();
    }

    public void startGroup(MouseEvent mouseEvent) throws IOException {
        ArrayList<User> usersToJoinTheGroup = new ArrayList<>();
        usersToJoinTheGroup.add(User.currentUser);
        Stage stage = new Stage();
        URL url = ProfileMenu.class.getResource("/FXML/newGroupChat.fxml");
        Pane pane = FXMLLoader.load(url);
        Scene scene = new Scene(pane, 300, 200);
        stage.initOwner(LaunchMenu.getStage());
        stage.setScene(scene);
        stage.show();
    }
}
