package UI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class YoutubeLoginController implements Initializable {
    @FXML private TextField loginAPI;
    @FXML private Button accept;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        accept.setOnAction(event -> youtubeWindow(true));
    }

    private void youtubeWindow(boolean login) {
        if(login)
        {
            Stage oldStage = (Stage) accept.getScene().getWindow();
            try {
                oldStage.close();
                Pane newPane = FXMLLoader.load(getClass().getResource("youtube.fxml"));
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
        else
        {
            try {
                Pane newPane = FXMLLoader.load(getClass().getResource("loginFail.fxml"));
                Scene newScene = new Scene(newPane);
                Stage newStage = new Stage();
                newStage.setScene(newScene);
                newStage.setTitle("Youtube Login Failed.");
                newStage.show();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

}