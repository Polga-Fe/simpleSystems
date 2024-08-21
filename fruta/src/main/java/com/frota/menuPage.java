package com.frota;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class menuPage {
    @FXML
    private void switchToRegister() {
        App.switchToRegisterScene();
    }

    @FXML
    private void switchToShow() {
        App.switchToShowScene();
    }

    @FXML
    private void exitGame() {
        Stage stage = App.getPrimaryStage();
        stage.close();
    }
}
