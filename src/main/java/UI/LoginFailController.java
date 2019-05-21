package UI;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LoginFailController implements Initializable {
    //Initialize closeButton in loginFail.fxml
    @FXML private Button closeButton;

    //initialize implementation for superclass
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Call handler for buttonClose
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleButtonAction(event);
            }
        });
    }

    //When buttonClose clicked, fail notice window will close.
    @FXML
    private void handleButtonAction(ActionEvent event) {

        Button tmpButton = (Button) event.getSource();
        Stage thisStage = (Stage) tmpButton.getScene().getWindow();

        thisStage.close();

    }
}
