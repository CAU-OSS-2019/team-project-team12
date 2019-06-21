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

public class KeywordsController implements Initializable {
    @FXML private TextField inputString;
    @FXML private Button add;
    @FXML private Button delete;
    @FXML private ListView<String> keywordsList;
    DataTable dataTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        add.setOnAction(event -> addKeyword());
        delete.setOnAction(event -> deleteKeyword(true));
        setKeywordsList();
    }

    private void setKeywordsList() {
        dataTable = new DataTable();
        ArrayList<String> myArrayList = new ArrayList<>();

        for(int i=0; i<65536; i++)
        {
            for(int j=0; j<dataTable.getBWTable()[i].size(); j++)
            {
                myArrayList.add(dataTable.getBWTable()[i].get(j));
            }
        }
        keywordsList.setItems(FXCollections.observableArrayList(myArrayList));
    }

    private void addKeyword() {
    	if (dataTable.AddToBWTable(inputString.getText())) {
    		inputSuccessWindow("delete Keyword succeed");
    	}
    	else {
    		inputFailWindow("Delete Keyword failed");
    	}
    }
    private void deleteKeyword(boolean isExist) {
    	if (dataTable.DeleteFromBWTable(inputString.getText())) {
    		inputSuccessWindow("delete Keyword succeed");
    	}
    	else {
    		inputFailWindow("Delete Keyword failed");
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