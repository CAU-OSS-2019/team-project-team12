package UI;

import java.io.File;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController implements Initializable {
    //initialize Buttons, TextField in login.fxml
    @FXML private Button youtubeButton;
    @FXML private Button twitchButton;
    @FXML private TextField loginID;
    
    String login;
    
    //Initialize implementation for superclass.
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Call event Handler when a button clicked.
        youtubeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleButtonAction(event);
            }
        });
        twitchButton.setOnAction(event -> handleButtonAction(event));
    }
    //handleButtonAction : event handler for each buttons.
    @FXML
    private void handleButtonAction(ActionEvent event) {

        Button tmpButton = (Button) event.getSource();//"youtubeButton" or "twitchButton".
        Stage oldStage = (Stage) tmpButton.getScene().getWindow();//login window.

        login = loginID.getText();
        //platform button input checking
        switch (tmpButton.getId()) {
            //for youtube platform.
            case "youtubeButton":
                //id checking function call.
                if(login.length() >= 8) {
                    //login success. close the login window and show youtube window.
                    try {
                        OutputStream output = new FileOutputStream("broadcastid");
                        try{
                            output.write(login.getBytes());
                        }catch (IOException ex){
                            ex.printStackTrace();
                        }
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                    executeGetAuthPythonScript();

                    oldStage.close();
                    youtubeLoginWindow();
                }
                else {
                    //login fail. show fail notice window.
                    failWindow();
                }
                break;
            //for twitch platform.
            case "twitchButton":
            	oldStage.close();
                twitchWindow();
                break;
        }
    }
    //idCheck : temp function for testing.
    public boolean idCheckTwitch(String login) {
        if(login.equals("twitch")) return true;
        else return false;
    }
    public boolean idCheckYoutube(String login) {
        if(login.equals("youtube")) return true;
        else return false;
    }

    private void executeGetAuthPythonScript(){
        File sourceCode = new File("src/main/java/UI/java_get_auth_key1.py");
        String command = "cmd.exe /c python "+sourceCode.getAbsolutePath();
        System.out.println(command);
        try {
            Process p = Runtime.getRuntime().exec(command);
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    //Pass Login URL to python Script
    public String loginIdReturnYoutube(String loginBroadcastID){
        try{
            return loginBroadcastID;
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
        return loginBroadcastID;
    }

    // show youtube login window.
    private void youtubeLoginWindow() {
        try {
            Pane newPane = FXMLLoader.load(getClass().getResource("/fxml/youtubeLogin.fxml"));
            Scene newScene = new Scene(newPane);
            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.setTitle("DDOKDDOK Chatbot for Youtube");
            newStage.show();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    // show twitch window.
    private void twitchWindow() {
        try {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/twitch.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene((Pane) loader.load()));
            newStage.setTitle("DDOKDDOK Chatbot for Twitch /" + login);
            TwitchController twitchController = loader.<TwitchController>getController();
            twitchController.setUserName(login);
            
            newStage.show();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

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
