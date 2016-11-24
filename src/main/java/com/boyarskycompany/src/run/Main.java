package com.boyarskycompany.src.run;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.*;

public class Main extends Application {
    private static Stage prStg;
    private static Stage loadStage;
    private static ResourceBundle resLan;

    public static Stage getLoadStage() {
        return loadStage;
    }

    public static void setLoadStage(Stage loadStage) {
        Main.loadStage = loadStage;
    }

    private static void setResLan(ResourceBundle resLan) {
        Main.resLan = resLan;
    }

    public static synchronized ResourceBundle getResLan() {
        return resLan;
    }

    public static Stage getPrStg() {
        return prStg;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.getIcons().add(new Image("images/car.png"));
        chooseLanguage();
        if (resLan == null) {
            System.exit(0);
        }
        setPrStg(primaryStage);
        primaryStage.close();
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        Scene loadScene = new Scene(FXMLLoader.load(getClass().getClassLoader().getResource("fxml/loadScene.fxml"), resLan));
        primaryStage.setOnCloseRequest(e -> {
            System.exit(0);
        });
        Stage loadStage = new Stage(StageStyle.UNDECORATED);
        loadStage.setScene(loadScene);
        loadStage.show();
        setLoadStage(loadStage);
//        primaryStage.show(); // only for test

    }

    private static void setPrStg(Stage prStgage) {
        prStg = prStgage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void chooseLanguage() {
        Locale defaultLocale = Locale.getDefault();
        String defaultChoice;
        ResourceBundle defaultRes = null;
        if (getResLan() != null) {
            defaultRes = getResLan();
        } else if (defaultLocale.getLanguage().equalsIgnoreCase("ru")) {
            defaultRes = ResourceBundle.getBundle("localization/languages", CharsetControl.RUS);
        } else if (defaultLocale.getLanguage().equalsIgnoreCase("ua")) {
            defaultRes = ResourceBundle.getBundle("localization/languages_uk", CharsetControl.UA);
        } else {
            defaultRes = ResourceBundle.getBundle("localization/languages", CharsetControl.ENG);
        }
        defaultChoice = defaultRes.getString("language");
        System.out.println("Default language: " + defaultChoice);
        ArrayList<String> listOfLanguages = new ArrayList<>(Arrays.asList("Русский", "Українська", "English"));
        ChoiceDialog<String> languageChooser = new ChoiceDialog<>(defaultChoice, listOfLanguages);
        ((Stage) languageChooser.getDialogPane().getScene().getWindow()).getIcons().add(new Image("images/languageIcon.png"));
        languageChooser.setContentText(defaultRes.getString("chooseLanguageText"));
        languageChooser.setTitle(defaultRes.getString("chooseLanguageTitle"));
        languageChooser.setHeaderText(defaultRes.getString("chooseLanguageHeader"));
        Optional<String> result = languageChooser.showAndWait();
        result.ifPresent(language -> {
            if (language.equals("Русский")) {
                setResLan(ResourceBundle.getBundle("localization/languages", CharsetControl.RUS));
            } else if (language.equals("Українська")) {
                setResLan(ResourceBundle.getBundle("localization/languages_uk", CharsetControl.UA));
            } else if (language.equals("English")) {
                setResLan(ResourceBundle.getBundle("localization/languages", CharsetControl.ENG));
            }
        });
    }

}

