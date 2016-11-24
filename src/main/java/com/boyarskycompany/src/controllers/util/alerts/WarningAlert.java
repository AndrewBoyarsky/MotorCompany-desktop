package com.boyarskycompany.src.controllers.util.alerts;

import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Created by zandr on 22.10.2016.
 */
public class WarningAlert extends AbstractAlert {
    public WarningAlert(String contentName) {
        super(AlertType.WARNING, contentName, "warningTitleText", "warningHeaderText");
        ((Stage) getDialogPane().getScene().getWindow()).getIcons().add(new Image("images/warningIcon.png"));

    }

    public WarningAlert() {
        this("");
    }
}
