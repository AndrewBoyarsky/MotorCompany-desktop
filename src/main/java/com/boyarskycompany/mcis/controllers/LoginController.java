package com.boyarskycompany.mcis.controllers;

import com.boyarskycompany.mcis.run.MainApplicationEntry;
import com.boyarskycompany.mcis.util.User;
import com.boyarskycompany.mcis.util.UserUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import static com.boyarskycompany.mcis.run.MainApplicationEntry.getPrimaryStage;
import static com.boyarskycompany.mcis.util.UserUtil.isValid;


/**
 * Created by zandr on 16.09.2016.
 */
public class LoginController implements Initializable {
    private static final Logger log = LogManager.getLogger(LoginController.class.getName());
    private static User currentUser;
    @FXML
    private TextField userField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label infField;
    @FXML
    private Button loginButton;

    public static User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    @FXML
    private void handleLoginButton() {
        User user = new User(userField.getText(), passwordField.getText());

        if (isValid(user)) {
            log.info("User " + user + " is valid.");
            currentUser = user;
            infField.setVisible(false);
            Stage mainStage = MainApplicationEntry.getPrimaryStage();
            try {
                mainStage.setScene(new Scene(FXMLLoader.load(getClass().getClassLoader().getResource("fxml/dbConfigurationScene.fxml"), MainApplicationEntry.getLanguageResourceBundle())));
                mainStage.show();
            }
            catch (IOException e) {
                log.error("Unable to create scene!", e);
            }
        } else {
            infField.setVisible(true);
            passwordField.clear();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getPrimaryStage().setTitle(MainApplicationEntry.getLanguageResourceBundle().getString("loginTitle"));
        infField.setVisible(false);
        try {
            if (Preferences.userNodeForPackage(MainApplicationEntry.class).keys().length == 0) {
                UserUtil.createDefaultUsers();
            }
        }
        catch (BackingStoreException e) {
            log.error("Preferences error!", e);
        }
    }
}
