package com.boyarskycompany.src.controllers.util;

import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class StageRegister {
    private static List<Stage> openedStages = new ArrayList<>();
        public static void register(Stage stage) {
            openedStages.add(stage);
            stage.setOnCloseRequest(e -> {
                openedStages.remove(stage);
            });
        }

    public static List<Stage> getOpenedStages() {
        return openedStages;
    }

    public static void closeStages() {
        openedStages.forEach(stage -> {
            stage.close();
        });
    }

}