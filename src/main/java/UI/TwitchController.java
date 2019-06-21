package UI;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import twitch.DDokDDokTwitch;

public class TwitchController implements Initializable {
    //3Buttons, TableView initialize.
    @FXML
    private Button keywords, urls, streamers;
    @FXML
    private TableView<ChatDataProperty> twitchTable;
    @FXML
    private TableColumn<ChatDataProperty, String> userID, nickName, chat, status;
    @FXML
    private TableView<ChatDataProperty> streamersTable;
    @FXML
    private TableColumn<ChatDataProperty, String> streamerNmae, streamerChat;
    
    private DDokDDokTwitch ddokddok;
    private String UserName;
    
    //initialize table contents, button actions.
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userID.setCellValueFactory(cellData -> cellData.getValue().getUserID());
        nickName.setCellValueFactory(cellData -> cellData.getValue().getUserNickName());
        chat.setCellValueFactory(cellData -> cellData.getValue().getChatText());
        status.setCellValueFactory(cellData -> setStatus(cellData.getValue().getIsBadword(), cellData.getValue().getIsNamed()));

        //call each corresponding window that will be shown for clicking button
        keywords.setOnAction(event -> keywordsWindow());
        urls.setOnAction(event -> urlsWindow());
        streamers.setOnAction(event-> streamersWindow());

        twitchTable.setOnMouseClicked(event -> {
            try {
                ChatDataProperty selected = twitchTable.getSelectionModel().getSelectedItem();
                banBoxWindow(selected);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        });
        /*
        banButton.setOnMouseClicked(event -> {
            ChatDataProperty selected = twitchTable.getSelectionModel().getSelectedItem();
            banUser(selected);
        });
        timeOutButton.setOnMouseClicked(event -> {
            ChatDataProperty selected = twitchTable.getSelectionModel().getSelectedItem();
            timeOutUser(selected);
        });*/
    }
    public void setUserName(String UserName) {
    	this.UserName = UserName;
    	ddokddok = new DDokDDokTwitch(UserName);
    	if (!ddokddok.connect()) {
    		failWindow();
    	}
    	twitchTable.setItems(ddokddok.getChatDataObservableList());
    }

    // show keywords window.
    public void keywordsWindow(){
        try {
        	/*URL url = getClass().getResource("/txt/keywords.txt");
            Desktop.getDesktop().edit(new File(url.getPath()));*/
            Pane newPane = FXMLLoader.load(getClass().getResource("/fxml/keywords.fxml"));
            Scene newScene = new Scene(newPane);
            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.setTitle("DDOKDDOK Chatbot keywords");
            newStage.show();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    // show urls window.
    public void urlsWindow(){
        try {
        	/*URL url = getClass().getResource("/txt/urls.txt");
            Desktop.getDesktop().edit(new File(url.getPath()));*/
            Pane newPane = FXMLLoader.load(getClass().getResource("/fxml/urls.fxml"));
            Scene newScene = new Scene(newPane);
            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.setTitle("DDOKDDOK Chatbot urls");
            newStage.show();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    //show streamers window.
    public void streamersWindow(){
        try {
        	/*URL url = getClass().getResource("/txt/streamers.txt");
            Desktop.getDesktop().edit(new File(url.getPath()));*/
            Pane newPane = FXMLLoader.load(getClass().getResource("/fxml/streamers.fxml"));
            Scene newScene = new Scene(newPane);
            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.setTitle("DDOKDDOK Chatbot streamers");
            newStage.show();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void banBoxWindow(ChatDataProperty selected) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/banBox.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene((Pane) loader.load()));
            BanBoxController banBoxController = loader.<BanBoxController>getController();;
            /*banBoxController.setPlatform("twitch");
            banBoxController.setSelectedUser(selected);*/
            newStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public StringProperty setStatus(BooleanProperty isBadword, BooleanProperty isNamed) {
        if(isBadword.getValue() == true) {
            return new SimpleStringProperty("욕설");
        }
        else if(isNamed.getValue() == true) {
            return new SimpleStringProperty("스트리머!");
        }
        else
            return new SimpleStringProperty("아무튼 이상함");
    }
    
    // show fail notice window.
    private void failWindow() {
        try {
            Pane newPane = FXMLLoader.load(getClass().getResource("/fxml/loginFail.fxml"));
            Scene newScene = new Scene(newPane);
            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.setTitle("Login Failed.");
            newStage.show();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}