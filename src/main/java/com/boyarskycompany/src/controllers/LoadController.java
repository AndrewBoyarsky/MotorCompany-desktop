package com.boyarskycompany.src.controllers;

import com.boyarskycompany.src.run.Main;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import static com.boyarskycompany.src.run.Main.getResLan;


public class LoadController implements Initializable {
    @FXML
    private ProgressBar loadProgress;
    @FXML
    private Text loadInfo;
    @FXML
    private Text loadDots;

    public void load(TimeUnit timeUnit, int duration, Stage stage) throws IOException {
        final Task<Void> task = new Task<Void>() {
            final int N_ITERATIONS = 100;

            @Override
            protected Void call() throws Exception {
                for (int i = 0; i <= N_ITERATIONS; i++) {
                    updateProgress(i, N_ITERATIONS);
                    timeUnit.sleep(duration / N_ITERATIONS);
                    changeSign(i);
                }

                return null;
            }
        };
        loadProgress.progressProperty().bind(
                task.progressProperty()
        );
        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                Parent login = null;
                try {
                    login = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/loginScene.fxml"), getResLan());
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                Main.getLoadStage().close();
                Scene loginScene = new Scene(login);
                stage.setScene(loginScene);
                stage.show();
                loadProgress.progressProperty().unbind();
                loadProgress.setProgress(0);
            }
        });

        loadProgress.progressProperty().addListener(observable -> {
            if (loadProgress.getProgress() >= 0.99) {
                loadProgress.setStyle("-fx-accent: forestgreen;");
            }
        });
            Thread dotThread = new Thread(new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    changeSign(loadProgress.getProgress());
                    return null;
                }

        });
        final Thread thread = new Thread(task, "task-thread");
        thread.setDaemon(true);
        thread.start();
        dotThread.setDaemon(true);
        dotThread.start();
//        changeLanguage();
    }

    private void changeSign(double d) {
        if (d % 7.0 == 0.0)
            loadDots.setText("");
        else
            loadDots.setText(new String(loadDots.getText() + " ."));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            load(TimeUnit.MILLISECONDS, 5000, Main.getPrStg());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

