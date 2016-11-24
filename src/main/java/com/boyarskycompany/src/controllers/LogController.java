package com.boyarskycompany.src.controllers;

import com.boyarskycompany.src.privacy.UserBean;
import com.boyarskycompany.src.privacy.Users;
import com.boyarskycompany.src.run.Main;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import static com.boyarskycompany.src.privacy.Users.isValid;


/**
 * Created by zandr on 16.09.2016.
 */
public class LogController implements Initializable {

    private static UserBean currentUser;

    public static UserBean getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(UserBean currentUser) {
        this.currentUser = currentUser;
    }

    @FXML
    private TextField userField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label infField;
    @FXML
    private Button loginButton;

    @FXML
    private void handleLoginButton() throws IOException, SQLException {
        UserBean user = new UserBean(userField.getText(), passwordField.getText());

        if (isValid(user)) {
            currentUser = user;
            infField.setVisible(false);
            Main.getPrStg().close();
            Main.getLoadStage().setScene(new Scene(FXMLLoader.load(getClass().getClassLoader().getResource("fxml/connectingScene.fxml"), Main.getResLan())));
            Main.getLoadStage().show();
            Thread thread;
            Task task = new Task() {
                @Override
                protected Void call() throws Exception {
                    Class.forName("com.boyarskycompany.src.controllers.database.HibernateUtil");
                    return null;
                }
            };
            task.setOnSucceeded(handler -> {
                try {
                    Main.getLoadStage().close();
                    Main.getPrStg().setScene(new Scene(FXMLLoader.load(getClass().getClassLoader().getResource("fxml/mainScene.fxml"), Main.getResLan())));
                    Main.getPrStg().show();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            });
            thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
        } else {
            infField.setVisible(true);
            passwordField.clear();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.getPrStg().setTitle(Main.getResLan().getString("loginTitle"));
        infField.setVisible(false);
        try {
            if (Preferences.userNodeForPackage(Main.class).keys().length == 0) {
                Users.createDefaultUsers();
            }
        }
        catch (BackingStoreException e) {
            e.printStackTrace();
        }
    }
}
