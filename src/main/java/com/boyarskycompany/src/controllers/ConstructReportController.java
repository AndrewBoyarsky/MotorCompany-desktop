package com.boyarskycompany.src.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by zandr on 13.11.2016.
 */
public class ConstructReportController implements Initializable {
    private static ProgressBar progressBar;

    public static ProgressBar getProgressBar() {
        return progressBar;
    }

    @FXML
    private ProgressBar constructReportProgress;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        progressBar = constructReportProgress;
    }
}
