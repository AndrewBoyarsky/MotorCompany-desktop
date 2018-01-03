package com.boyarskycompany.mcis.controllers;

import com.boyarskycompany.mcis.run.MainApplicationEntry;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import static com.boyarskycompany.mcis.run.MainApplicationEntry.getLanguageResourceBundle;


public class LoadingController implements Initializable {
    private static final Logger log = LogManager.getLogger(LoadingController.class.getName());
    @FXML
    private ProgressBar loadingProgress;
    @FXML
    private Text dotsText;

    public void load(TimeUnit timeUnit, int duration, Stage stage) throws IOException {
        Task<Void> loadingTask = new Task<Void>() {
            int iterationsNumber = 100;
            @Override
            protected Void call() throws Exception {
                for (int i = 0; i <= iterationsNumber; i++) {
                    updateProgress(i, iterationsNumber);
                    timeUnit.sleep(duration / iterationsNumber);
                    changeDotsNumber(i);
                }
                return null;
            }
        };
        loadingProgress.progressProperty().bind(loadingTask.progressProperty());

        loadingTask.setOnSucceeded(e -> {
            try {
                Scene loginScene = new Scene(FXMLLoader.load(getClass().getClassLoader().getResource("fxml/loginScene.fxml"), getLanguageResourceBundle()));
                MainApplicationEntry.getLoadingStage().close();
                stage.setScene(loginScene);
                stage.show();
                loadingProgress.progressProperty().unbind();
                loadingProgress.setProgress(0);
                log.info("Loading is finished.");
            }
            catch (IOException ioex) {
                log.error("Unable to find or load login scene. ", ioex);
            }
        });

        loadingProgress.progressProperty().addListener(observable -> {
            if (loadingProgress.getProgress() >= 0.99) {
                loadingProgress.setStyle("-fx-accent: forestgreen;");
            }
        });

//        Thread dotChangingThread = new Thread(new Task<Void>() {
//            @Override
//            protected Void call() throws Exception {
//                changeDotsNumber(loadingProgress.getProgress());
//                return null;
//            }
//
//        });
        final Thread thread = new Thread(loadingTask, "task-thread");
        thread.setDaemon(true);
        thread.start();
//        dotChangingThread.setDaemon(true);
//        dotChangingThread.start();
    }

    private void changeDotsNumber(double d) {
        if (d % 7.0 == 0.0)
            dotsText.setText("");
        else
            dotsText.setText(new String(dotsText.getText() + " ."));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            load(TimeUnit.MILLISECONDS, 5000, MainApplicationEntry.getPrimaryStage());
        }
        catch (IOException e) {
            log.error("Wrong i/o. ", e);
        }
    }
}

