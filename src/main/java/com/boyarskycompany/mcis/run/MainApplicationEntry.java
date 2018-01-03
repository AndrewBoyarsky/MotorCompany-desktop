package com.boyarskycompany.mcis.run;

import com.boyarskycompany.mcis.util.CharsetControl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class MainApplicationEntry extends Application {
    private static Logger log = LogManager.getLogger(MainApplicationEntry.class.getName());
    private static Stage primaryStage;
    private static Stage loadingStage;
    private static ResourceBundle languageResourceBundle;

    public static Stage getLoadingStage() {
        return loadingStage;
    }

    public static void setLoadingStage(Stage loadingStage) {
        MainApplicationEntry.loadingStage = loadingStage;
    }

    public static synchronized ResourceBundle getLanguageResourceBundle() {
        return languageResourceBundle;
    }

    private static void setLanguageResourceBundle(ResourceBundle languageResourceBundle) {
        MainApplicationEntry.languageResourceBundle = languageResourceBundle;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    private static void setPrimaryStage(Stage primaryStage) {
        MainApplicationEntry.primaryStage = primaryStage;
    }

    public static void main(String[] args) {
        log.info("Application is launching");
        launch(args);
    }

    public static void chooseLanguage() {
        Locale defaultLocale = Locale.getDefault();
        log.info("Default locale is " + defaultLocale.getLanguage());
        String defaultChoice;
        ResourceBundle defaultRes = null;
        if (getLanguageResourceBundle() != null) {
            defaultRes = getLanguageResourceBundle();
        } else if (defaultLocale.getLanguage().equalsIgnoreCase("ru")) {
            defaultRes = ResourceBundle.getBundle("localization/languages", CharsetControl.RUS);
        } else if (defaultLocale.getLanguage().equalsIgnoreCase("ua")) {
            defaultRes = ResourceBundle.getBundle("localization/languages_uk", CharsetControl.UA);
        } else {
            defaultRes = ResourceBundle.getBundle("localization/languages", CharsetControl.ENG);
        }
        defaultChoice = defaultRes.getString("language");
        log.info("Default language is " + defaultChoice);
        ArrayList<String> listOfLanguages = new ArrayList<>(Arrays.asList("Русский", "Українська", "English"));
        ChoiceDialog<String> languageChooser = new ChoiceDialog<>(defaultChoice, listOfLanguages);
        ((Stage) languageChooser.getDialogPane().getScene().getWindow()).getIcons().add(new Image("images/languageIcon.png"));
        languageChooser.setContentText(defaultRes.getString("chooseLanguageText"));
        languageChooser.setTitle(defaultRes.getString("chooseLanguageTitle"));
        languageChooser.setHeaderText(defaultRes.getString("chooseLanguageHeader"));
        Optional<String> result = languageChooser.showAndWait();
        result.ifPresent(language -> {
            if (language.equals("Русский")) {
                setLanguageResourceBundle(ResourceBundle.getBundle("localization/languages", CharsetControl.RUS));
            } else if (language.equals("Українська")) {
                setLanguageResourceBundle(ResourceBundle.getBundle("localization/languages_uk", CharsetControl.UA));
            } else if (language.equals("English")) {
                setLanguageResourceBundle(ResourceBundle.getBundle("localization/languages", CharsetControl.ENG));
            }
        });
        log.info("Chosen language is " + languageResourceBundle.getString("language"));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.getIcons().add(new Image("images/car.png"));
        chooseLanguage();
        if (languageResourceBundle == null) {
            log.info("Application is terminated. Language is not chosen.");
            System.exit(0);
        }
        setPrimaryStage(primaryStage);
        primaryStage.close();
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        Scene loadScene = new Scene(FXMLLoader.load(getClass().getClassLoader().getResource("fxml/loadScene.fxml"), languageResourceBundle));
        primaryStage.setOnCloseRequest(e -> {
            log.info("Application is terminated.");
            System.exit(0);
        });
        Stage loadStage = new Stage(StageStyle.UNDECORATED);
        loadStage.setScene(loadScene);
        setLoadingStage(loadStage);
        loadStage.show();
        log.info("Loading...");
    }

}

