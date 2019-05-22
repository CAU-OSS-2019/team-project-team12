package UI;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;

//Seongmin Java Scheduler

//Seongmin JSON Parser
import chatcontrol.ChatProc;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import chatcontrol.ChatData;
import chatcontrol.ChatProc;

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


class ScheduledJob extends TimerTask{
    private JSONObject retjsn = null;

    public void run() {
        File sourceCode = new File("src/main/java/UI/getLiveMessageList.py");
        String command = "cmd.exe /c python " + sourceCode.getAbsolutePath();
        System.out.println(command);
        try {
            Process p = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String jsn = "";
        try {
            File chatjsonfile = new File("chatdata.json");
            FileReader file_reader = new FileReader(chatjsonfile);
            int cur = 0;
            while((cur = file_reader.read())!=-1) {
                jsn = jsn + (char)cur;
            }
            file_reader.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        try {
            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject) parser.parse(jsn);
            this.retjsn = object;
        }catch(ParseException e){
            e.printStackTrace();
        }
    }

    public JSONObject getJSONFile(){
        if(retjsn != null) {
            return this.retjsn;
        }
        else{
            return null;
        }
    }
}

class JsonParsingSchedule extends TimerTask{
    JSONObject jsn;
    ArrayList<ChatData> ch;

    JsonParsingSchedule(JSONObject j, ArrayList<ChatData> ch){
        this.jsn =(JSONObject) j;
        this.ch = ch;
    }

    public void run() {
        //유저아이디, 닉네임, 챗 텍스트, 채널아이디, 라이브챗아이디
        if(jsn==null){return;}
        JSONArray items = (JSONArray) jsn.get("items");

        for(int i = 0; i< items.size(); i++){
            JSONObject res = (JSONObject) items.get(i);
            JSONObject authorDetails = (JSONObject) res.get("authorDetails");
            JSONObject snippet = (JSONObject) res.get("snippet");
            String liveChatID = (String) snippet.get("liveChatId");
            String message= (String) snippet.get("displayMessage");
            String userNick = (String) authorDetails.get("displayName");
            String channelID = (String) authorDetails.get("channelId");
            String authorChannelId = (String) snippet.get("authorChannelId");

            ChatData tmp = new ChatData(authorChannelId,userNick,message,channelID,liveChatID);
            ch.add(tmp);
        }

    }

}


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
        ChatProc proc =new ChatProc();
        ArrayList<ChatData> localChatdataArrayList = new ArrayList<ChatData>();
        JSONObject localJsonObj = readJson();

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

        ScheduledJob job = new ScheduledJob();
        Timer jobScheduler = new Timer();
        //After 5000ms pass, run jsonparser
        jobScheduler.scheduleAtFixedRate(job, 1000, 5000);

        JsonParsingSchedule parseAndAddJob = new JsonParsingSchedule(localJsonObj, localChatdataArrayList);
        Timer parseAndAddScheduler = new Timer();
        //After 6000ms pass, run
        parseAndAddScheduler.scheduleAtFixedRate(parseAndAddJob,1000,6000);
        ArrayList<ChatDataProperty> updateChatProp = makeChatData(localJsonObj);

        for(int i =0;i<updateChatProp.size();i++){
            myList.add(updateChatProp.get(i));
        }
        //Execute Python bot.py
        executeBotPythonScript();
    }
    private ArrayList<ChatDataProperty> makeChatData(JSONObject jsn) {
        ChatProc chatProc = new ChatProc();
        //유저아이디, 닉네임, 챗 텍스트, 채널아이디, 라이브챗아이디
        ArrayList<ChatData> ch = new ArrayList<ChatData>();
        ArrayList<ChatDataProperty> chatprop = new ArrayList<ChatDataProperty>();
        if (jsn == null) {
            return null;
        }
        JSONArray items = (JSONArray) jsn.get("items");

        for (int i = 0; i < items.size(); i++) {
            JSONObject res = (JSONObject) items.get(i);
            JSONObject authorDetails = (JSONObject) res.get("authorDetails");
            JSONObject snippet = (JSONObject) res.get("snippet");
            String liveChatID = (String) snippet.get("liveChatId");
            String message = (String) snippet.get("displayMessage");
            String userNick = (String) authorDetails.get("displayName");
            String channelID = (String) authorDetails.get("channelId");
            String authorChannelId = (String) snippet.get("authorChannelId");

            ChatData tmp = new ChatData(authorChannelId, userNick, message, channelID, liveChatID);
            ch.add(tmp);
        }
        for(int i=0;i<ch.size();i++){
            chatProc.doProc(ch.get(i));
            chatprop.add(new ChatDataProperty(ch.get(i)));
        }
        return chatprop;
    }
    private JSONObject readJson(){
        JSONObject retjsn = new JSONObject();
        String jsn = "";
        try {
            File chatjsonfile = new File("chatdata.json");
            FileReader file_reader = new FileReader(chatjsonfile);
            int cur = 0;
            while((cur = file_reader.read())!=-1) {
                jsn = jsn + (char)cur;
            }
            file_reader.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        try {
            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject) parser.parse(jsn);
            retjsn = object;
        }catch(ParseException e){
            e.printStackTrace();
        }
        return retjsn;
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