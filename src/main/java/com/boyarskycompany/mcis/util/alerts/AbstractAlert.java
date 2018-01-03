package com.boyarskycompany.mcis.util.alerts;

import com.boyarskycompany.mcis.run.MainApplicationEntry;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.ResourceBundle;

/**
 * Created by zandr on 22.10.2016.
 */
public abstract class AbstractAlert extends Alert {

    private ResourceBundle res = MainApplicationEntry.getLanguageResourceBundle();
    private ButtonType confirmButton = new ButtonType(res.getString("yesButton"), ButtonBar.ButtonData.YES);
    private ButtonType cancelButton = new ButtonType(res.getString("noButton"), ButtonBar.ButtonData.NO);
    protected ResourceBundle getRes() {
        return res;
    }

    public AbstractAlert(AlertType alertType) {
        super(alertType);
        getButtonTypes().clear();
        getButtonTypes().addAll(confirmButton, cancelButton);
    }

    public AbstractAlert(AlertType alertType, String contentTextName, String titleName, String headerName) {
        this(alertType);
        setContentText(res.getString(contentTextName));
        setHeaderText(res.getString(headerName));
        setTitle(res.getString(titleName));
    }
    public AbstractAlert(AlertType alertType, String titleName, String headerName) {
        this(alertType, "", titleName, headerName);
    }

}
