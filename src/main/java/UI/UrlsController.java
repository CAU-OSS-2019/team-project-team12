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

public class UrlsController implements Initializable {

    @FXML private TextField inputString;
    @FXML private Button add;
    @FXML private Button delete;
    @FXML private ListView<String> urlsList;
    DataTable dataTable;
    
    @Override
    public void initialize (URL location, ResourceBundle resources){
        add.setOnAction(event -> addUrls());
        delete.setOnAction(event -> deleteUrls(true));
        setUrlsList();
    }

    private void setUrlsList() {
       dataTable = new DataTable();
       urlsList.setItems(FXCollections.observableList(dataTable.getSafeURLList()));
    }

    private void addUrls (){
    	if (dataTable.AddToURLList(inputString.getText())) {
    		inputSuccessWindow("delete URL succeed");
    	} else {
    		inputFailWindow("Delete URL failed");
    	}
     

    }
    private void deleteUrls ( boolean isExist){
    	if (dataTable.DeleteFromURLList(inputString.getText())) {
    		inputSuccessWindow("delete URL succeed");
    	} else {
    		inputFailWindow("Delete URL failed");
    	}
    }

    private void inputFailWindow (String whatOperation){
        try {
            Pane newPane = FXMLLoader.load(getClass().getResource("/fxml/inputFail.fxml"));
            Scene newScene = new Scene(newPane);
            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.setTitle(whatOperation);
            newStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    private void inputSuccessWindow (String whatOperation) {
        try {
            Pane newPane = FXMLLoader.load(getClass().getResource("/fxml/inputSuccess.fxml"));
            Scene newScene = new Scene(newPane);
            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.setTitle(whatOperation);
            newStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}