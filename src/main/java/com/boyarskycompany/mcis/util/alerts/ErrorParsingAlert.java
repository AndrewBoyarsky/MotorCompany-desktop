package com.boyarskycompany.mcis.util.alerts;

import com.boyarskycompany.mcis.run.MainApplicationEntry;
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

    public ErrorParsingAlert(String contentPropertyResourceBundleName, String headerPropertyResourceBundleName, String titlePropertyResourceBundleName) {
        this(contentPropertyResourceBundleName, headerPropertyResourceBundleName);
        setTitle(MainApplicationEntry.getLanguageResourceBundle().getString(titlePropertyResourceBundleName));
    }
}
