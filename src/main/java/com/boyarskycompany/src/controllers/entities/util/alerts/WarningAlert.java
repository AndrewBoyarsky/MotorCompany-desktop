package com.boyarskycompany.src.controllers.entities.util.alerts;

import javafx.stage.StageStyle;

/**
 * Created by zandr on 22.10.2016.
 */
public class WarningAlert extends AbstractAlert {
    public WarningAlert(String contentName) {
        super(AlertType.WARNING, contentName, "warningTitleText", "warningHeaderText");
        initStyle(StageStyle.UTILITY);
    }

    public WarningAlert() {
        this("");
    }
}
