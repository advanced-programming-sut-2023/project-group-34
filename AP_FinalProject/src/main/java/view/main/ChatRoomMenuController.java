package view.main;

import com.google.gson.Gson;
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
    private Rectangle like1;
    @FXML
    private Rectangle like2;
    @FXML
    private Rectangle like3;
    @FXML
    private Rectangle like4;
    @FXML
    private Rectangle like5;
    @FXML
    private Rectangle like6;
    @FXML
    private Rectangle like7;
    @FXML
    private Rectangle like8;
    @FXML
    private Rectangle like9;
    @FXML
    private Rectangle like10;

    @FXML
    private Rectangle dislike1;
    @FXML
    private Rectangle dislike2;
    @FXML
    private Rectangle dislike3;
    @FXML
    private Rectangle dislike4;
    @FXML
    private Rectangle dislike5;
    @FXML
    private Rectangle dislike6;
    @FXML
    private Rectangle dislike7;
    @FXML
    private Rectangle dislike8;
    @FXML
    private Rectangle dislike9;
    @FXML
    private Rectangle dislike10;

    @FXML
    private Rectangle laughing1;
    @FXML
    private Rectangle laughing2;
    @FXML
    private Rectangle laughing3;
    @FXML
    private Rectangle laughing4;
    @FXML
    private Rectangle laughing5;
    @FXML
    private Rectangle laughing6;
    @FXML
    private Rectangle laughing7;
    @FXML
    private Rectangle laughing8;
    @FXML
    private Rectangle laughing9;
    @FXML
    private Rectangle laughing10;


    @FXML
    private TextField textMessage;

    @FXML
    private Button sendButton;
    private int show;

    public ArrayList<Rectangle> seens = new ArrayList<>();
    public ArrayList<Rectangle> avatars = new ArrayList<>();
    public ArrayList<Text> messages = new ArrayList<>();
    public ArrayList<Line> lines = new ArrayList<>();
    public ArrayList<Rectangle> likes = new ArrayList<>();
    public ArrayList<Rectangle> dislikes = new ArrayList<>();
    public ArrayList<Rectangle> laughing = new ArrayList<>();

    public Chat currentChat;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chats.clear();
        //TODO fill in the chats with what comes from the server;
        try {
            LaunchMenu.dataOutputStream.writeUTF("get chats");
            chats = (new Gson().fromJson(LaunchMenu.dataInputStream.readUTF() , ArrayList.class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        getLinesRead();
        getMessagesRead();
        getAvatarRectRead();
        getSeenRectRead();
        getLikesRead();
        getLaughingRead();
        getDislikesRead();
        hideEveryAvatar();
        hideEveryLine();
        hideEveryMessage();
        hideEverySeen();
        hideEveryLaughing();
        hideEveryDisLike();
        hideEveryLike();
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
        lines.add(line2);
        lines.add(line1);
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
        messages.add(message2);
        messages.add(message3);
        messages.add(message1);
        messages.add(message4);
        messages.add(message5);
        messages.add(message6);
        messages.add(message7);
        messages.add(message8);
        messages.add(message9);
        messages.add(message10);
    }
    public void getLikesRead(){
        likes.add(like1);
        likes.add(like2);
        likes.add(like3);
        likes.add(like4);
        likes.add(like5);
        likes.add(like6);
        likes.add(like7);
        likes.add(like8);
        likes.add(like9);
        likes.add(like10);
    }
    public void getDislikesRead(){
        dislikes.add(dislike1);
        dislikes.add(dislike2);
        dislikes.add(dislike3);
        dislikes.add(dislike4);
        dislikes.add(dislike5);
        dislikes.add(dislike6);
        dislikes.add(dislike7);
        dislikes.add(dislike8);
        dislikes.add(dislike9);
        dislikes.add(dislike10);
    }

    public void getLaughingRead(){
        laughing.add(laughing1);
        laughing.add(laughing2);
        laughing.add(laughing3);
        laughing.add(laughing4);
        laughing.add(laughing5);
        laughing.add(laughing6);
        laughing.add(laughing7);
        laughing.add(laughing8);
        laughing.add(laughing9);
        laughing.add(laughing10);

    }

    public void hideEveryLike(){
        like1.setVisible(false);
        like2.setVisible(false);
        like3.setVisible(false);
        like4.setVisible(false);
        like5.setVisible(false);
        like6.setVisible(false);
        like7.setVisible(false);
        like8.setVisible(false);
        like9.setVisible(false);
        like10.setVisible(false);
    }

    public void hideEveryDisLike(){
        dislike1.setVisible(false);
        dislike2.setVisible(false);
        dislike3.setVisible(false);
        dislike4.setVisible(false);
        dislike5.setVisible(false);
        dislike6.setVisible(false);
        dislike7.setVisible(false);
        dislike8.setVisible(false);
        dislike9.setVisible(false);
        dislike10.setVisible(false);
    }

    public void hideEveryLaughing(){
        laughing1.setVisible(false);
        laughing2.setVisible(false);
        laughing3.setVisible(false);
        laughing4.setVisible(false);
        laughing5.setVisible(false);
        laughing6.setVisible(false);
        laughing7.setVisible(false);
        laughing8.setVisible(false);
        laughing9.setVisible(false);
        laughing10.setVisible(false);
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
        hideEveryLaughing();
        hideEveryDisLike();
        hideEveryLike();
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
//                if (!chat.getMessages().get(i).getSender().equals(User.currentUser))
//                    chat.getMessages().get(i).setSeen(true);

                if (chat.getMessages().get(i).isLiked()){
                    likes.get(counter).setFill(new ImagePattern(new Image(ChatRoomMenu.class.getResource("/images/like.jpg").toString())));
                    likes.get(counter).setVisible(true);
                }
                if (chat.getMessages().get(i).isDisliked()){
                    dislikes.get(counter).setFill(new ImagePattern(new Image(ChatRoomMenu.class.getResource("/images/dislike.jpg").toString())));
                    dislikes.get(counter).setVisible(true);
                }
                if (chat.getMessages().get(i).isLaughed()){
                    laughing.get(counter).setFill(new ImagePattern(new Image(ChatRoomMenu.class.getResource("/images/laugh.jpg").toString())));
                    laughing.get(counter).setVisible(true);
                }

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
//                if (!chat.getMessages().get(i).getSender().equals(User.currentUser))
//                    chat.getMessages().get(i).setSeen(true);

                if (chat.getMessages().get(i).isLiked()){
                    likes.get(counter).setFill(new ImagePattern(new Image(ChatRoomMenu.class.getResource("/images/like.jpg").toString())));
                    likes.get(counter).setVisible(true);
                }
                if (chat.getMessages().get(i).isDisliked()){
                    dislikes.get(counter).setFill(new ImagePattern(new Image(ChatRoomMenu.class.getResource("/images/dislike.jpg").toString())));
                    dislikes.get(counter).setVisible(true);
                }
                if (chat.getMessages().get(i).isLaughed()){
                    laughing.get(counter).setFill(new ImagePattern(new Image(ChatRoomMenu.class.getResource("/images/laugh.jpg").toString())));
                    laughing.get(counter).setVisible(true);
                }
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
        if (message == null){
            openReactionPanel(mouseEvent.getSceneY());
            return;
        }


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

    public void openReactionPanel(double y) throws IOException {
        Stage stage = new Stage();
        URL url = ProfileMenu.class.getResource("/FXML/messageOptions.fxml");
        Pane pane = FXMLLoader.load(url);
        Scene scene = new Scene(pane, 300, 200);
        int index = (int) (((y - 150)/53)) + show - 10;

        Rectangle like = new Rectangle();
        Rectangle dislike = new Rectangle();
        Rectangle laugh = new Rectangle();
        like.setWidth(45);
        like.setHeight(45);
        dislike.setWidth(45);
        dislike.setHeight(45);
        laugh.setWidth(45);
        laugh.setHeight(45);

        makeRectangle(pane, like, 60, 60, "like", index);
        makeRectangle(pane, dislike, 120, 60, "dislike", index);
        makeRectangle(pane, laugh, 180, 60, "laugh", index);


        stage.setScene(scene);
        stage.initOwner(LaunchMenu.getStage());
        stage.show();
    }

    public void makeRectangle(Pane pane, Rectangle rectangle, float x, float y, String name, int index){
        rectangle.setX(x);
        rectangle.setY(y);
        rectangle.setFill(new ImagePattern(new Image(ChatRoomMenu.class.getResource("/images/"+name+".jpg").toString())));
        rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (name.equals("like"))
                    currentChat.getMessages().get(index).setLiked(true);
                else if (name.equals("dislike"))
                    currentChat.getMessages().get(index).setDisliked(true);
                else
                    currentChat.getMessages().get(index).setLaughed(true);
                displayMessages(currentChat);
            }
        });
        pane.getChildren().add(rectangle);
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

    public void backToMain(MouseEvent mouseEvent) throws Exception{
        new MainMenu().start(LaunchMenu.getStage());
    }
}
