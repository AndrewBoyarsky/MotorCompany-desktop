package com.boyarskycompany.mcis.controllers;

import com.boyarskycompany.mcis.run.MainApplicationEntry;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by zandr on 18.09.2016.
 */
public class ConnectController implements Initializable {
    @FXML
    private Text userNameText;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MainApplicationEntry.getPrimaryStage().setTitle(resources.getString("connectTitle"));
        userNameText.setText(LoginController.getCurrentUser().getPrivileges());
    }
}
