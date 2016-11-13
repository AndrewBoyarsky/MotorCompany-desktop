package com.boyarskycompany.src.controllers.entities.util.alerts;

import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Created by zandr on 17.10.2016.
 */
public class ConfirmationAlert extends AbstractAlert {
    public ConfirmationAlert(String contentName) {
        super(AlertType.CONFIRMATION, contentName, "confirmationTitleText", "confirmationHeaderText");
        ((Stage) getDialogPane().getScene().getWindow()).getIcons().add(new Image("images/confirmIcon.png"));

    }

    public ConfirmationAlert() {
        this("");
    }


}
