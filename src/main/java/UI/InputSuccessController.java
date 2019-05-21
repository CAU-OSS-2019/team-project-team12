package UI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class InputSuccessController implements Initializable {
    @FXML
    private Button ok;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ok.setOnAction(event -> okCliked());
    }

    private void okCliked() {
        Stage oldStage = (Stage) ok.getScene().getWindow();
        oldStage.close();
    }
}
