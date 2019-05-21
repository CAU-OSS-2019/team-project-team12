package UI;

import java.io.IOException;
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

public class LoginController implements Initializable {
    //initialize Buttons, TextField in login.fxml
    @FXML private Button youtubeButton;
    @FXML private Button twitchButton;
    @FXML private TextField loginID;

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

        String login = loginID.getText();
        //platform button input checking
        switch (tmpButton.getId()) {
            //for youtube platform.
            case "youtubeButton":
                //id checking function call.
                if(idCheckYoutube(login)) {
                    //login success. close the login window and show youtube window.
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
                //id checking function call.
                if(idCheckTwitch(login)) {
                    //login success. close the login window and show twitch window.
                    oldStage.close();
                    twitchWindow();
                }
                else {
                    //login fail. show fail notice window.
                    failWindow();
                }
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
    // show youtube login window.
    private void youtubeLoginWindow() {
        try {
            Pane newPane = FXMLLoader.load(getClass().getResource("youtubeLogin.fxml"));
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
            Pane newPane = FXMLLoader.load(getClass().getResource("twitch.fxml"));
            Scene newScene = new Scene(newPane);
            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.setTitle("DDOKDDOK Chatbot for Twitch");
            newStage.show();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    // show fail notice window.
    private void failWindow() {
        try {
            Pane newPane = FXMLLoader.load(getClass().getResource("loginFail.fxml"));
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
