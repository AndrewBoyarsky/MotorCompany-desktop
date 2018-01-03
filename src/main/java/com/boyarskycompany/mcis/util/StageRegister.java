package com.boyarskycompany.mcis.util;

import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class StageRegister {
    private static final Logger log = LogManager.getLogger(StageRegister.class.getName());
    private static List<Stage> openedStages = new ArrayList<>();

    public static void register(Stage stage) {

        openedStages.add(stage);
        stage.setOnCloseRequest(e -> {
            openedStages.remove(stage);
            log.debug("Stage " + stage + " was removed.");
        });
        log.debug("Stage " + stage + " was registered.");
    }

    public static List<Stage> getOpenedStages() {
        return openedStages;
    }

    public static void closeStages() {
        openedStages.forEach(stage -> {
            stage.close();
        });
        log.info("All non-main stages were closed.");
    }

}