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

public class YoutubeController implements Initializable {
    //3Buttons, TableView initialize.
    @FXML
    private Button keywords;
    @FXML
    private Button urls;
    @FXML
    private Button streamers;
    @FXML
    private TableView<ChatDataProperty> youtubeTable;
    @FXML
    private TableColumn<ChatDataProperty, String> userID;
    @FXML
    private TableColumn<ChatDataProperty, String> nickName;
    @FXML
    private TableColumn<ChatDataProperty, String> chat;
    @FXML
    private TableColumn<ChatDataProperty, String> status;

    //initialize table contents, button actions.
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<ChatDataProperty> myList = FXCollections.observableArrayList(

        );

        userID.setCellValueFactory(cellData -> cellData.getValue().getUserID());
        nickName.setCellValueFactory(cellData -> cellData.getValue().getUserNickName());
        chat.setCellValueFactory(cellData -> cellData.getValue().getChatText());
        status.setCellValueFactory(cellData -> setStatus(cellData.getValue().getIsBadword(), cellData.getValue().getIsNamed()));
        youtubeTable.setItems(myList);

        //call each corresponding window that will be shown for clicking button
        keywords.setOnAction(event -> keywordsWindow());
        urls.setOnAction(event -> urlsWindow());
        streamers.setOnAction(event->streamersWindow());

        youtubeTable.setOnMouseClicked(event -> {
            ChatDataProperty selected = youtubeTable.getSelectionModel().getSelectedItem();
            banUser(selected);
        });


        //Execute Python bot.py
        executeBotPythonScript();
    }

    private void executeBotPythonScript(){
        File sourceCode = new File("src/main/java/UI/bot.py");
        String command = "cmd.exe /c python "+sourceCode.getAbsolutePath();
        System.out.println(command);
        try {
            Process p = Runtime.getRuntime().exec(command);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /*
    public void addList(ChatData element) {
        youtubeTable.getItems().add(new ChatDataProperty(element);
    }
    */

    // show keywords window.
    public void keywordsWindow(){
        try {
        	URL url = getClass().getResource("/txt/keywords.txt");
            Desktop.getDesktop().edit(new File(url.getPath()));
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
        	URL url = getClass().getResource("/txt/urls.txt");
            Desktop.getDesktop().edit(new File(url.getPath()));
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
        	URL url = getClass().getResource("/txt/streamers.txt");
            Desktop.getDesktop().edit(new File(url.getPath()));
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
    
    public void banUser(ChatDataProperty selected) {
        if(false) {
            try {
                Pane newPane = FXMLLoader.load(getClass().getResource("/fxml/inputSuccess.fxml"));
                Scene newScene = new Scene(newPane);
                Stage newStage = new Stage();
                newStage.setScene(newScene);
                newStage.setTitle(selected.getUserNickName().getValue() + " was banned");
                newStage.show();
                youtubeTable.getSelectionModel().clearSelection();
                youtubeTable.getItems().remove(selected);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        else {
            try {
                Pane newPane = FXMLLoader.load(getClass().getResource("/fxml/inputFail.fxml"));
                Scene newScene = new Scene(newPane);
                Stage newStage = new Stage();
                newStage.setScene(newScene);
                newStage.setTitle("Ban " + selected.getUserNickName().getValue() + " failed");
                newStage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    public StringProperty setStatus(BooleanProperty isBadword, BooleanProperty isNamed) {
        if(isBadword.getValue() == true) {
            return new SimpleStringProperty("욕설");
        }
        else if(isNamed.getValue() == true) {
            return new SimpleStringProperty("스트리머방문");
        }
        else
            return new SimpleStringProperty("아무튼 이상함");
    }
}