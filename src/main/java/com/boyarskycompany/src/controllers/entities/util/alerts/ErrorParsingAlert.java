package com.boyarskycompany.src.controllers.entities.util.alerts;

import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Created by zandr on 13.11.2016.
 */
public class ErrorParsingAlert extends AbstractAlert {
    public ErrorParsingAlert(String contentPropertyResourceBundleName, String headerPropertyResourceBundleName) {
        super(AlertType.ERROR, contentPropertyResourceBundleName, "ErrorParsingTitle", headerPropertyResourceBundleName);
        ((Stage) getDialogPane().getScene().getWindow()).getIcons().add(new Image("images/errorIcon.png"));
        getButtonTypes().clear();
        getButtonTypes().add(ButtonType.OK);
        show();
    }
}
