package UI;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import twitch.DDokDDokTwitch;

public class BanBoxController implements Initializable {
    @FXML Button banButton, timeOutButton, closeButton;
    @FXML Label userLabel;

    private ChatDataProperty selectedUser;
    private String platform;
    private DDokDDokTwitch ddokddok;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userLabel.setText("닉네임 : " /*+selectedUser.getUserNickName().toString()*/);
        banButton.setOnAction(event -> banUser(selectedUser));
        timeOutButton.setOnAction(event -> timeOutUser(selectedUser));
        closeButton.setOnAction(event -> closeThisWindow());
    }

    public void setSelectedUser(ChatDataProperty input) {
        this.selectedUser = input;
    }

    public void setPlatform(String input) {
        this.platform = input;
    }

    public void setDdokddok(DDokDDokTwitch input) {
        this.ddokddok = input;
    }

    public void banUser(ChatDataProperty selected) {
        ddokddok.banUser(selected.getUserID().getValue());
        try {
            Pane newPane = FXMLLoader.load(getClass().getResource("/fxml/inputSuccess.fxml"));
            Scene newScene = new Scene(newPane);
            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.setTitle(selected.getUserNickName().getValue() + " was banned");
            newStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void timeOutUser(ChatDataProperty selected) {
        //ddokddok.timeOutUser(selected.getUserID().getValue());
        try {
            Pane newPane = FXMLLoader.load(getClass().getResource("/fxml/inputSuccess.fxml"));
            Scene newScene = new Scene(newPane);
            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.setTitle(selected.getUserNickName().getValue() + " was banned");
            newStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void closeThisWindow() {
        Stage oldStage = (Stage) closeButton.getScene().getWindow();
        oldStage.close();
    }
}

