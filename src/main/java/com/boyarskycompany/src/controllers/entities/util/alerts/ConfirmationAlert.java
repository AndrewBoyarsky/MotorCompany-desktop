package com.boyarskycompany.src.controllers.entities.util.alerts;

import javafx.stage.StageStyle;

/**
 * Created by zandr on 17.10.2016.
 */
public class ConfirmationAlert extends AbstractAlert {
    public ConfirmationAlert(String contentName) {
        super(AlertType.CONFIRMATION, contentName, "confirmationTitleText", "confirmationHeaderText");
        initStyle(StageStyle.UTILITY);
    }

    public ConfirmationAlert() {
        this("");
    }


}
