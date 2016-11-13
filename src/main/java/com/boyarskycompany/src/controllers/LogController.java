package com.boyarskycompany.src.controllers;

import com.boyarskycompany.src.controllers.database.HibernateUtil;
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


/**
 * Created by zandr on 16.09.2016.
 */
public class LogController implements Initializable {

    private static String userPrivileges;
    private static String userName;
    public static String getUserPrivileges() {
        return userPrivileges;
    }

    public static String getUserName() {
        return userName;
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
//        userField.setText("Admin"); //only for test
//        passwordField.setText("finalfullpowers"); //only for test
        LoginData loginData = login(userField.getText(), passwordField.getText());
        if (loginData.isValidated()) {
            infField.setVisible(false);
            Preferences pref = Preferences.userNodeForPackage(Main.class);
            Main.getPrStg().setScene(new Scene(FXMLLoader.load(getClass().getClassLoader().getResource("fxml/connecting.fxml"), Main.getResLan())));
            Thread thread;
            Task task = new Task() {
                @Override
                protected Void call() throws Exception {
                    HibernateUtil.connect();
                    return null;
                }
            };
            task.setOnSucceeded(handler -> {
                try {
                    Main.getPrStg().setScene(new Scene(FXMLLoader.load(getClass().getClassLoader().getResource("fxml/mainScene.fxml"), Main.getResLan())));
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

    public static LoginData login(String user, String password) {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        int number = Integer.parseInt(prefs.get("number", "0"));
        if (number == 0) return new LoginData(false, -1);
        else
            for (int i = 1; i <= number; i++)
                if (prefs.get("user" + i, "0").equals(user) && !prefs.get("user" + i, "0").equals("0"))
                    if (prefs.get("password" + i, "0").equals(password) && !prefs.get("password" + i, "0").equals("0")) {
                        userPrivileges = prefs.get("privileges" + i, "0");
                        userName = prefs.get("user" + i, "0");
                        return new LoginData(true, i);
                    }

        return new LoginData(false, -1);
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
