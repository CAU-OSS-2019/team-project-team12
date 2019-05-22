package UI;

import chatcontrol.DataTable;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StreamersController implements Initializable {
    @FXML
    private TextField inputString;
    @FXML private Button add;
    @FXML private Button delete;
    @FXML private ListView<String> streamersList;
    DataTable dataTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        add.setOnAction(event -> addStreamer(true));
        delete.setOnAction(event -> deleteStreamer(true));
        setStreamersList();

    }

    private void setStreamersList() {
        DataTable dataTable = new DataTable();
        streamersList.setItems(FXCollections.observableArrayList(dataTable.getNamedList()));

    }

    private void addStreamer(boolean isExist) {
    	if (dataTable.AddToNamedList(inputString.getText())) {
    		inputSuccessWindow("delete URL succeed");
    	} else {
    		inputFailWindow("Delete URL failed");
    	}
    }
    private void deleteStreamer(boolean isExist) {
    	if (dataTable.DeleteFromNamedList(inputString.getText())) {
    		inputSuccessWindow("delete URL succeed");
    	} else {
    		inputFailWindow("Delete URL failed");
    	}
    }

    private void inputFailWindow(String whatOperation) {
        try {
            Pane newPane = FXMLLoader.load(getClass().getResource("/fxml/inputFail.fxml"));
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
            Pane newPane = FXMLLoader.load(getClass().getResource("/fxml/inputSuccess.fxml"));
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