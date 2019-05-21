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

public class KeywordsController implements Initializable {
    @FXML private TextField inputString;
    @FXML private Button add;
    @FXML private Button delete;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        add.setOnAction(event -> addKeyword(true));
        delete.setOnAction(event -> deleteKeyword(true));

    }

    private void addKeyword(boolean isExist) {
        if(isExist) inputFailWindow("Add Keyword failed");
        else inputSuccessWindow("Add Keyword succeed");
    }
    private void deleteKeyword(boolean isExist) {
        if(isExist) inputSuccessWindow("delete Keyword succeed");
        else inputFailWindow("Delete Keyword failed");
    }

    private void inputFailWindow(String whatOperation) {
        try {
            Pane newPane = FXMLLoader.load(getClass().getResource("inputFail.fxml"));
            Scene newScene = new Scene(newPane);
            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.setTitle(whatOperation);
            newStage.show();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    private void inputSuccessWindow(String whatOperation) {
        try {
            Pane newPane = FXMLLoader.load(getClass().getResource("inputSuccess.fxml"));
            Scene newScene = new Scene(newPane);
            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.setTitle(whatOperation);
            newStage.show();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}